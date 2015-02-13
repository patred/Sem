if (typeof SEM == "undefined" || !SEM) {
	var SEM = {};
}

if (typeof SEM.SemClient == "undefined" || !SEM.SemClient) {
	SEM.SemClient = {};
}

if (typeof SEM.SemClient.Urls == "undefined" || !SEM.SemClient.Urls) {
	SEM.SemClient.Urls = {};
}

if (typeof SEM.SemClient.Utils == "undefined" || !SEM.SemClient.Utils) {
	SEM.SemClient.Utils = {};
}

SEM.SemClient.Urls.timesheet = "services/timesheet";
SEM.SemClient.Urls.timesheetDetails = "services/timesheet/detail/";
SEM.SemClient.Urls.timesheetForm = "services/timesheet/form";
SEM.SemClient.Urls.timesheetHistory = "services/timesheet/history";

SEM.SemClient.timesheet = function(callback) {
	$.get(SEM.SemClient.Urls.timesheet, function(data) {
		$("#main").html(data);
		$("#Theader").hide();
	});
};

SEM.SemClient.timesheetForm = function(id) {
	$.get(SEM.SemClient.Urls.timesheetForm + ((id != undefined && id != null) ? '/'+id : ''), function(data) {
		$("#Theader").html(data);
		$("#Tbody").hide();
		$('#getAddTimesheetForm').hide();
		$('#getEditTimesheetForm').hide();
		$('#delTimesheet').hide();
		if(id != undefined && id != null){
			 $('#addTimesheet').hide();
			 $('#editTimesheet').show();
		} else {
			$('#editTimesheet').hide();
			$('#addTimesheet').show();
		};
		$("#cancel").show();
		$("#Theader").show();
	});
};

SEM.SemClient.timesheetHistory = function(callback) {
	$.get(SEM.SemClient.Urls.timesheetHistory, function(data) {
		$("#main").html(data);
		$("#Theader").hide();
	});
};

SEM.SemClient.addTimesheet	= function(callback){
	var obj = {};
	obj.status = $('#status').val();
	obj.month = parseInt($('#datetimepicker').val().split('/')[0]);
	obj.year = parseInt($('#datetimepicker').val().split('/')[1]);
	obj.note = $('#note').val();
	$.ajax(
		{
			url: SEM.SemClient.Urls.timesheetForm, 
			type: "POST",
			data: JSON.stringify(obj), 
		    headers: { 
		        "Accept" : "application/json",
		        "Content-Type": "application/json"
		    },
			success: function(data, textStatus, jqXHR) {
				if (callback != undefined)
					callback(true);
				SEM.SemClient.timesheet();
			},
			error: function(jqXHR, textStatus, errorThrown) {
				var data = {error: "generic"}; 
				try {
					data = $.parseJSON(jqXHR.responseText);					
				} catch (e) {
					//nothing
				}
				if (callback != undefined)
					callback(false);
			}
		}
	);
};

SEM.SemClient.editTimesheet	= function(id, callback){
	var obj = {};
	if(id != 0)
		obj.id = id;
	obj.status = $('#status').val();
	obj.month = parseInt($('#datetimepicker').val().split('/')[0]);
	obj.year = parseInt($('#datetimepicker').val().split('/')[1]);
	obj.note = $('#note').val();
	$.ajax(
		{
			url: SEM.SemClient.Urls.timesheetForm, 
			type: "PUT",
			data: JSON.stringify(obj), 
		    headers: { 
		        "Accept" : "application/json",
		        "Content-Type": "application/json"
		    },
			success: function(data, textStatus, jqXHR) {
				if (callback != undefined)
					callback(true);
				 SEM.SemClient.timesheet();
			},
			error: function(jqXHR, textStatus, errorThrown) {
				var data = {error: "generic"}; 
				try {
					data = $.parseJSON(jqXHR.responseText);					
				} catch (e) {
					//nothing
				}
				if (callback != undefined)
					callback(false);
			}
		}
	);
};

SEM.SemClient.delTimesheetForm = function(id) {
	if (id != undefined && id != null) {
		$.ajax({
			url : SEM.SemClient.Urls.timesheetForm + '/' + id,
			type : 'DELETE',
			success : function(data, textStatus, jqXHR) {
				SEM.SemClient.timesheet();
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log('error');
			}
		});
	}
};

SEM.SemClient.timesheetDetails = function(id) {
	$.get(SEM.SemClient.Urls.timesheetDetails + id, function(data) {
	});
};