
	var server = "";
	var username;
	var password;
	var role = "none";
	var originalRecordSet = "";
	YUI.namespace("Sem.container");
	
	var Y = YUI({debug: true}).use('*', function(Y) {
    // Any modules that were already loaded on the page statically will now be
    // attached and ready to use. YUI will not automatically load any modules
    // that weren't already on the page.
	});

	var YContainer	= YUI.Sem;
	
	/*YAHOO.util.Event.throwErrors = true;*/
	
	//Funzioni per la dialog LOGINDIALOG
	YContainer.loginDialog = {};
	YContainer.waitDialog = {};
	YContainer.tooltipDialog ={};
	YContainer.errorMessageDialog = {};
	YContainer.alertDialog = {};
	YContainer.TagLogDialog = {};
	YContainer.TagLogDialog.dialog = new Array();
	
	
	YContainer.HelpDialog = {};
	YContainer.promptDialogForm = {};
	
	YContainer.StatsDialog = {};
	
	/* Initialize and render the MenuBar when the page's DOM is ready to be scripted. */
	
	YUI().use("node", function(Y) {
			
		
		if (document.URL.indexOf("http://") > -1 || document.URL.indexOf("https://") > -1) {
			Y.one("#serverTR").setStyle("display", "none");
		};
		
		YContainer.loginDialog.callback = {
			  success: function(o) { 
					role = o.responseXML;
					var nodes = evalutateXPath(role, "root/roles"); 
					var vRoles = new Array();			
					for (var i=0; i < nodes.length; i++) {
						
						vRoles.push(getContentInElement(nodes[i]).toLowerCase());
					};
					$(".inapp_"+vRoles.join("_")).show();
					var sUser = getContentInElement(evalutateXPath(role, "root/user")[0]);
					YContainer.loginDialog.dialog.hide();
					YContainer.waitDialog.dialog.hide();
					
					//Con questo submit faccio chiedere al browser il salvataggio della password
					Y.one("#loginForm").submit();
					
					var idx = YContainer.menubar.getItems().length -1 ;
					YContainer.menubar.getItem(idx).cfg.setProperty("text", "Loggato come " + sUser +" ("+(vRoles.join(", "))+")");
				}, 
			  failure: function(o) {
					YContainer.loginDialog.dialog.show();
					YContainer.waitDialog.dialog.hide();
					onErrorMessage("Username e/o password errate");
				}
		};
		
		YContainer.loginDialog.handleLoginSubmit = function() {   		
			var input = YContainer.loginDialog.dialog.body.getElementsByTagName("input");
			username = input[0].value;
			password = input[1].value;
			YAHOO.util.Connect.syncRequest("GET", server + "manager/userroles" , YContainer.loginDialog.callback);
			YContainer.waitDialog.dialog.show();
			YContainer.loginDialog.dialog.hide();
			YAHOO.util.Connect.setDefaultPostHeader(true);
		};
		YContainer.loginDialog.handleAutoLoginSubmit = function() {   		
			YAHOO.util.Connect.asyncRequest("GET", server + "manager/userroles" , YContainer.loginDialog.callback);
			YContainer.waitDialog.dialog.show();
			YContainer.loginDialog.dialog.hide();
			YAHOO.util.Connect.setDefaultPostHeader(true);
		};
		
		YContainer.menubar = new YAHOO.widget.MenuBar("menubar");
		YContainer.menubar.render(document.body);
		
		YContainer.manager = new YAHOO.widget.OverlayManager();

		YContainer.loginDialog.dialog = new YAHOO.widget.Dialog("loginDialog", 
			{ 
				close: false,
				visible:false,
				width:"300px",
				modal: true,
				buttons: [
				      { text:"Accedi", handler: YContainer.loginDialog.handleLoginSubmit, isDefault:true } 
				]
			}
		);
		
		
		YContainer.loginDialog.dialog.render();
		YContainer.manager.register(YContainer.loginDialog.dialog);
		
		YContainer.waitDialog.stop = function(){
			YContainer.waitDialog.dialog.hide();
		};
		
		YContainer.waitDialog.dialog = new YAHOO.widget.Panel("waitDialog", 
			{ width: "240px", fixedcenter: true, close: false, draggable: false,
			  zindex:4, modal: true, visible: false }
		);
		
		YContainer.waitDialog.dialog.render();
		YContainer.manager.register(YContainer.waitDialog.dialog);

		YContainer.errorMessageDialog.dialog = new YAHOO.widget.Dialog("errorMessageDialog", 
				{ width:"350px", visible:false, draggable:true, close:false, fixedcenter : true, 
					constraintoviewport : true, zIndex: 1, modal : true, 
					buttons : [ { text:"Ok", handler: function() {YContainer.errorMessageDialog.dialog.hide(); }, isDefault:true } ] } 
		);
		YContainer.errorMessageDialog.dialog.render();
		YContainer.manager.register(YContainer.errorMessageDialog.dialog);

		YContainer.promptDialogForm.dialog = new YAHOO.widget.Dialog("promptDialogForm", 
				{ width:"350px", visible:false, draggable:true, close:false, fixedcenter : true, 
					constraintoviewport : true, zIndex: 1, modal : true, 
					buttons:[
					        	 { text:"Ok", isDefault:true }, 
					        	 { text:"Annulla", isDefault:true }
					]
		}); 
		YContainer.promptDialogForm.dialog.render();
		YContainer.promptDialogForm.dialog.getButtons()[0].subscribe("click", function() { YContainer.promptDialogForm.dialog.hide(); }); 
		YContainer.promptDialogForm.dialog.getButtons()[1].subscribe("click", function() { YContainer.promptDialogForm.dialog.hide(); });  
		YContainer.manager.register(YContainer.promptDialogForm.dialog);
		/*YAHOO.util.Event.addListener(YAHOO.util.Dom.get("promptDialogForm_Name"), "keyup", YContainer.promptDialogForm.checkChart);*/
		Y.on("keyup", YContainer.promptDialogForm.checkChart, Y.one("#promptDialogForm_Name"));
		
		YContainer.alertDialog.dialog = new YAHOO.widget.Dialog("alertDialog",
				{ width:"350px", visible: false, draggable: true, close:false, fixedcenter: true, 
					constraintoviewport: true, zIndex: 1, modal: true, 
					buttons : [ { text:"Si", isDefault: true },
								{ text:"No", isDefault: false } 
					]
				} 
		);
		YContainer.alertDialog.dialog.render();
		YContainer.alertDialog.dialog.getButtons()[0].subscribe("click", function() { YContainer.alertDialog.dialog.hide(); }); 
		YContainer.alertDialog.dialog.getButtons()[1].subscribe("click", function() { YContainer.alertDialog.dialog.hide(); });
		YContainer.manager.register(YContainer.alertDialog.dialog);
		
		/**/
		//BASTA LOGIN
		YContainer.loginDialog.dialog.show();
		YContainer.loginDialog.dialog.center(); 
		
		/**/
		//se si clicca sul titolo di una finestra nascondo i menu...
		YAHOO.util.Event.on(YContainer.loginDialog.dialog.element, "mousedown", 
				YContainer.menubar.clearActiveItem, null, YContainer.menubar);

		//Configuro il menu
		var idx = YContainer.menubar.getItems().length - 1;
		YContainer.menubar.getItem(idx).cfg.setProperty("disabled", true);
		
		
		if (YAHOO.util.History.getQueryStringParameter("username")){
			username = YAHOO.util.History.getQueryStringParameter("username");
			password = YAHOO.util.History.getQueryStringParameter("password");
			YContainer.loginDialog.handleAutoLoginSubmit();
		}
	});


	

	/***********************************************************************************************/
	/***********************************************************************************************/
	/***********************    Codice delle finestre    *******************************************/
	/***********************************************************************************************/
	
	/******************************  ALERT *********************************************************/
	function onAlertMessage(message, callback, args) {
		YContainer.alertDialog.dialog.setBody(message);
		YContainer.alertDialog.dialog.show();        
		YContainer.alertDialog.dialog.center(); 

		YContainer.alertDialog.dialog.getButtons()[0].set("onclick", null);
		YContainer.alertDialog.dialog.getButtons()[1].set("onclick", null);

		if (callback != null && callback.yes != null && callback.yes != undefined) {
			YContainer.alertDialog.dialog.getButtons()[0].set("onclick", callback.yes);
		};

		if (callback != null && callback.no != null && callback.no != undefined) {
			YContainer.alertDialog.dialog.getButtons()[1].set("onclick", callback.no);
		};
		
	};
	
	/******************************  Error message **************************************************/
	function onErrorMessage(message) {
		YContainer.errorMessageDialog.dialog.setHeader("Errore");
		YContainer.errorMessageDialog.dialog.setBody(message);
		YContainer.errorMessageDialog.dialog.show();        
		YContainer.errorMessageDialog.dialog.center(); 
	};

	/******************************Prompt per stringe***********************************************/
	onPromptMessage = function(title, message, label, callback, notValidChart) {
		Y.one("#promptDialogForm_Name").value = "";
		Y.one("#promptDialogForm_P").innerHTML = message;
		Y.one("#promptDialogForm_Label").innerHTML = label;
		YContainer.promptDialogForm.dialog.setHeader(title);
		YContainer.promptDialogForm.dialog.show();
		YContainer.promptDialogForm.dialog.center();
		
		YContainer.promptDialogForm.dialog.getButtons()[0].set("onclick", null);
		YContainer.promptDialogForm.dialog.getButtons()[1].set("onclick", null);

		if (callback != null && callback.ok != null && callback.ok != undefined) {
			YContainer.promptDialogForm.dialog.getButtons()[0].set("onclick", callback.ok);
		};

		if (callback != null && callback.cancel != null && callback.cancel != undefined) {
			YContainer.promptDialogForm.dialog.getButtons()[1].set("onclick", callback.cancel);
		};
		YContainer.promptDialogForm.notValidChart = notValidChart; 
		Y.one("#promptDialogForm_Name").focus();
	};
	
	YContainer.promptDialogForm.checkChart = function() {
		var input = Y.one("#promptDialogForm_Name");
		var notValidChart = YContainer.promptDialogForm.notValidChart; 
		if (notValidChart != undefined && notValidChart.constructor == Array) {
			for (var i in notValidChart) {
				if (input.value.indexOf(notValidChart[i]) > -1)
					input.value = input.value.replace(notValidChart[i], "");
			};
		};
	};
	 
    		
	/******************************* HELP ***************************************************/            
	YContainer.HelpDialog.createAndShow = function() {
		if (YContainer.HelpDialog.dialog == null) {
			var html = '<div id="helpDialog">' + 
				'<div class="hd">Help</div> ' +
				'<div class="bd"> ' +
					'<iframe src="help.html" style="width: 100%; height: 100%;"/> ' +
				 '</div>' + 
			  '</div>'; 

			var elDiv = document.createElement("div");
			elDiv.innerHTML = html;
			document.body.appendChild(elDiv); 
				
			YContainer.HelpDialog.dialog = new YAHOO.widget.Dialog("helpDialog",  
					{ width: "450px", height: "500px", fixedcenter: false, close: true, draggable:true, modal: false, visible: false, zIndex: 2, autofillheight: "body",
					  effect: { effect: YAHOO.widget.ContainerEffect.FADE, duration:0.5} } );
			YContainer.HelpDialog.dialog.render();
			YContainer.manager.register(YContainer.HelpDialog.dialog);

			var resize = new YAHOO.util.Resize("helpDialog", {
				handles: ["br"], autoRatio: false, minWidth: 300, minHeight: 100, status: false } );

			  resize.on("startResize", function(args) {
					if (this.cfg.getProperty("constraintoviewport")) {
					  var D = YAHOO.util.Dom;
					  var clientRegion = D.getClientRegion();
					  var elRegion = D.getRegion(this.element);
					  resize.set("maxWidth", clientRegion.right - elRegion.left - YAHOO.widget.Overlay.VIEWPORT_OFFSET);
					  resize.set("maxHeight", clientRegion.bottom - elRegion.top - YAHOO.widget.Overlay.VIEWPORT_OFFSET);
				  } else {
					  resize.set("maxWidth", null);
					  resize.set("maxHeight", null);
				};
			
			  }, YContainer.HelpDialog.dialog, true);
			
			  resize.on("resize", function(args) {
					var panelHeight = args.height;
					this.cfg.setProperty("height", panelHeight + "px"); }, 
					YContainer.HelpDialog.dialog, true);      

		};
		YContainer.HelpDialog.dialog.center();
		YContainer.HelpDialog.dialog.show();
	};