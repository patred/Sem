/* Initialize and render the MenuBar when the page's DOM is ready to be scripted. */
var server = "";
var username;
var password;
var role = "none";

YUI.namespace("Sem.container");
var YContainer = YUI.Sem;

YContainer.loginDialog = {};
YContainer.waitDialog = {};
YContainer.errorMessageDialog = {};
YContainer.alertDialog = {};

YContainer.UserDialog = {};

YUI().use("node", "node-menunav", "panel", "io-form", "datasource-io", "datatable", "datasource-xmlschema","gallery-datatable-selection", function(Y) {

	if (document.URL.indexOf("http://") > -1 || document.URL.indexOf("https://") > -1) {
		Y.one("#serverTR").setStyle("display", "none");
	};

	Y.on("contentready", function() {
		this.plug(Y.Plugin.NodeMenuNav, {
			autoSubmenuDisplay : false,
			mouseOutHideDelay : 0
		});
	}, "#menubar");

	YContainer.loginDialog = new Y.Panel({
		contentBox : '#loginDialog',
		headerContent : 'Login',
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
	
	YContainer.loginDialog.handleLoginSubmit = function(e) {
		e.preventDefault();
		Y.log("handleLoginSubmit");
		username = Y.one('#username').get('value');
		password = Y.one('#password').get('value');
		Y.io(server + 'backoffice/userroles', {
			method : 'POST',
			data :  { 'username':username, 'password':password },
			form : {
				id : '#loginForm',
				useDisabled : true
			},
			on:{
				start:function(id,response){
					Y.log('start');
					YContainer.waitDialog.show();
				},
				complete:function(id, response){
					Y.log('complete');
					YContainer.loginDialog.hide();
				},
				success:function(id,response){
					Y.log('success');
					var xml = response.responseXML;
					var role = evalutateXPath(xml, "root/role");
					var user = evalutateXPath(xml, "root/user");
					console.log('role: ' + role);
					console.log('user: ' + user);
					var sRole = getContentInElement(role.pop());
					var sUser = getContentInElement(user.pop());
					$(".sem_"+sRole.toLowerCase()).show();
				},
				failure:function(id, response){
					Y.log('failure');
					YContainer.loginDialog.show();
					onErrorMessage("Username e/o password errate");
				},
				end:function(id, response){
					Y.log('end');
					YContainer.waitDialog.hide();
				}
			}
		});
	}

	YContainer.waitDialog = new Y.Panel({
		contentBox : "#waitDialog",
		width : 240,
		zIndex : 4,
		centered : true,
		render : true,
		modal : true, // modal behavior
		visible : false
	// make visible explicitly with .show()
	});

	/**
	 * **************************** ALERT
	 * ********************************************************
	 */
	YContainer.alertDialog = new Y.Panel({
		contentBox : "#alertDialog",
		bodyContent : '<div class="message icon-warn"></div>',
		width : 410,
		zIndex : 5,
		centered : true,
		modal : true, // modal behavior
		render : true,
		visible : false, // make visible explicitly with .show()
		buttons : {
			footer : [ {
				name : 'cancel',
				label : 'No',
				action : 'onCancel'
			},

			{
				name : 'proceed',
				label : 'Si',
				action : 'onOK'
			} ]
		}
	});

	YContainer.alertDialog.onCancel = function(e) {
		e.preventDefault();
		this.hide();
		// the callback is not executed, and is
		// callback reference removed, so it won't persist
		this.callback = false;
	}

	YContainer.alertDialog.onOK = function(e) {
		e.preventDefault();
		this.hide();
		// code that executes the user confirmed action goes here
		if (this.callback) {
			this.callback.fn();
		}
		// callback reference removed, so it won't persist
		this.callback = false;
	}

	function onAlertMessage(message, callback) {
		Y.one("#alertDialog .message").setContent(message);

		if (callback != null && callback.yes != null
				&& callback.yes != undefined) {
			YContainer.alertDialog.callback = callback.yes;
		};

		YContainer.alertDialog.show();

	};

	/**
	 * **************************** Error message
	 * *************************************************
	 */
	YContainer.errorMessageDialog = new Y.Panel({
		contentBox : "#errorMessageDialog",
		bodyContent : '<div class="message icon-error"></div>',
		width : 350,
		zIndex : 5,
		centered : true,
		modal : true, // modal behavior
		render : true,
		visible : false, // make visible explicitly with .show()
		buttons : [ {
			value : 'Ok',
			section : Y.WidgetStdMod.FOOTER,
			action : function(e) {
				e.preventDefault();
				this.hide();
			}
		} ]
	});

	function onErrorMessage(message) {
		Y.one("#errorMessageDialog .message").setContent(message);
		YContainer.errorMessageDialog.show();
	};
	
	YContainer.UserDialog.createAndShow = function() {
		if (YContainer.UserDialog.dialog == null) {
			var html = getFile("dialog/users.html");
			var elDiv = document.createElement("div");
			elDiv.innerHTML = html;
			document.body.appendChild(elDiv);
			
			YContainer.UserDialog.dialog = new Y.Panel({
				contentBox 		: "#usersDialog",
				headerContent	: "Gestione Utenti",
				width : 450,
				zIndex : 1,
				centered : true,
				modal : true, // modal behavior
				render : true,
				visible : true, // make visible explicitly with .show()
				buttons : {
					footer : [
					          { name : 'add', 	label : 'Aggiungi', action : 'onAdd'},
					          { name : 'del', 	label : 'Elimina', 	action : 'onDel', disabled : true},
					          { name : 'close', label : 'Close', 	action : 'onClose'}
					         ]
				}
			});

			var myColumnDef = [
			    { key : "username",	label : "Username",	sortable : true,	resizeable:false },
			    { key : "name",		label : "Nome",		sortable : true, 	resizeable:false},
			    { key : "surname",	label : "Cognome",	sortable : true, 	resizeable:false},
			    { key : "role",		label : "Ruolo",	sortable : false,	resizeable:true }
			   ];

			var myDataSource = new Y.DataSource.IO({
			    source:"http://localhost:8080/sem/backoffice/user"
			});

			myDataSource.plug(Y.Plugin.DataSourceXMLSchema, {
			    schema: {
			        resultListLocator: "user",
			        resultFields: [
                       {key:"username", locator:"*[local-name() ='username']"},
                       {key:"name", locator:"*[local-name() ='name']"},
                       {key:"surname", locator:"*[local-name() ='surname']"},
                       {key:"role", locator:"*[local-name() ='role']"}
			        ]
			    }
			});
			
			var myDataTable = new Y.DataTable({
		        columns	: myColumnDef,
		        highlightMode: 'row',
		        selectionMode: 'row',
		        selectionMulti: false,
		        scrollable: 'y',
		        width	: '100%',
		        sortBy	: { username: 'desc' }
		    });
			
			myDataTable.plug(Y.Plugin.DataTableDataSource, {
			    datasource: myDataSource,
			    initialRequest: ""
			});
			
			myDataSource.after("response", function() {
				myDataTable.render("#usersDataTable")}
			);
			
			myDataTable.delegate('click', function (e) {
				if(YContainer.UserDialog.currentRecord != this.getRecord(e.currentTarget)){
					YContainer.UserDialog.currentRecord = this.getRecord(e.currentTarget);
					YContainer.UserDialog.dialog.getButton(1).set('disabled', false);
				}

			}, '.yui3-datatable-data tr', myDataTable);
			
			myDataTable.delegate('dblclick', function (e) {
				Y.log('dblclick');
				 Y.log(YContainer.UserDialog.currentRecord.toJSON());
		     }, '.yui3-datatable-data tr', myDataTable);
			
			YContainer.UserDialog.datatable = myDataTable;
			YContainer.UserDialog.datasource = myDataSource;
		} else {
			YContainer.UserDialog.refresh();
		};
		
		YContainer.UserDialog.refresh = function () {
			YContainer.UserDialog.currentRecord = null;
			YContainer.UserDialog.datatable.clearAll()
			YContainer.UserDialog.dialog.getButton(1).set('disabled', true);
			YContainer.UserDialog.dialog.show();
		};
		
		YContainer.UserDialog.dialog.onAdd = function(e) {
			e.preventDefault();
			alert("YContainer.alertDialog.onAdd");
		};
			
		YContainer.UserDialog.dialog.onDel = function(e) {
	        Y.log(YContainer.UserDialog.currentRecord.toJSON());
			this.hide();
		}
		
		YContainer.UserDialog.dialog.onClose = function(e) {
			e.preventDefault();
			YContainer.UserDialog.dialog.hide();
		}
	};

});
