addTimesheet = function() {
	SEM.SemClient.addTimesheet();
};

cancel = function() {
	SEM.SemClient.timesheet();
};

addTimesheetForm = function() {
	SEM.SemClient.timesheetForm();
};

editTimesheet = function() {
	SEM.SemClient.editTimesheet($('#timesheetID').val());
};

editTimesheetForm = function(callback) {
	if($('input[type=radio]:checked').val() != undefined){
		SEM.SemClient.timesheetForm($('input[type=radio]:checked').val());
	}else
		alert("Selezionare un Timesheet");
};

delTimesheet = function(callback) {
	if ($('input[type=radio]:checked').val() != undefined) {
		if (confirm("Sicuro di voler eliminare il Timesheet "
				+ $('input[type=radio]:checked').val() + "?")) {
			SEM.SemClient.delTimesheetForm($('input[type=radio]:checked').val());
		} else {
			// Do nothing!
		}
	} else {
		alert("Selezionare un Timesheet");
	}
};

timesheetDetails = function() {
	SEM.SemClient.timesheetDetails($('#timesheetID').val());
};

addDetailForm = function() {
	SEM.SemClient.timesheetDetailForm();
};

confirmDetail = function() {
	SEM.SemClient.confirmDetail();
};