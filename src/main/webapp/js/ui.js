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
YContainer.SelectUserDialog = {};

YContainer.EmployeeDialog = {};
YContainer.EmployeeDialogForm = {};
YContainer.SelectOrderDialog = {};

YContainer.ManagerDialog = {};
YContainer.ManagerDialogForm = {};

YContainer.ClientDialog = {};
YContainer.ClientDialogForm = {};
YContainer.SelectClientDialog = {};
YContainer.SimpleClientDialogForm = {};

YContainer.OrderDialog = {};
YContainer.OrderDialogForm = {};


YUI().use("node", "node-menunav", "panel", "dd-plugin", "transition", "io-form", "datasource-io", "datatable", "datatable-scroll", "datasource-xmlschema","gallery-datatable-selection", "resize-plugin", function(Y) {
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
		Y.io(server + 'userroles', {
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
					//var sUser = getContentInElement(user.pop());
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
			var html = getFile("../dialog/users.html");
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
					          { name : 'close', label : 'Chiudi', 	action : 'onClose'}
					         ]
				}
			});

			YContainer.UserDialog.dialog.plug(Y.Plugin.Resize);
			
			YContainer.UserDialog.dialog.plug(Y.Plugin.Drag, {
			    handles: [
			        '.yui3-widget-hd'
			    ]
			});
			
			var myColumnDef = [
			    { key : "username",	label : "Username",	sortable : true,	resizeable:false },
			    { key : "role",		label : "Ruolo",	sortable : false,	resizeable:false }
			   ];

			var myDataSource = new Y.DataSource.IO({
				source: server + 'user'
			});

			myDataSource.plug(Y.Plugin.DataSourceXMLSchema, {
			    schema: {
			        resultListLocator: "user",
			        resultFields: [
                       {key:"username", locator:"username"},
                       {key:"role", locator:"role"}
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
				myDataTable.render("#usersDataTable");
				});
			
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
			
			YContainer.UserDialog.dialog.onAdd = function() {
				var user = new SEM.BussinessObject.User();
				YContainer.UserDialogForm.createAndShow(user);
			};
				
			YContainer.UserDialog.dialog.onDel = function() {
				var record = YContainer.UserDialog.currentRecord;
				onAlertMessage("Sicuro di voler eliminare l'utente  <b>" + record.get("username") + "</b>?", { 
					yes: {
						  fn: function(o) {
							  var user = new SEM.BussinessObject.User(record.get("username"));
							  user.setToDelete(true);
							  SEM.BussinessMethos.saveBussinesObject(user);
							  YContainer.UserDialog.refresh();
							}, 
						  obj: record
						}
				});
			};
			
			YContainer.UserDialog.dialog.onClose = function() {
				YContainer.UserDialog.dialog.hide();
			};
			
			YContainer.UserDialog.datatable = myDataTable;
			YContainer.UserDialog.datasource = myDataSource;
			
		} else {
			YContainer.UserDialog.refresh();
		};
	};
	
	YContainer.UserDialog.refresh = function () {
		YContainer.UserDialog.currentRecord = null;
		YContainer.UserDialog.datatable.clearAll();
		YContainer.UserDialog.dialog.getButton(1).set('disabled', true);
		YContainer.UserDialog.datatable.datasource.load();
		YContainer.UserDialog.dialog.show();
	};
	
	YContainer.UserDialogForm.createAndShow = function(user) {
		YContainer.UserDialogForm.currentRecord = user;

		if(YContainer.UserDialogForm.dialog == null) {
			var html = getFile("../dialog/userForm.html");
			var elDiv = document.createElement("div");
			elDiv.innerHTML = html;
			document.body.appendChild(elDiv);
			
			YContainer.UserDialogForm.dialog = new Y.Panel({
				contentBox 		: "#UserDialogForm",
				headerContent	: "Utente",
				width : 360,
				zIndex : 1,
				centered : true,
				modal : true, // modal behavior
				render : true,
				visible : true, // make visible explicitly with .show()
				buttons : {
					footer : [
					          { name : 'cancel',label : 'Annulla', 	action : 'onCancel'},
					          { name : 'ok', 	label : 'Ok', 		action : 'onOk'}
					         ]
				}
			});
			
			var combo = Y.one('#userDialogForm_Role');
			addOption("Employee", combo, true);
			addOption("Manager", combo, true);
			
			YContainer.UserDialogForm.dialog.onCancel = function() {
				YContainer.UserDialogForm.dialog.hide();
			};
				
			YContainer.UserDialogForm.dialog.onOk = function() {
				YContainer.waitDialog.show();
				var user = YContainer.UserDialogForm.currentRecord;
				user.setUsername(Y.one('#userDialogForm_Username').get('value'));
				user.setRole(Y.one('#userDialogForm_Role').get('value'));
				SEM.BussinessMethos.saveBussinesObject(user);
				YContainer.UserDialogForm.dialog.hide();
				YContainer.waitDialog.hide();
				YContainer.UserDialog.refresh();
			};
			
		} else {
			YContainer.UserDialogForm.dialog.show();
		};
		
		Y.one('#userDialogForm_Username').set('value', user.getUsername());
		Y.one('#userDialogForm_Role').set('value', user.getRole());

		if(user.isNew())
			Y.one('#userDialogForm_Username').set('disabled', false);
		else
			Y.one('#userDialogForm_Username').set('disabled', true);
	};
	
	/******* EmployeeDialog ********/
	
	YContainer.EmployeeDialog.createAndShow = function() {
		if (YContainer.EmployeeDialog.dialog == null) {
			var html = getFile("../dialog/employees.html");
			var elDiv = document.createElement("div");
			elDiv.innerHTML = html;
			document.body.appendChild(elDiv);
			
			YContainer.EmployeeDialog.dialog = new Y.Panel({
				contentBox 		: "#employeesDialog",
				headerContent	: "Gestione Dipendenti",
				width : 750,
				zIndex : 1,
				centered : true,
				modal : true, // modal behavior
				render : true,
				visible : true, // make visible explicitly with .show()
				buttons : {
					footer : [
					          { name : 'joinOrder',	label : 'Associa Ordini',	action : 'onJoinOrder',	disabled : true},
					          { name : 'joinUser',	label : 'Associa Utenza',	action : 'onJoinUser',	disabled : true},
					          { name : 'add', 		label : 'Aggiungi', 		action : 'onAdd'},
					          { name : 'del', 		label : 'Elimina', 			action : 'onDel', disabled : true},
					          { name : 'close', 	label : 'Chiudi', 			action : 'onClose'}
					         ]
				}
			});
			
			YContainer.EmployeeDialog.dialog.plug(Y.Plugin.Resize);
			
			YContainer.EmployeeDialog.dialog.plug(Y.Plugin.Drag, {
			    handles: [
			        '.yui3-widget-hd'
			    ]
			});
			
			var myColumnDef = [
			                { key : "id",		label : "ID",		sortable : true, 	resizeable:false, width: 50},
			                { key : "surname",	label : "Cognome",	sortable : true, 	resizeable:false, width: 200},
			   			    { key : "name",		label : "Nome",		sortable : true, 	resizeable:false, width: 200},
			   			    { key : "role",		label : "Ruolo",	sortable : false,	resizeable:false },
			   			    { key : "username",	label : "Username",	sortable : true, 	resizeable:true, width: 50, allowHTML: true,
			   			    	formatter: function(o) {
			   			    		return o.value == null ? "N.A." : o.value;
			   			    	}
			   			    },
			   			    { key : "nOrders",  label : "N. Ordini",sortable : true,	resizeable:false, width: 50,
			   			    	formatter: function(o){
			   			    		var orders = SEM.BussinessMethos.getNOrder(o.data.id);
			   			    		var count = orders.responseXML.childNodes[0].childElementCount;
			   			    		return orders.responseXML.childNodes[0].childElementCount;
			   		             }
			   		        }
			   			   ];
			var myDataSource = new Y.DataSource.IO({
				source: server + 'employee'
			});
			
			myDataSource.plug(Y.Plugin.DataSourceXMLSchema, {
			    schema: {
			        resultListLocator: "employee",
			        resultFields: [
			           {key:"id", locator:"id"},
			           {key:"surname", locator:"surname"},
			           {key:"name", locator:"name"},
                       {key:"role", locator:"role"},
                       {key:"username", locator:"user/username"}
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
		        sortBy	: { surname: 'desc' }
		    });
			
			myDataTable.plug(Y.Plugin.DataTableDataSource, {
			    datasource: myDataSource,
			    initialRequest: ""
			});
			
			myDataSource.after("response", function() {
				myDataTable.render("#employeesDataTable");
				});
			
			myDataTable.delegate('click', function (e) {
				if(YContainer.EmployeeDialog.currentRecord != this.getRecord(e.currentTarget)){
					YContainer.EmployeeDialog.currentRecord = this.getRecord(e.currentTarget);
				}
				
				YContainer.EmployeeDialog.dialog.getButton(0).set('disabled', false);
				YContainer.EmployeeDialog.dialog.getButton(1).set('disabled', false);
				YContainer.EmployeeDialog.dialog.getButton(3).set('disabled', false);


			}, '.yui3-datatable-data tr', myDataTable);
			
			myDataTable.delegate('dblclick', function (e) {
				var record = YContainer.EmployeeDialog.currentRecord;
				var employee = new SEM.BussinessObject.Employee(record.get("id"));
				YContainer.EmployeeDialogForm.createAndShow(employee);
		     }, '.yui3-datatable-data tr', myDataTable);
			
			YContainer.EmployeeDialog.dialog.onJoinUser = function() {
				var record = YContainer.EmployeeDialog.currentRecord;
				YContainer.SelectUserDialog.createAndShow(record, 'employee');
			};
			
			YContainer.EmployeeDialog.dialog.onJoinOrder = function() {
				var record = YContainer.EmployeeDialog.currentRecord;
				Y.log("onJoinOrder:" + record.get('id'));
				YContainer.SelectOrderDialog.createAndShow(record);
			};
			
			YContainer.EmployeeDialog.dialog.onAdd = function() {
				var employee = new SEM.BussinessObject.Employee();
				YContainer.EmployeeDialogForm.createAndShow(employee);
			};
			
			YContainer.EmployeeDialog.dialog.onDel = function() {
				var record = YContainer.EmployeeDialog.currentRecord;
				onAlertMessage("Sicuro di voler eliminare il dipendente  <b>" + record.get("id") + "</b>?", { 
					yes: {
						  fn: function(o) {
							  var employee = new SEM.BussinessObject.Employee(record.get("id"));
							  employee.setToDelete(true);
							  SEM.BussinessMethos.saveBussinesObject(employee);
							  YContainer.EmployeeDialog.refresh();
							}, 
						  obj: record
						}
				});
			};
			
			YContainer.EmployeeDialog.dialog.onClose = function() {
				YContainer.EmployeeDialog.dialog.hide();
			};
			
			YContainer.EmployeeDialog.datatable = myDataTable;
			YContainer.EmployeeDialog.datasource = myDataSource;
		} else {
			YContainer.EmployeeDialog.refresh();
		};
	};
	
	YContainer.EmployeeDialog.refresh = function () {
		YContainer.EmployeeDialog.currentRecord = null;
		YContainer.EmployeeDialog.datatable.clearAll();
		YContainer.EmployeeDialog.dialog.getButton(0).set('disabled', true);
		YContainer.EmployeeDialog.dialog.getButton(1).set('disabled', true);
		YContainer.EmployeeDialog.dialog.getButton(3).set('disabled', true);
		YContainer.EmployeeDialog.datatable.datasource.load();
		YContainer.EmployeeDialog.dialog.show();
	};
	
	YContainer.EmployeeDialogForm.createAndShow = function(employee) {
		YContainer.EmployeeDialogForm.currentRecord = employee;
		
		if(YContainer.EmployeeDialogForm.dialog == null) {
			var html = getFile("../dialog/employeeForm.html");
			var elDiv = document.createElement("div");
			elDiv.innerHTML = html;
			document.body.appendChild(elDiv);
			
			YContainer.EmployeeDialogForm.dialog = new Y.Panel({
				contentBox 		: "#EmployeeDialogForm",
				headerContent	: "Dipendente",
				width : 360,
				zIndex : 1,
				centered : true,
				modal : true, // modal behavior
				render : true,
				visible : true, // make visible explicitly with .show()
				buttons : {
					footer : [
					          { name : 'cancel',label : 'Annulla', 	action : 'onCancel'},
					          { name : 'ok', 	label : 'Ok', 		action : 'onOk'}
					         ]
				}
			});
			
			YContainer.EmployeeDialogForm.dialog.onCancel = function() {
				YContainer.EmployeeDialogForm.dialog.hide();
			};
			
			YContainer.EmployeeDialogForm.dialog.onOk = function() {
				YContainer.waitDialog.show();
				var employee = YContainer.EmployeeDialogForm.currentRecord;
				employee.setName(Y.one('#employeeDialogForm_Name').get('value'));
				employee.setSurname(Y.one('#employeeDialogForm_Surname').get('value'));
				employee.setRole(Y.one('#employeeDialogForm_Role').get('value'));
				SEM.BussinessMethos.saveBussinesObject(employee);
				YContainer.EmployeeDialogForm.dialog.hide();
				YContainer.waitDialog.hide();
				YContainer.EmployeeDialog.refresh();
			};
		} else {
			YContainer.EmployeeDialogForm.dialog.show();
		};
		
		Y.one('#employeeDialogForm_Name').set('value', employee.getName());
		Y.one('#employeeDialogForm_Surname').set('value', employee.getSurname());
		Y.one('#employeeDialogForm_Role').set('value', employee.getRole());
	};
	
	YContainer.SelectOrderDialog.createAndShow = function(employee) {
		YContainer.SelectOrderDialog.employee = employee;
		if(YContainer.SelectOrderDialog.dialog == null) {
			var html = getFile("../dialog/selectOrders.html");
			var elDiv = document.createElement("div");
			elDiv.innerHTML = html;
			document.body.appendChild(elDiv);
			
			YContainer.SelectOrderDialog.dialog = new Y.Panel({
				contentBox 		: "#selectOrdersDialog",
				headerContent	: "Ordini",
				width : 600,
				zIndex : 1,
				centered : true,
				modal : true, // modal behavior
				render : true,
				visible : true, // make visible explicitly with .show()
				buttons : {
					footer : [
					          { name : 'select', 	label : 'Seleziona', 	action : 'onSelect', disabled : true },
					          { name : 'cancel', 	label : 'Annulla', 		action : 'onCancel'}
					         ]
				}
			});
			
			YContainer.SelectOrderDialog.dialog.plug(Y.Plugin.Resize);

			YContainer.SelectOrderDialog.dialog.plug(Y.Plugin.Drag, {
			    handles: [
			        '.yui3-widget-hd'
			    ]
			});
			
			var myColumnDef = [
					   		{ key : "id",			label : "ID",			sortable : true,	resizeable:true, width: 50 },
			   			    { key : "code",			label : "Cod. commessa",sortable : false,	resizeable:true, width: 100},
			   			    { key : "description",	label : "Descrizione",	sortable : true,	resizeable:true},
			   			    { key : "client",		label : "Cliente",		sortable : true, 	resizeable:true, width: 100}
				   			   ];
			var myDataSource = new Y.DataSource.IO({
				source: server + 'order'
			});
			
			myDataSource.plug(Y.Plugin.DataSourceXMLSchema, {
			    schema: {
			        resultListLocator: "order",
			        resultFields: [
			           {key:"id", locator:"id"},
                       {key:"code", locator:"code"},
                       {key:"description", locator:"description"},
                       {key:"client", locator:"client/companyName"}
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
		        sortBy	: { id: 'desc' }
		    });
			
			myDataTable.plug(Y.Plugin.DataTableDataSource, {
			    datasource: myDataSource,
			    initialRequest: ""
			});
			
			myDataSource.after("response", function() {
				myDataTable.render("#selectOrdersDataTable");
			});
			
			myDataTable.delegate('click', function (e) {
				if(YContainer.SelectOrderDialog.currentRecord != this.getRecord(e.currentTarget)){
					YContainer.SelectOrderDialog.currentRecord = this.getRecord(e.currentTarget);
				}
				YContainer.SelectOrderDialog.dialog.getButton(0).set('disabled', false);

			}, '.yui3-datatable-data tr', myDataTable);
			
			myDataTable.delegate('dblclick', function (e) {
				var record = YContainer.SelectOrderDialog.currentRecord;
				var employee = YContainer.SelectOrderDialog.employee;
				YContainer.SelectOrderDialog.join(employee.get('id'), record.get('id'));
				YContainer.SelectOrderDialog.dialog.hide();
		     }, '.yui3-datatable-data tr', myDataTable);
			
			YContainer.SelectOrderDialog.dialog.onSelect = function() {
				var record = YContainer.SelectOrderDialog.currentRecord;
				var employee = YContainer.SelectOrderDialog.employee;
				YContainer.SelectOrderDialog.join(employee.get('id'), record.get('id'));
				YContainer.SelectOrderDialog.dialog.hide();
			};
			
			YContainer.SelectOrderDialog.dialog.onCancel = function() {
				YContainer.SelectOrderDialog.dialog.hide();
				YContainer.SelectOrderDialog.currentRecord = null;
			};
			
			YContainer.SelectOrderDialog.datatable = myDataTable;
			YContainer.SelectOrderDialog.datasource = myDataSource;
			
		} else {
			YContainer.SelectOrderDialog.refresh();
		};
	};
	
	YContainer.SelectOrderDialog.refresh = function () {
		YContainer.SelectOrderDialog.currentRecord = null;
		YContainer.SelectOrderDialog.datatable.clearAll();
		YContainer.SelectOrderDialog.dialog.getButton(0).set('disabled', true);
		YContainer.SelectOrderDialog.datatable.datasource.load();
		YContainer.SelectOrderDialog.dialog.show();
	};
	
	YContainer.SelectOrderDialog.join = function (employeeId, orderId) {
		Y.log("Associazione ordine " + orderId + " con il dipendente " + employeeId);
		onAlertMessage("Sicuro di voler associare l'ordine  <b>" + orderId + "</b> con il dipendente <b>" + employeeId + "</b>?", { 
			yes: {
				  fn: function(o) {
					  var employeeOrder = new SEM.BussinessObject.EmployeeOrder();
					  employeeOrder.setEmployeeId(employeeId);
					  employeeOrder.setOrderId(orderId);
					  SEM.BussinessMethos.saveBussinesObject(employeeOrder);
					  YContainer.SelectOrderDialog.dialog.hide();
					  YContainer.EmployeeDialog.refresh();
					}
				}
		});
	};
	
	
/******* ManagerDialog ********/
	
	YContainer.ManagerDialog.createAndShow = function() {
		if (YContainer.ManagerDialog.dialog == null) {
			var html = getFile("../dialog/managers.html");
			var elDiv = document.createElement("div");
			elDiv.innerHTML = html;
			document.body.appendChild(elDiv);
			
			YContainer.ManagerDialog.dialog = new Y.Panel({
				contentBox 		: "#managersDialog",
				headerContent	: "Gestione Responsabili",
				width : 600,
				zIndex : 1,
				centered : true,
				modal : true, // modal behavior
				render : true,
				visible : true, // make visible explicitly with .show()
				buttons : {
					footer : [
					          { name : 'joinUser',	label : 'Associa Utenza',	action : 'onJoinUser',	disabled : true},
					          { name : 'add', 		label : 'Aggiungi', 		action : 'onAdd'},
					          { name : 'del', 		label : 'Elimina', 			action : 'onDel', disabled : true},
					          { name : 'close', 	label : 'Chiudi', 			action : 'onClose'}
					         ]
				}
			});
			
			YContainer.ManagerDialog.dialog.plug(Y.Plugin.Resize);
			
			YContainer.ManagerDialog.dialog.plug(Y.Plugin.Drag, {
			    handles: [
			        '.yui3-widget-hd'
			    ]
			});
			
			var myColumnDef = [
			                { key : "id",		label : "ID",		sortable : true, 	resizeable:false, width: 50},
			   			    { key : "role",		label : "Ruolo",	sortable : false,	resizeable:false },
			   			    { key : "username",	label : "Username",	sortable : true, 	resizeable:true, width: 50, allowHTML: true,
			   			    	formatter: function(o) {
			   			    		return o.value == null ? "N.A." : o.value;
			   			    	}
			   			    }
			   			   ];
			var myDataSource = new Y.DataSource.IO({
				source: server + 'manager'
			});
			
			myDataSource.plug(Y.Plugin.DataSourceXMLSchema, {
			    schema: {
			        resultListLocator: "manager",
			        resultFields: [
			           {key:"id", locator:"id"},
                       {key:"role", locator:"role"},
                       {key:"username", locator:"user/username"}
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
		        sortBy	: { surname: 'desc' }
		    });
			
			myDataTable.plug(Y.Plugin.DataTableDataSource, {
			    datasource: myDataSource,
			    initialRequest: ""
			});
			
			myDataSource.after("response", function() {
				myDataTable.render("#managersDataTable");
				});
			
			myDataTable.delegate('click', function (e) {
				if(YContainer.ManagerDialog.currentRecord != this.getRecord(e.currentTarget)){
					YContainer.ManagerDialog.currentRecord = this.getRecord(e.currentTarget);
				}
				
				YContainer.ManagerDialog.dialog.getButton(0).set('disabled', false);
				YContainer.ManagerDialog.dialog.getButton(2).set('disabled', false);


			}, '.yui3-datatable-data tr', myDataTable);
			
			myDataTable.delegate('dblclick', function (e) {
				var record = YContainer.ManagerDialog.currentRecord;
				var manager = new SEM.BussinessObject.Manager(record.get("id"));
				YContainer.ManagerDialogForm.createAndShow(manager);
		     }, '.yui3-datatable-data tr', myDataTable);
			
			YContainer.ManagerDialog.dialog.onJoinUser = function() {
				var record = YContainer.ManagerDialog.currentRecord;
				YContainer.SelectUserDialog.createAndShow(record, 'manager');
			};
			
			YContainer.ManagerDialog.dialog.onAdd = function() {
				var manager = new SEM.BussinessObject.Manager();
				YContainer.ManagerDialogForm.createAndShow(manager);
			};
			
			YContainer.ManagerDialog.dialog.onDel = function() {
				var record = YContainer.ManagerDialog.currentRecord;
				onAlertMessage("Sicuro di voler eliminare il responsabile  <b>" + record.get("id") + "</b>?", { 
					yes: {
						  fn: function(o) {
							  var manager = new SEM.BussinessObject.Manager(record.get("id"));
							  manager.setToDelete(true);
							  SEM.BussinessMethos.saveBussinesObject(manager);
							  YContainer.ManagerDialog.refresh();
							}, 
						  obj: record
						}
				});
			};
			
			YContainer.ManagerDialog.dialog.onClose = function() {
				YContainer.ManagerDialog.dialog.hide();
			};
			
			YContainer.ManagerDialog.datatable = myDataTable;
			YContainer.ManagerDialog.datasource = myDataSource;
		} else {
			YContainer.ManagerDialog.refresh();
		};
	};
	
	YContainer.ManagerDialog.refresh = function () {
		YContainer.ManagerDialog.currentRecord = null;
		YContainer.ManagerDialog.datatable.clearAll();
		YContainer.ManagerDialog.dialog.getButton(0).set('disabled', true);
		YContainer.ManagerDialog.dialog.getButton(2).set('disabled', true);
		YContainer.ManagerDialog.datatable.datasource.load();
		YContainer.ManagerDialog.dialog.show();
	};
	
	YContainer.ManagerDialogForm.createAndShow = function(manager) {
		YContainer.ManagerDialogForm.currentRecord = manager;
		
		if(YContainer.ManagerDialogForm.dialog == null) {
			var html = getFile("../dialog/managerForm.html");
			var elDiv = document.createElement("div");
			elDiv.innerHTML = html;
			document.body.appendChild(elDiv);
			
			YContainer.ManagerDialogForm.dialog = new Y.Panel({
				contentBox 		: "#ManagerDialogForm",
				headerContent	: "Responsabile",
				width : 360,
				zIndex : 1,
				centered : true,
				modal : true, // modal behavior
				render : true,
				visible : true, // make visible explicitly with .show()
				buttons : {
					footer : [
					          { name : 'cancel',label : 'Annulla', 	action : 'onCancel'},
					          { name : 'ok', 	label : 'Ok', 		action : 'onOk'}
					         ]
				}
			});
			
			YContainer.ManagerDialogForm.dialog.onCancel = function() {
				YContainer.ManagerDialogForm.dialog.hide();
			};
			
			YContainer.ManagerDialogForm.dialog.onOk = function() {
				YContainer.waitDialog.show();
				var manager = YContainer.ManagerDialogForm.currentRecord;
				manager.setRole(Y.one('#managerDialogForm_Role').get('value'));
				SEM.BussinessMethos.saveBussinesObject(manager);
				YContainer.ManagerDialogForm.dialog.hide();
				YContainer.waitDialog.hide();
				YContainer.ManagerDialog.refresh();
			};
		} else {
			YContainer.ManagerDialogForm.dialog.show();
		};
		
		Y.one('#managerDialogForm_Role').set('value', manager.getRole());

	};
	
	/**** SelectUserDialog *****/
	YContainer.SelectUserDialog.createAndShow = function(record, path) {
		YContainer.SelectUserDialog.record = record;
		
		if(YContainer.SelectUserDialog.dialog == null) {
			var html = getFile("../dialog/selectUsers.html");
			var elDiv = document.createElement("div");
			elDiv.innerHTML = html;
			document.body.appendChild(elDiv);
			
			YContainer.SelectUserDialog.dialog = new Y.Panel({
				contentBox 		: "#selectUsersDialog",
				headerContent	: "Utenze Disponibili",
				width : 300,
				zIndex : 10,
				centered : true,
				modal : true, // modal behavior
				render : true,
				visible : true, // make visible explicitly with .show()
				buttons : {
					footer : [
					          { name : 'select', 	label : 'Seleziona', 	action : 'onSelect', disabled : true },
					          { name : 'cancel', 	label : 'Annulla', 		action : 'onCancel'}
					         ]
				}
			});
			
			YContainer.SelectUserDialog.dialog.plug(Y.Plugin.Resize);
			
			YContainer.SelectUserDialog.dialog.plug(Y.Plugin.Drag, {
			    handles: [
			        '.yui3-widget-hd'
			    ]
			});
			
			var myColumnDef = [
			                   {key : "username", label : "Username", sortable : true, resizeable:true}
			                   ];
			
			var myDataSource = new Y.DataSource.IO({
				source: server + 'user/available?role=' + path + '&available=true'
			});
			
			myDataSource.plug(Y.Plugin.DataSourceXMLSchema, {
			    schema: {
			        resultListLocator: "user",
			        resultFields: [{key:"username", locator:"username"}]
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
				myDataTable.render("#selectUsersDataTable");
			});
			
			myDataTable.delegate('click', function (e) {
				if(YContainer.SelectUserDialog.currentRecord != this.getRecord(e.currentTarget)){
					YContainer.SelectUserDialog.currentRecord = this.getRecord(e.currentTarget);
				}
				YContainer.SelectUserDialog.dialog.getButton(0).set('disabled', false);

			}, '.yui3-datatable-data tr', myDataTable);
			
			myDataTable.delegate('dblclick', function (e) {
				var user = YContainer.SelectUserDialog.currentRecord;
				var record = YContainer.SelectUserDialog.record;
				if(path == 'manager')
					YContainer.SelectUserDialog.joinManager(record.get('id'), user.get('username'));
				else
					YContainer.SelectUserDialog.joinEmployee(record.get('id'), user.get('username'));
				
				YContainer.SelectUserDialog.dialog.hide();
		     }, '.yui3-datatable-data tr', myDataTable);
			
			YContainer.SelectUserDialog.dialog.onSelect = function() {
				var user = YContainer.SelectUserDialog.currentRecord;
				var record = YContainer.SelectUserDialog.record;
				
				if(path == 'manager')
					YContainer.SelectUserDialog.joinManager(record.get('id'), user.get('username'));
				else
					YContainer.SelectUserDialog.joinEmployee(record.get('id'), user.get('username'));				YContainer.SelectUserDialog.dialog.hide();
			};
			
			YContainer.SelectUserDialog.dialog.onCancel = function() {
				YContainer.SelectUserDialog.dialog.hide();
				YContainer.SelectUserDialog.currentRecord = null;
			};
			
			YContainer.SelectUserDialog.datatable = myDataTable;
			YContainer.SelectUserDialog.datasource = myDataSource;
		} else {
			YContainer.SelectUserDialog.refresh();
		};
	};
		
	YContainer.SelectUserDialog.refresh = function () {
		YContainer.SelectUserDialog.currentRecord = null;
		YContainer.SelectUserDialog.datatable.clearAll();
		YContainer.SelectUserDialog.dialog.getButton(0).set('disabled', true);
		YContainer.SelectUserDialog.datatable.datasource.load();
		YContainer.SelectUserDialog.dialog.show();
	};
	
	YContainer.SelectUserDialog.joinEmployee = function (employeeId, username) {
		Y.log("Associazione dipendente " + employeeId + " con l'utenza " + username);
		onAlertMessage("Sicuro di voler associare il dipendente  <b>" + employeeId + "</b> con l'utenza <b>" + username + "</b>?", { 
			yes: {
				  fn: function(o) {
					  var employee = new SEM.BussinessObject.Employee(employeeId);
					  employee.setUsername(username);
					  SEM.BussinessMethos.saveBussinesObject(employee);
					  YContainer.SelectUserDialog.dialog.hide();
					  YContainer.EmployeeDialog.refresh();
					}
				}
		});
	};
	
	YContainer.SelectUserDialog.joinManager = function (managerId, username) {
		Y.log("Associazione responsabile " + managerId + " con l'utenza " + username);
		onAlertMessage("Sicuro di voler associare il responsabile  <b>" + managerId + "</b> con l'utenza <b>" + username + "</b>?", { 
			yes: {
				  fn: function(o) {
					  var manager = new SEM.BussinessObject.Manager(managerId);
					  manager.setUsername(username);
					  SEM.BussinessMethos.saveBussinesObject(manager);
					  YContainer.SelectUserDialog.dialog.hide();
					  YContainer.ManagerDialog.refresh();
					}
				}
		});
	};
		
	
	/******* ClientDialog ********/
	YContainer.ClientDialog.createAndShow = function() {
		if (YContainer.ClientDialog.dialog == null) {
			var html = getFile("../dialog/clients.html");
			var elDiv = document.createElement("div");
			elDiv.innerHTML = html;
			document.body.appendChild(elDiv);
			
			YContainer.ClientDialog.dialog = new Y.Panel({
				contentBox 		: "#clientsDialog",
				headerContent	: "Gestione Clienti",
				width : 600,
				zIndex : 1,
				centered : true,
				modal : true, // modal behavior
				render : true,
				visible : true, // make visible explicitly with .show()
				buttons : {
					footer : [
					          { name : 'add', 	label : 'Aggiungi', action : 'onAdd'},
					          { name : 'del', 	label : 'Elimina', 	action : 'onDel', disabled : true},
					          { name : 'close', label : 'Chiudi', 	action : 'onClose'}
					         ]
				}
			});
			
			YContainer.ClientDialog.dialog.plug(Y.Plugin.Resize);

			YContainer.ClientDialog.dialog.plug(Y.Plugin.Drag, {
			    handles: [
			        '.yui3-widget-hd'
			    ]
			});
			
			var myColumnDef = [
				   			{ key : "id",				label : "ID",			sortable : true,	resizeable:true, width: 50 },
			   			    { key : "companyName",		label : "Nome",			sortable : true,	resizeable:true },
			   			    { key : "registeredOffice",	label : "Rag. Sociale",	sortable : true, 	resizeable:true },
			   			    { key : "address",			label : "Indirizzo",	sortable : false, 	resizeable:true },
			   			    { key : "telephone",		label : "Telefono",		sortable : false,	resizeable:true },
			   			    { key : "description",		label : "Descrizione",	sortable : false,	resizeable:true }
			   			   ];
			var myDataSource = new Y.DataSource.IO({
				source: server + 'client'
			});
			
			myDataSource.plug(Y.Plugin.DataSourceXMLSchema, {
			    schema: {
			        resultListLocator: "client",
			        resultFields: [
			           {key:"id", locator:"id"},
                       {key:"companyName", locator:"companyName"},
                       {key:"registeredOffice", locator:"registeredOffice"},
                       {key:"address", locator:"address"},
                       {key:"telephone", locator:"telephone"},
                       {key:"description", locator:"description"}
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
		        sortBy	: { companyName: 'desc' }
		    });
			
			myDataTable.plug(Y.Plugin.DataTableDataSource, {
			    datasource: myDataSource,
			    initialRequest: ""
			});
			
			myDataSource.after("response", function() {
				myDataTable.render("#clientsDataTable");
				});
			
			myDataTable.delegate('click', function (e) {
				if(YContainer.ClientDialog.currentRecord != this.getRecord(e.currentTarget)){
					YContainer.ClientDialog.currentRecord = this.getRecord(e.currentTarget);
					YContainer.ClientDialog.dialog.getButton(1).set('disabled', false);
				}

			}, '.yui3-datatable-data tr', myDataTable);
			
			myDataTable.delegate('dblclick', function (e) {
				var record = YContainer.ClientDialog.currentRecord;
				var client = new SEM.BussinessObject.Client(record.get("id"));
				YContainer.ClientDialogForm.createAndShow(client);
		     }, '.yui3-datatable-data tr', myDataTable);
			
			YContainer.ClientDialog.dialog.onAdd = function() {
				var client = new SEM.BussinessObject.Client();
				YContainer.ClientDialogForm.createAndShow(client);
			};
			
			YContainer.ClientDialog.dialog.onDel = function() {
				var record = YContainer.ClientDialog.currentRecord;
				onAlertMessage("Sicuro di voler eliminare l'utente  <b>" + record.get("companyName") + "</b>?", { 
					yes: {
						  fn: function(o) {
							  var client = new SEM.BussinessObject.Client(record.get("id"));
							  client.setToDelete(true);
							  SEM.BussinessMethos.saveBussinesObject(client);
							  YContainer.ClientDialog.refresh();
							}, 
						  obj: record
						}
				});
			};
			
			YContainer.ClientDialog.dialog.onClose = function() {
				YContainer.ClientDialog.dialog.hide();
			};
			
			YContainer.ClientDialog.datatable = myDataTable;
			YContainer.ClientDialog.datasource = myDataSource;
		} else {
			YContainer.ClientDialog.refresh();
		};
	};
	
	YContainer.ClientDialog.refresh = function () {
		YContainer.ClientDialog.currentRecord = null;
		YContainer.ClientDialog.datatable.clearAll();
		YContainer.ClientDialog.dialog.getButton(1).set('disabled', true);
		YContainer.ClientDialog.datatable.datasource.load();
		YContainer.ClientDialog.dialog.show();
	};
	
	YContainer.SimpleClientDialogForm.createAndShow = function(client) {
		if (client && typeof client === 'number') {
			client = new SEM.BussinessObject.Client(client.toString());
	    }
		
		YContainer.SimpleClientDialogForm.currentRecord = client;
		if(YContainer.SimpleClientDialogForm.dialog == null) {
			var html = getFile("../dialog/simpleClientForm.html");
			var elDiv = document.createElement("div");
			elDiv.innerHTML = html;
			document.body.appendChild(elDiv);
			
			YContainer.SimpleClientDialogForm.dialog = new Y.Panel({
				contentBox 		: "#SimpleClientDialogForm",
				headerContent	: "Cliente",
				width : 360,
				zIndex : 1,
				centered : true,
				modal : true, // modal behavior
				render : true,
				visible : true, // make visible explicitly with .show()
			});
		} else {
			YContainer.SimpleClientDialogForm.dialog.show();
		};
		
		Y.all('#SimpleClientDialogForm input').set('disabled', true);
		Y.one('#simpleClientDialogForm_CName').set('value', client.getCompanyName());
		Y.one('#simpleClientDialogForm_ROffice').set('value', client.getRegisteredOffice());
		Y.one('#simpleClientDialogForm_Address').set('value', client.getAddress());
		Y.one('#simpleClientDialogForm_Telephone').set('value', client.getTelephone());
		Y.one('#simpleClientDialogForm_Descr').set('value', client.getDescription());
	};
	
	
	YContainer.ClientDialogForm.createAndShow = function(client) {
		if (client && typeof client === 'number') {
			client = new SEM.BussinessObject.Client(client.toString());
	    }
		
		YContainer.ClientDialogForm.currentRecord = client;

		if(YContainer.ClientDialogForm.dialog == null) {
			var html = getFile("../dialog/clientForm.html");
			var elDiv = document.createElement("div");
			elDiv.innerHTML = html;
			document.body.appendChild(elDiv);
			
			YContainer.ClientDialogForm.dialog = new Y.Panel({
				contentBox 		: "#ClientDialogForm",
				headerContent	: "Cliente",
				width : 360,
				zIndex : 1,
				centered : true,
				modal : true, // modal behavior
				render : true,
				visible : true, // make visible explicitly with .show()
				buttons : {
					footer : [
					          { name : 'cancel',label : 'Annulla', 	action : 'onCancel'},
					          { name : 'ok', 	label : 'Ok', 		action : 'onOk'}
					         ]
				}
			});
			
			YContainer.ClientDialogForm.dialog.onCancel = function() {
				YContainer.ClientDialogForm.dialog.hide();
			};
			
			YContainer.ClientDialogForm.dialog.onOk = function() {
				YContainer.waitDialog.show();
				var client = YContainer.ClientDialogForm.currentRecord;
				client.setCompanyName(Y.one('#clientDialogForm_CName').get('value'));
				client.setRegisteredOffice(Y.one('#clientDialogForm_ROffice').get('value'));
				client.setAddress(Y.one('#clientDialogForm_Address').get('value'));
				client.setTelephone(Y.one('#clientDialogForm_Telephone').get('value'));
				client.setDescription(Y.one('#clientDialogForm_Descr').get('value'));
				SEM.BussinessMethos.saveBussinesObject(client);
				YContainer.ClientDialogForm.dialog.hide();
				YContainer.waitDialog.hide();
				YContainer.ClientDialog.refresh();
			};
		} else {
			YContainer.ClientDialogForm.dialog.show();
		};
		
		Y.one('#clientDialogForm_CName').set('value', client.getCompanyName());
		Y.one('#clientDialogForm_ROffice').set('value', client.getRegisteredOffice());
		Y.one('#clientDialogForm_Address').set('value', client.getAddress());
		Y.one('#clientDialogForm_Telephone').set('value', client.getTelephone());
		Y.one('#clientDialogForm_Descr').set('value', client.getDescription());
	};
	
	/***************************** OrderDialog *********************/
	YContainer.OrderDialog.createAndShow = function() {
		if (YContainer.OrderDialog.dialog == null) {
			var html = getFile("../dialog/orders.html");
			var elDiv = document.createElement("div");
			elDiv.innerHTML = html;
			document.body.appendChild(elDiv);
			
			YContainer.OrderDialog.dialog = new Y.Panel({
				contentBox 		: "#ordersDialog",
				headerContent	: "Gestione Ordini",
				width : 650,
				zIndex : 1,
				centered : true,
				modal : true, // modal behavior
				render : true,
				visible : true, // make visible explicitly with .show()
				buttons : {
					footer : [
					          { name : 'editOrder', label : 'Modifica Ordine', 	action : 'onEditOrder',	disabled : true},
					          { name : 'add', 		label : 'Aggiungi', 		action : 'onAdd'},
					          { name : 'del', 		label : 'Elimina', 			action : 'onDel', 		disabled : true},
					          { name : 'close', 	label : 'Chiudi', 			action : 'onClose'}
					         ]
				}
			});
			
			YContainer.OrderDialog.dialog.plug(Y.Plugin.Resize);
			
			YContainer.OrderDialog.dialog.plug(Y.Plugin.Drag, {
			    handles: [
			        '.yui3-widget-hd'
			    ]
			});
			
			YContainer.OrderDialog.getClient = function(o) {
				if(o.value == null)
					return "(nessuno)";
				return"<a href='javascript:YContainer.SimpleClientDialogForm.createAndShow("+o.value+");'>"+o.value+"</a>";
			};

			var myColumnDef = [
				   			{ key : "id",			label : "ID",			sortable : true,	resizeable:true, width: 50 },
			   			    { key : "code",			label : "Cod. commessa",sortable : true,	resizeable:true, width: 100},
			   			    { key : "description",	label : "Descrizione",	sortable : true,	resizeable:true},
			   			    { key : "client",		label : "Id Cliente",	sortable : true, 	resizeable:true, width: 50, formatter: YContainer.OrderDialog.getClient, allowHTML: true}
			   			   ];
			var myDataSource = new Y.DataSource.IO({
				source: server + 'order'
			});
			
			myDataSource.plug(Y.Plugin.DataSourceXMLSchema, {
			    schema: {
			        resultListLocator: "order",
			        resultFields: [
                       {key:"id", locator:"id"},
                       {key:"code", locator:"code"},
                       {key:"description", locator:"description"},
                       {key:"client", locator:"client/id"}
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
		        sortBy	: { description: 'desc' }
		    });
			
			myDataTable.plug(Y.Plugin.DataTableDataSource, {
			    datasource: myDataSource,
			    initialRequest: ""
			});
			
			myDataSource.after("response", function() {
				myDataTable.render("#ordersDataTable");
			});
			
			myDataTable.delegate('click', function (e) {
				if(YContainer.OrderDialog.currentRecord != this.getRecord(e.currentTarget)){
					YContainer.OrderDialog.currentRecord = this.getRecord(e.currentTarget);
				}
				
				YContainer.OrderDialog.dialog.getButton(0).set('disabled', false);
				YContainer.OrderDialog.dialog.getButton(2).set('disabled', false);

			}, '.yui3-datatable-data tr', myDataTable);
			
			myDataTable.delegate('dblclick', function (e) {
				var record = YContainer.OrderDialog.currentRecord;
				var order = new SEM.BussinessObject.Order(record.get("id"));
				YContainer.OrderDialogForm.createAndShow(order);
		     }, '.yui3-datatable-data tr', myDataTable);
			
			YContainer.OrderDialog.dialog.onEditOrder = function() {
				var record = YContainer.OrderDialog.currentRecord;
				YContainer.SelectClientDialog.createAndShow(record);
			};
			
			YContainer.OrderDialog.dialog.onAdd = function() {
				var order = new SEM.BussinessObject.Order();
				YContainer.OrderDialogForm.createAndShow(order);
			};
			
			YContainer.OrderDialog.dialog.onDel = function() {
				var record = YContainer.OrderDialog.currentRecord;
				onAlertMessage("Sicuro di voler eliminare l'ordine  <b>" + record.get("id") + "</b>?", { 
					yes: {
						  fn: function(o) {
							  var order = new SEM.BussinessObject.Order(record.get("id"));
							  order.setToDelete(true);
							  SEM.BussinessMethos.saveBussinesObject(order);
							  YContainer.OrderDialog.refresh();
							}, 
						  obj: record
						}
				});
			};
			
			YContainer.OrderDialog.dialog.onClose = function() {
				YContainer.OrderDialog.dialog.hide();
			};
			
			YContainer.OrderDialog.datatable = myDataTable;
			YContainer.OrderDialog.datasource = myDataSource;
			
		} else {
			YContainer.OrderDialog.refresh();
		};
	};
	
	YContainer.OrderDialog.refresh = function () {
		YContainer.OrderDialog.currentRecord = null;
		YContainer.OrderDialog.datatable.clearAll();
		YContainer.OrderDialog.dialog.getButton(0).set('disabled', true);
		YContainer.OrderDialog.dialog.getButton(2).set('disabled', true);
		YContainer.OrderDialog.datatable.datasource.load();
		YContainer.OrderDialog.dialog.show();
	};
		
	YContainer.OrderDialogForm.createAndShow = function(order) {
		YContainer.OrderDialogForm.currentRecord = order;
		
		if(YContainer.OrderDialogForm.dialog == null) {
			var html = getFile("../dialog/orderForm.html");
			var elDiv = document.createElement("div");
			elDiv.innerHTML = html;
			document.body.appendChild(elDiv);
			
			YContainer.OrderDialogForm.dialog = new Y.Panel({
				contentBox 		: "#OrderDialogForm",
				headerContent	: "Ordine",
				width : 360,
				zIndex : 1,
				centered : true,
				modal : true, // modal behavior
				render : true,
				visible : true, // make visible explicitly with .show()
				buttons : {
					footer : [
					          { name : 'cancel',label : 'Annulla', 	action : 'onCancel'},
					          { name : 'ok', 	label : 'Ok', 		action : 'onOk'}
					         ]
				}
			});
			
			YContainer.OrderDialogForm.dialog.onCancel = function() {
				YContainer.OrderDialogForm.dialog.hide();
			};
			
			YContainer.OrderDialogForm.dialog.onOk = function() {
				YContainer.waitDialog.show();
				var order = YContainer.OrderDialogForm.currentRecord;
				order.setDescription(Y.one('#orderDialogForm_Descr').get('value'));
				order.setCode(Y.one('#orderDialogForm_Code').getHTML());
				SEM.BussinessMethos.saveBussinesObject(order);
				YContainer.OrderDialogForm.dialog.hide();
				YContainer.waitDialog.hide();
				YContainer.OrderDialog.refresh();
			};
		} else {
			YContainer.OrderDialogForm.dialog.show();
		};
		
		Y.one('#orderDialogForm_Descr').set('value', order.getDescription());
		Y.one('#orderDialogForm_Code').setHTML(order.getCode());
	};
	
	YContainer.SelectClientDialog.createAndShow = function(order) {
		YContainer.SelectClientDialog.order = order;
		if(YContainer.SelectClientDialog.dialog == null) {
			var html = getFile("../dialog/selectClients.html");
			var elDiv = document.createElement("div");
			elDiv.innerHTML = html;
			document.body.appendChild(elDiv);
			
			YContainer.SelectClientDialog.dialog = new Y.Panel({
				contentBox 		: "#selectClientsDialog",
				headerContent	: "Clienti",
				width : 600,
				zIndex : 1,
				centered : true,
				modal : true, // modal behavior
				render : true,
				visible : true, // make visible explicitly with .show()
				buttons : {
					footer : [
					          { name : 'select', 	label : 'Seleziona', 	action : 'onSelect', disabled : true },
					          { name : 'cancel', 	label : 'Annulla', 		action : 'onCancel'}
					         ]
				}
			});
			
			YContainer.SelectClientDialog.dialog.plug(Y.Plugin.Resize);

			YContainer.SelectClientDialog.dialog.plug(Y.Plugin.Drag, {
			    handles: [
			        '.yui3-widget-hd'
			    ]
			});
			
			var myColumnDef = [
					   			{ key : "id",				label : "ID",			sortable : true,	resizeable:true, width: 50 },
				   			    { key : "companyName",		label : "Nome",			sortable : true,	resizeable:true },
				   			    { key : "registeredOffice",	label : "Rag. Sociale",	sortable : true, 	resizeable:true },
				   			    { key : "address",			label : "Indirizzo",	sortable : false, 	resizeable:true },
				   			    { key : "telephone",		label : "Telefono",		sortable : false,	resizeable:true },
				   			    { key : "description",		label : "Descrizione",	sortable : false,	resizeable:true }
				   			   ];
			var myDataSource = new Y.DataSource.IO({
				source: server + 'client'
			});
			
			myDataSource.plug(Y.Plugin.DataSourceXMLSchema, {
			    schema: {
			        resultListLocator: "client",
			        resultFields: [
			           {key:"id", locator:"id"},
                       {key:"companyName", locator:"companyName"},
                       {key:"registeredOffice", locator:"registeredOffice"},
                       {key:"address", locator:"address"},
                       {key:"telephone", locator:"telephone"},
                       {key:"description", locator:"description"}
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
		        sortBy	: { companyName: 'desc' }
		    });
			
			myDataTable.plug(Y.Plugin.DataTableDataSource, {
			    datasource: myDataSource,
			    initialRequest: ""
			});
			
			myDataSource.after("response", function() {
				myDataTable.render("#selectClientsDataTable");
			});
			
			myDataTable.delegate('click', function (e) {
				if(YContainer.SelectClientDialog.currentRecord != this.getRecord(e.currentTarget)){
					YContainer.SelectClientDialog.currentRecord = this.getRecord(e.currentTarget);
				}
				YContainer.SelectClientDialog.dialog.getButton(0).set('disabled', false);

			}, '.yui3-datatable-data tr', myDataTable);
			
			myDataTable.delegate('dblclick', function (e) {
				var record = YContainer.SelectClientDialog.currentRecord;
				var order = YContainer.SelectClientDialog.order;
				YContainer.SelectClientDialog.join(order.get('id'), record.get('id'));
				YContainer.SelectClientDialog.dialog.hide();
		     }, '.yui3-datatable-data tr', myDataTable);
			
			YContainer.SelectClientDialog.dialog.onSelect = function() {
				var record = YContainer.SelectClientDialog.currentRecord;
				var order = YContainer.SelectClientDialog.order;
				YContainer.SelectClientDialog.join(order.get('id'), record.get('id'));
				YContainer.SelectClientDialog.dialog.hide();
			};
			
			YContainer.SelectClientDialog.dialog.onCancel = function() {
				YContainer.SelectClientDialog.dialog.hide();
				YContainer.SelectClientDialog.currentRecord = null;
			};
			
			YContainer.SelectClientDialog.datatable = myDataTable;
			YContainer.SelectClientDialog.datasource = myDataSource;
			
		} else {
			YContainer.SelectClientDialog.refresh();
		};
	};
	
	YContainer.SelectClientDialog.refresh = function () {
		YContainer.SelectClientDialog.currentRecord = null;
		YContainer.SelectClientDialog.datatable.clearAll();
		YContainer.SelectClientDialog.dialog.getButton(0).set('disabled', true);
		YContainer.SelectClientDialog.datatable.datasource.load();
		YContainer.SelectClientDialog.dialog.show();
	};
	
	YContainer.SelectClientDialog.join = function (orderId, clientId) {
		Y.log("Associazione ordine " + orderId + " con il cliente " + clientId);
		onAlertMessage("Sicuro di voler associare l'ordine  <b>" + orderId + "</b> con il cliente <b>" + clientId + "</b>?", { 
			yes: {
				  fn: function(o) {
					  var order = new SEM.BussinessObject.Order(orderId);
					  order.setClientId(clientId);
					  SEM.BussinessMethos.saveBussinesObject(order);
					  YContainer.SelectClientDialog.dialog.hide();
					  YContainer.OrderDialog.refresh();
					}
				}
		});
	};
});
