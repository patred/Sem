
if (typeof SEM == "undefined" || !SEM) {
	var SEM = {};
}

if (typeof SEM.BussinessObject == "undefined" || !SEM.BussinessObject) {
	SEM.BussinessObject = {};
}

if (typeof SEM.BussinessMethos == "undefined" || !SEM.BussinessMethos) {
	SEM.BussinessMethos = {};
}

SEM.BussinessMethos.genericCallback = {
    success: function(mess) {
		//Do nothing
		console.log("success")
		YContainer.waitDialog.dialog.hide();
	},
	failure: function(mess) {
		console.log("failure dannaz!")
		var el = evalutateXPath(mess.responseXML, "desc");
		if (el.length > 0)
			onErrorMessage(getContentInElement(el[0]));
		else
			onErrorMessage(mess.responseText);
    }   
};

SEM.BussinessMethos.uploadCallback = {
	    upload: function(o) {
	    	console.log(o.responseXML.childNodes[0].childNodes[1].childNodes[0].data);
	    	if(o.responseXML.childNodes[0].childNodes[0].childNodes[0].data != 400) {
	    		YContainer.ProductDialogForm.save(o.responseXML.childNodes[0].childNodes[1].childNodes[0].data);
	    	} else {
	    		YContainer.waitDialog.dialog.hide();
	    	}
		}
	};

SEM.BussinessObject.BaseObject = function() {
	
	this.servletpath = "";
	this.todelete = false;
	this.isnew = false;
	
	this.init = function(data) {
		var xhr = YAHOO.util.Connect.createXhrObject();
		var conn = xhr.conn;
		var url = server + this.servletpath;
		if (data == null) {
			this.isnew = true;
			url +="/new";
			conn.open("GET", url , false);
			YAHOO.util.Connect.setHeader(xhr);
			conn.send(null);
			
			this.data = conn.responseXML.firstChild;
			this.xmldata = conn.responseXML;
		}else if (typeof data == 'string') {
			if (data.indexOf("=")>=0) {
				this.isnew = true;
				url +="/new?"+data;
			}else{
				if (data.length > 0){
					url += "/" + data;
				}
				
			};
			conn.open("GET", url , false);
			YAHOO.util.Connect.setHeader(xhr);
			conn.send(null);
			this.data = conn.responseXML.firstChild;
			this.xmldata = conn.responseXML;
		}else if (typeof data == 'object'){
			this.data = data;
		};
	};
	
	this.verifyXmlNodes = function(xPath){
		
		var vNodes	= xPath.split("/");
		var myNode = this.xmldata.childNodes;

		for (var i=0; i<vNodes.length; i++){
			var myNodeNew = evalutateXPath(myNode, vNodes[i]);
			if (myNodeNew.length == 0) {
				myNode[0].appendChild(this.xmldata.createElement(vNodes[i]));
				myNodeNew = evalutateXPath(myNode, vNodes[i]);
			};
			myNode = myNodeNew;
		};
	};
	
	this.getNode = function(field) {
		var nodes = evalutateXPath(this.data, field);
		if (nodes.length > 0)
			return nodes[0];
		return null;
	};

	this.setNode = function(node) {
		var nodes = evalutateXPath(this.data, node.base.data.nodeName);
		if (nodes.length == 0) {
			if (this.data.ownerDocument ==  node.base.data.ownerDocument){
				this.data.appendChild(node.base.data);
			}else{
				this.data.appendChild(this.data.ownerDocument.importNode(node.base.data, true));
			};
		}else{
			
			if (this.data.ownerDocument ==  node.base.data.ownerDocument){
				this.data.appendChild(node.base.data);
			}else{
				this.data.appendChild(this.data.ownerDocument.importNode(node.base.data, true));
			};
		};
	};
	
	this.get = function(field) {
		var nodes = evalutateXPath(this.data, field);
		if (nodes.length > 0)
			return getContentInElement(nodes[0]);
		return null;
	};
	
	this.set = function(field, value) {
		this.verifyXmlNodes(field);
		var nodes = evalutateXPath(this.data, field);
		
		setContentInElement(nodes[0], value);
		
	};
	
	this.setToDelete = function(bool) 	{ 	this.base.todelete = bool; 			};
	this.isToDelete = function() 		{	return this.base.todelete; 			};
	this.isNew = function() 			{	return this.base.isnew; 			};
	this.getData = function() 			{	return this.data;					};
	this.getParentNode = function() 	{	return this.base.data.parentNode;	};
	this.getXmldata		= function() 	{	return this.xmldata;				};
};

/* Dichiarazioni oggetti di bussiness e access method */

/*TODO*/


/***************************   Funzioni di accesso remoto ***************************************************/
/*
 * DELETE HTTP METHOD - delete
 * POST HTTP METHOD - create
 * PUT HTTP METHOD - modify
 * 
 */
SEM.BussinessMethos.saveBussinesObject = function (object) {
	
	
	var key = object.getKey();

	var servletpath = object.base.servletpath;
	
	var method;
	var data = null;
	
	if (object.isToDelete()) { 	
		method = "DELETE";
		if (key){
			servletpath += "/" + key;
		}
	} else {
		data = object.base.xmldata;
		if (object.isNew()) {
			method = "POST";
		} else { 								 
			method = "PUT";
			
		}
	}
	
	return YAHOO.util.Connect.syncRequest(method, server + servletpath, SEM.BussinessMethos.genericCallback, data);
};