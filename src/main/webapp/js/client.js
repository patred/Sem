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
	success : function(mess) {
		// Do nothing
		console.log("success")
		YContainer.waitDialog.dialog.hide();
	},
	failure : function(mess) {
		console.log("failure dannaz!")
		var el = evalutateXPath(mess.responseXML, "desc");
		if (el.length > 0)
			onErrorMessage(getContentInElement(el.pop()));
		else
			onErrorMessage(mess.responseText);
	}
};

SEM.BussinessObject.BaseObject = function() {

	this.servletpath = "";
	this.todelete = false;
	this.isnew = false;

	this.init = function(data) {
		//var xhr = YAHOO.util.Connect.createXhrObject();
		var response;
		var url = server + this.servletpath;
		if (data == null) {
			this.isnew = true;
			url += "/new";
			console.log("GET " + url);
			response = syncRequest(url);
			this.data = response.responseXML.firstChild;
			this.xmldata = response.responseXML;
		} else if (typeof data == 'string') {
			if (data.indexOf("=") >= 0) {
				this.isnew = true;
				url += "/new?" + data;
			} else {
				if (data.length > 0) {
					url += "/" + data;
				};
			};
			console.log("GET " + url);
			response = syncRequest(url);
			this.data = response.responseXML.firstChild;
			this.xmldata = response.responseXML;
		} else if (typeof data == 'object') {
			console.log("this.data = " + data);
			this.data = data;
		};
	};

	this.verifyXmlNodes = function(xPath) {

		var vNodes = xPath.split("/");
		var myNode = this.xmldata.childNodes;

		for (var i = 0; i < vNodes.length; i++) {
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
			if (this.data.ownerDocument == node.base.data.ownerDocument) {
				this.data.appendChild(node.base.data);
			} else {
				this.data.appendChild(this.data.ownerDocument.importNode(
						node.base.data, true));
			};
		} else {

			if (this.data.ownerDocument == node.base.data.ownerDocument) {
				this.data.appendChild(node.base.data);
			} else {
				this.data.appendChild(this.data.ownerDocument.importNode(
						node.base.data, true));
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

	this.setToDelete = function(bool) {
		this.base.todelete = bool;
	};
	this.isToDelete = function() {
		return this.base.todelete;
	};
	this.isNew = function() {
		return this.base.isnew;
	};
	this.getData = function() {
		return this.data;
	};
	this.getParentNode = function() {
		return this.base.data.parentNode;
	};
	this.getXmldata = function() {
		return this.xmldata;
	};
};

/* Dichiarazioni oggetti di bussiness e access method */

SEM.BussinessObject.User = function (data) {
	/**
	 * Esempio di messaggio:
	 * 	<user>
	 * 		<employee>
	 * 			<id>2</id>
	 * 			<role>Java Developer Expert</role>
	 * 		</employee>
	 * 		<name>-</name>
	 * 		<password>OVw0md1U3Pmqr89YGjyj5vmj48w=</password>
	 * 		<role>Employee</role>
	 * 		<salt>jnyHV5Z4KPA=</salt>
	 * 		<surname>-</surname>
	 * 		<username>Employee</username>
	 * 	</user>
	 */
	this.base = new SEM.BussinessObject.BaseObject();
	this.base.servletpath = "backoffice/user";
	this.base.init(data);
	
	this.setToDelete = this.base.setToDelete;
	this.isToDelete = this.base.isToDelete;
	this.isNew = this.base.isNew;
	this.getData = this.base.getData;
	this.getParentNode = this.base.getParentNode;
	
	this.getUsername = function() 			{	return this.base.get("username"); 		};
	this.setUsername = function(username)	{	this.base.set("username", username);	};
	this.getSurname = function() 			{	return this.base.get("surname"); 		};
	this.setSurname = function(surname)		{	this.base.set("surname", surname);		};
	this.getSalt = function() 				{	return this.base.get("salt"); 			};
	this.setSalt = function(salt)			{	this.base.set("salt", salt);			};
	this.getRole = function() 				{	return this.base.get("role"); 			};
	this.setRole = function(role)			{	this.base.set("role", role);			};
	this.getName = function() 				{	return this.base.get("name"); 			};
	this.setName = function(name)			{	this.base.set("name", name);			};
	this.getId = function(role) 				{ 	return this.base.get(role.toLowerCase() + "/id");		};
	this.getRoleDescription = function(role) 	{	return this.base.get(role.toLowerCase() + "/role"); 	};
	this.setRoleDescription = function(role, des)	{	this.base.set(role.toLowerCase() + "/role", des); 	};
	
	this.getKey = function() {return this.getUsername();};
	this.getQKey = function() {return "username="+this.getUsername();};
};

SEM.BussinessObject.User = function (data) {
	this.base = new SEM.BussinessObject.BaseObject();
	this.base.servletpath = "backoffice/client";
	this.base.init(data);
	
	this.setToDelete = this.base.setToDelete;
	this.isToDelete = this.base.isToDelete;
	this.isNew = this.base.isNew;
	this.getData = this.base.getData;
	this.getParentNode = this.base.getParentNode;
	
	this.getId = function() 								{	return this.base.get("id"); 						};
	this.setId = function(id)								{	this.base.set("id", id);							};
	this.getCompanyName = function() 						{	return this.base.get("companyName"); 				};
	this.setCompanyName = function(companyName)				{	this.base.set("companyName", companyName);			};
	this.getRegisteredOffice = function() 					{	return this.base.get("registeredOffice"); 			};
	this.setRegisteredOffice = function(registeredOffice)	{	this.base.set("registeredOffice", registeredOffice);};
	this.getAddress = function() 							{	return this.base.get("address"); 					};
	this.setAddress = function(address)						{	this.base.set("address", address);					};
	this.getTelephone = function() 							{	return this.base.get("telephone"); 					};
	this.setTelephone = function(telephone)					{	this.base.set("telephone", telephone);				};
	this.getDescription = function() 						{	return this.base.get("description"); 				};
	this.setDescription = function(des)						{	this.base.set("description", description); 			};
	
	this.getKey = function() {return this.getId();};
	this.getQKey = function() {return "id="+this.getId();};
};

/**
 * ************************* Funzioni di accesso remoto
 * **************************************************
 */
/*
 * DELETE HTTP METHOD - delete POST HTTP METHOD - create PUT HTTP METHOD -
 * modify
 * 
 */
SEM.BussinessMethos.saveBussinesObject = function(object) {

	var key = object.getKey();

	var servletpath = object.base.servletpath;

	var method;
	var data = null;

	if (object.isToDelete()) {
		method = "DELETE";
		if (key) {
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

	return syncRequest(server + servletpath, method, SEM.BussinessMethos.genericCallback, data);
};