/* Initialize and render the MenuBar when the page's DOM is ready to be scripted. */
//var server = window.location.protocol + "//" + window.location.host + window.location.pathname;
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
YContainer.UserDialogForm = {};


YUI().use("node", "node-menunav", "panel", "transition", "io-form", "datasource-io", "datatable", "datatable-scroll", "datasource-xmlschema","gallery-datatable-selection", "resize-plugin", function(Y) {

	if (document.URL.indexOf("http://") > -1 || document.URL.indexOf("https://") > -1) {
		Y.one("#serverTR").setStyle("display", "none");
	};

	Y.on("contentready", function() {
		this.plug(Y.Plugin.NodeMenuNav, {
			autoSubmenuDisplay : false,
			mouseOutHideDelay : 5
		});
		YContainer.loginDialog.showPanel();
	}, "#menubar");

	YContainer.loginDialog = new Y.Panel({
		contentBox : '#loginDialog',
		headerContent : 'Login',
		width : 300,
		zIndex : 1,
		centered : true,
		modal : true, // modal behavior
		render : true,
		visible : false, // make visible explicitly with .show()
		buttons : {
			footer : [ {
				name : 'proceed',
				label : 'Accedi',
				action : 'handleLoginSubmit'
			} ]
		}
	});
	
	bb = YContainer.loginDialog.get('boundingBox');
	
	YContainer.loginDialog.showPanel = function() {
		YContainer.loginDialog.show();
        bb.transition({
            duration: 0.5,
            top     : '80px'
        });
    };

    YContainer.loginDialog.hidePanel = function() {
        bb.transition({
            duration: 0.5,
            top     : '-300px'
        }, function () {
        	YContainer.loginDialog.hide();
        });
    };
	
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
					YContainer.loginDialog.hidePanel();
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
					YContainer.loginDialog.showPanel();
					//YContainer.loginDialog.show();
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
	
	/******* UserDialog ********/
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

			YContainer.UserDialog.dialog.plug(Y.Plugin.Resize);
			
			var myColumnDef = [
			    { key : "username",	label : "Username",	sortable : true,	resizeable:false },
			    { key : "name",		label : "Nome",		sortable : true, 	resizeable:false},
			    { key : "surname",	label : "Cognome",	sortable : true, 	resizeable:false},
			    { key : "role",		label : "Ruolo",	sortable : false,	resizeable:true }
			   ];

			var myDataSource = new Y.DataSource.IO({
				source: server + 'backoffice/user'
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
		        height	: '200px',
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
				var record = YContainer.UserDialog.currentRecord;
				var user = new SEM.BussinessObject.User(record.get("username"));
				YContainer.UserDialogForm.createAndShow(user);
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
			YContainer.UserDialog.datatable.datasource.load();
			YContainer.UserDialog.dialog.show();
		};
		
		YContainer.UserDialog.dialog.onAdd = function(e) {
			e.preventDefault();
			var user = new SEM.BussinessObject.User();
			YContainer.UserDialogForm.createAndShow(user);
		};
			
		YContainer.UserDialog.dialog.onDel = function(e) {
			e.preventDefault();
			var record = YContainer.UserDialog.currentRecord;
			onAlertMessage("Sicuro di voler eliminare l'utente  <b>" + record.get("username") + "</b>?", { 
				yes: {
					  fn: function(o) {
						  var user = new SEM.BussinessObject.User(record.get("username"));
						  user.setToDelete(true);
						  SEM.BussinessMethos.saveBussinesObject(user);
						  YContainer.UserDialog.refresh;
						}, 
					  obj: record
					}
			});
		};
		
		YContainer.UserDialog.dialog.onClose = function(e) {
			e.preventDefault();
			YContainer.UserDialog.dialog.hide();
		};
		
		YContainer.UserDialog.dialog.show();
	};
	
	YContainer.UserDialogForm.createAndShow = function(user) {
		YContainer.UserDialogForm.currentUser = user;

		if(YContainer.UserDialogForm.dialog == null) {
			var html = getFile("dialog/userForm.html");
			var elDiv = document.createElement("div");
			elDiv.innerHTML = html;
			document.body.appendChild(elDiv);
			
			YContainer.UserDialogForm.dialog = new Y.Panel({
				contentBox 		: "#UserDialogForm",
				headerContent	: "Utente",
				width : 350,
				//height : 350,
				zIndex : 1,
				centered : true,
				modal : true, // modal behavior
				render : true,
				visible : true, // make visible explicitly with .show()
				buttons : {
					footer : [
					          { name : 'cancel', 	label : 'Annulla', 	action : 'onCancel'},
					          { name : 'ok', label : 'Ok', 	action : 'onOk'}
					         ]
				}
			});
			
			var combo = Y.one('#userDialogForm_Role');
			addOption("Empty", combo, true);
			addOption("Employee", combo, true);
			addOption("Manager", combo, true);
			
		} else {
			YContainer.UserDialogForm.dialog.show();
		};
		
		Y.one('#userDialogForm_Username').set('value', user.getUsername());
		Y.one('#userDialogForm_Name').set('value', user.getName());
		Y.one('#userDialogForm_Surname').set('value', user.getSurname());
		Y.one('#userDialogForm_Role').set('value', user.getRole());

		if(user.isNew())
			Y.one('#userDialogForm_Username').set('disabled', false);
		else
			Y.one('#userDialogForm_Username').set('disabled', true);
		
		YContainer.UserDialogForm.dialog.onCancel = function(e) {
			e.preventDefault();
			this.hide();
		};
			
		YContainer.UserDialogForm.dialog.onOk = function(e) {
			e.preventDefault();
			YContainer.waitDialog.show();
			var user = YContainer.UserDialogForm.currentUser;
			user.setUsername(Y.one('#userDialogForm_Username').get('value'));
			user.setName(Y.one('#userDialogForm_Name').get('value'));
			user.setSurname(Y.one('#userDialogForm_Surname').get('value'));
			user.setRole(Y.one('#userDialogForm_Role').get('value'));
			SEM.BussinessMethos.saveBussinesObject(user);
			this.hide();
			YContainer.waitDialog.hide();
			YContainer.UserDialog.refresh();
		};
	};
});
