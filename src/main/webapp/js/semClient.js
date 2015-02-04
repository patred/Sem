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
SEM.SemClient.Urls.timesheetHistory = "services/timesheet/history";

SEM.SemClient.timesheet = function(callback) {
	$.get(SEM.SemClient.Urls.timesheet, function(data) {
		$("#main").html(data);
		alert("Load was performed.");
	});
};

SEM.SemClient.timesheetHistory = function(callback) {
	$.get(SEM.SemClient.Urls.timesheetHistory, function(data) {
		$("#main").html(data);
		alert("Load was performed.");
	});
};
