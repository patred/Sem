/* Initialize and render the MenuBar when the page's DOM is ready to be scripted. */

YUI.namespace("Sem.container");
var YContainer = YUI.Sem;

YContainer.loginDialog = {};
YContainer.waitDialog = {};
YContainer.tooltipDialog = {};
YContainer.errorMessageDialog = {};
YContainer.alertDialog = {};
YContainer.TagLogDialog = {};
YContainer.TagLogDialog.dialog = new Array();

YUI().use("panel", function(Y) { // loading escape only for security on this page
	YContainer.alertDialog = new Y.Panel({
		contentBox : '#loginDialog',
		headerContent : '#loginDialogHd',
		bodyContent : '#loginDialogBd',
		width : 300,
		zIndex : 1,
		centered : true,
		modal : true, // modal behavior
		render : true,
		visible : true, // make visible explicitly with .show()
		buttons : {
			footer : [ {
				name : 'proceed',
				label : 'Accedi',
				action : 'handleLoginSubmit'
			} ]
		}
	});

	YContainer.loginDialog.handleLoginSubmit = function() {
		var input = YContainer.loginDialog.dialog.body.getElementsByTagName("input");
		username = input[0].value;
		password = input[1].value;
		YAHOO.util.Connect.syncRequest("GET", server + "manager/userroles", YContainer.loginDialog.callback);
		YContainer.waitDialog.dialog.show();
		YContainer.loginDialog.dialog.hide();
		YAHOO.util.Connect.setDefaultPostHeader(true);
	};

	YContainer.loginDialog.handleLoginSubmit = function(e) {
		e.preventDefault();
		this.hide();
		// code that executes the user confirmed action goes
		// here
		if (this.callback) {
			this.callback.fn();
		}
		// callback reference removed, so it won't persist
		this.callback = false;
		Y.log("handleLoginSubmit");
	}
});