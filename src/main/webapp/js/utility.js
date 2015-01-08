/*
*  @autor: Giuseppe Cozzolino
*  @since: 06/02/2009
*
*/

var REspace = new RegExp(' ', "ig");
var REat = '@'; //new RegExp('@', "ig");
var REquote = new RegExp('\'', "ig");



function clone(obj){
    if(obj == null || typeof(obj) != 'object')
        return obj;

    var temp = new obj.constructor(); // changed (twice)
    for(var key in obj)
        temp[key] = clone(obj[key]);

    return temp;
}

function CheckCreateXmlNodes(objXml, xPath){
		
	var vNodes	= xPath.split("/");
	var myNode = objXml.childNodes;

	for (var i=0; i<vNodes.length; i++) {
		var myNodeNew = evalutateXPath(myNode, vNodes[i]);
		if (myNodeNew.length == 0) {
			myNode[0].appendChild(objXml.createElement(vNodes[i]));
			myNodeNew = evalutateXPath(myNode, vNodes[i]);
		};
		myNode = myNodeNew;
	};
};


function ToString(obj){
	if (obj==undefined || obj==null){
		
		return "";
	}else{
		return obj.toString();
	};
};
function ToInt(obj){
	if (obj==undefined || obj==null || isNaN(parseInt(obj))){
		
		return 0;
	}else{
		return parseInt(obj);
	};
};

var formatUTCDate = function(elLiner, oRecord, oColumn, oData) {
	elLiner.innerHTML = oData;
};

var insertInCData= function(str) {
	return "<![CDATA[" + str + "]]>";
};

var extractCData = function(str) {
	if (str){
		if (str.indexOf("<![CDATA[") == 0){
			return str.substr(9, str.length-12);
		}
		return str ;
	};
	return "" ;
};

var formatHTMLcode = function(elLiner, oRecord, oColumn, oData) {
	
	var a = document.createTextNode(extractCData(oData));
	elLiner.appendChild(a);
	
};

var formatUTCDateHours = function(elLiner, oRecord, oColumn, oData) {
	elLiner.innerHTML = oData;
};

var formatSiNo = function(elLiner, oRecord, oColumn, oData) { 
	if (oData == "true"){
		elLiner.innerHTML = "Si";
	}else{
		elLiner.innerHTML = "No";
	};
};
function toDate(date){
	if (date!=""){
		var vFullDate = date.split(" ");
		var sDate	= vFullDate[0];
		var sTime	= vFullDate[1];
		var oNewDate = new Date().parse(toUSADate(sDate,"-")+"T"+sTime, "yyyy-mm-dd'T'HH:MM:ss");
		return oNewDate;
	}else{
		return null;
	};
	
	
};
/*
 * Date Format 1.2.3
 * (c) 2007-2009 Steven Levithan <stevenlevithan.com>
 * MIT license
 *
 * Includes enhancements by Scott Trenda <scott.trenda.net>
 * and Kris Kowal <cixar.com/~kris.kowal/>
 *
 * Accepts a date, a mask, or a date and a mask.
 * Returns a formatted version of the given date.
 * The date defaults to the current date/time.
 * The mask defaults to dateFormat.masks.default.
 */

var dateFormat = function () {
	var	token = /d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g,
		timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g,
		timezoneClip = /[^-+\dA-Z]/g,
		pad = function (val, len) {
			val = String(val);
			len = len || 2;
			while (val.length < len) val = "0" + val;
			return val;
		};

	// Regexes and supporting functions are cached through closure
	return function (date, mask, utc) {
		var dF = dateFormat;

		// You can't provide utc if you skip other args (use the "UTC:" mask prefix)
		if (arguments.length == 1 && Object.prototype.toString.call(date) == "[object String]" && !/\d/.test(date)) {
			mask = date;
			date = undefined;
		}

		// Passing date through Date applies Date.parse, if necessary
		date = date ? new Date(date) : new Date;
		if (isNaN(date)) throw SyntaxError("invalid date");

		mask = String(dF.masks[mask] || mask || dF.masks["default"]);

		// Allow setting the utc argument via the mask
		if (mask.slice(0, 4) == "UTC:") {
			mask = mask.slice(4);
			utc = true;
		}

		var	_ = utc ? "getUTC" : "get",
			d = date[_ + "Date"](),
			D = date[_ + "Day"](),
			m = date[_ + "Month"](),
			y = date[_ + "FullYear"](),
			H = date[_ + "Hours"](),
			M = date[_ + "Minutes"](),
			s = date[_ + "Seconds"](),
			L = date[_ + "Milliseconds"](),
			o = utc ? 0 : date.getTimezoneOffset(),
			flags = {
				d:    d,
				dd:   pad(d),
				ddd:  dF.i18n.dayNames[D],
				dddd: dF.i18n.dayNames[D + 7],
				m:    m + 1,
				mm:   pad(m + 1),
				mmm:  dF.i18n.monthNames[m],
				mmmm: dF.i18n.monthNames[m + 12],
				yy:   String(y).slice(2),
				yyyy: y,
				h:    H % 12 || 12,
				hh:   pad(H % 12 || 12),
				H:    H,
				HH:   pad(H),
				M:    M,
				MM:   pad(M),
				s:    s,
				ss:   pad(s),
				l:    pad(L, 3),
				L:    pad(L > 99 ? Math.round(L / 10) : L),
				t:    H < 12 ? "a"  : "p",
				tt:   H < 12 ? "am" : "pm",
				T:    H < 12 ? "A"  : "P",
				TT:   H < 12 ? "AM" : "PM",
				Z:    utc ? "UTC" : (String(date).match(timezone) || [""]).pop().replace(timezoneClip, ""),
				o:    (o > 0 ? "-" : "+") + pad(Math.floor(Math.abs(o) / 60) * 100 + Math.abs(o) % 60, 4),
				S:    ["th", "st", "nd", "rd"][d % 10 > 3 ? 0 : (d % 100 - d % 10 != 10) * d % 10]
			};

		return mask.replace(token, function ($0) {
			return $0 in flags ? flags[$0] : $0.slice(1, $0.length - 1);
		});
	};
}();

var dateParse = function () {
	
	var	token = /d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g;
	var	timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g;
	var	timezoneClip = /[^-+\dA-Z]/g;
	
	var	pad = function (val, len) {
		val = String(val);
		len = len || 2;
		while (val.length < len) val = "0" + val;
		return val;
	};

	// Regexes and supporting functions are cached through closure
	return function (date, mask, utc) {
		var dF = dateFormat;

		mask = String(dF.masks[mask] || mask || dF.masks["default"]);

		// Allow setting the utc argument via the mask
		if (mask.slice(0, 4) == "UTC:") {
			mask = mask.slice(4);
			utc = true;
		}
		
		//TODO usa variuos mask than yyyy-mm-dd'T'HH:MM:ss

		var year = date.substring(0,4);
		var month = parseInt(parseFloat(date.substring(5,7))) - 1;
		var day = date.substring(8,10);
		var hours = date.substring(11,13);
		var minutes = date.substring(14,16);
		var seconds = date.substring(17,20);
		var milliseconds = 0;
	
		return new Date(year, month, day, hours, minutes, seconds, milliseconds);
	};
}();

// Some common format strings
dateFormat.masks = {
	"default":      "ddd mmm dd yyyy HH:MM:ss",
	shortDate:      "m/d/yy",
	mediumDate:     "mmm d, yyyy",
	longDate:       "mmmm d, yyyy",
	fullDate:       "dddd, mmmm d, yyyy",
	shortTime:      "h:MM TT",
	mediumTime:     "h:MM:ss TT",
	longTime:       "h:MM:ss TT Z",
	isoDate:        "yyyy-mm-dd",
	isoTime:        "HH:MM:ss",
	isoDateTime:    "yyyy-mm-dd'T'HH:MM:ss",
	isoDateTimeEU:	"dd-mm-yyyy HH:MM:ss",
	isoUtcDateTime: "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"
};

// Internationalization strings
dateFormat.i18n = {
	dayNames: [
		"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
		"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
	],
	monthNames: [
		"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
		"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
	]
};

// For convenience...
Date.prototype.format = function (mask, utc) {
	return dateFormat(this, mask, utc);
};

//For convenience...
Date.prototype.parse = function (sdate, mask, utc) {
	return dateParse(sdate, mask, utc);
};




function deepObjCopy (dupeObj) {
	var retObj = new Object();
	if (typeof(dupeObj) == 'object') {
		if (typeof(dupeObj.length) != 'undefined')
			var retObj = new Array();
		for (var objInd in dupeObj) {
			if (typeof(dupeObj[objInd]) == 'object') {
				retObj[objInd] = deepObjCopy(dupeObj[objInd]);
			} else if (typeof(dupeObj[objInd]) == 'string') {
				retObj[objInd] = dupeObj[objInd];
			} else if (typeof(dupeObj[objInd]) == 'number') {
				retObj[objInd] = dupeObj[objInd];
			} else if (typeof(dupeObj[objInd]) == 'boolean') {
				((dupeObj[objInd] == true) ? retObj[objInd] = true : retObj[objInd] = false);
			}
		}
	}
	return retObj;
}
function getFile(url,sType) {
	var type = sType || "";
	try {
		
		AJAX = new ActiveXObject("Microsoft.XMLHTTP");  
	}catch(sErr){
		AJAX = new XMLHttpRequest();  
	}
	try{
		if (AJAX) {
			AJAX.open("GET", url, false);                             
			AJAX.send(null);
			if(type == ""){
				return AJAX.responseText;                                         
			}else{
				return AJAX.responseXML;                                         
			
			};
		} 
	}catch(myErr){
		
	}
}

function GetArrDate(vArrObjDate){
	
	var vArrDate = new Array();
	for (i=0;i< vArrObjDate.length;i++){
		var sData = vArrObjDate[i].getDate()+"/"+(vArrObjDate[i].getMonth()+1)+"/"+vArrObjDate[i].getFullYear();
		vArrDate.push(sData);
	};
	
	return vArrDate;
	
};

function isString() {
	if (typeof arguments[0] == 'string') return true;
	if(typeof arguments[0] == 'object') { 
		 var criterion = arguments[0].constructor.toString().match(/string/i); 
 		return (criterion != null);  
	};
	return false;
};
function isObject(o) {
  return (typeof(o)=="object");
}
function isArray(obj){
	return (obj && obj.constructor && obj.constructor.toString().indexOf("Array") > 0);
}

function getAsArray(obj){
	if(obj == null) return new Array();
	else if(!isArray(obj)) return new Array(obj);
	else return obj;
}

function addAsArray(newOne, parent, attribute) {
	if(!parent[attribute]) {
		parent[attribute] = newOne;
	} else {
		
		if (!isArray(parent[attribute])) {
			var temp = parent[attribute];
			parent[attribute] = new Array();
			parent[attribute].push(temp);
		}
		parent[attribute].push(newOne);
	}	
}

function delOptions(obj){
	while (obj.length > 0){
		obj.remove(obj.length - 1);
	};
};
function addOption(valore, obj, isEnable){
	var newElem1 = document.createElement("option");
    newElem1.setAttribute('value',valore);
	if(!isEnable) newElem1.className="unavailable";    
    var newText1 = document.createTextNode(valore);
    newElem1.appendChild(newText1);    
    obj.appendChild(newElem1);
};

function addOptionText(text, valore, obj){
	var newElem1 = document.createElement("option");
    newElem1.setAttribute('value',valore);
    var newText1 = document.createTextNode(text);
    newElem1.appendChild(newText1);    
    obj.appendChild(newElem1);
}

function addOptionLanguage( key, obj){
	var newElem1 = document.createElement("option");
    newElem1.setAttribute('langres',key);
	newElem1.setAttribute('value',key);
	var newText1 = document.createTextNode(key);
    newElem1.appendChild(newText1);    
	obj.appendChild(newElem1);
}

function setSelectValue(select, value) {
	var options = select.options;
	for(var i = 0; i<options.length; i++) {
		if(options[i].value.toUpperCase() == value.toUpperCase()) {
			select.selectedIndex = i;
			break;
		}
	}
}

function setSelectMultipleValue(select, arr) {
	var options = select.options;
	for(var i = 0; i<options.length; i++) {
		if(inArray(arr,options[i].value)) {
			options[i].selected = true;
		}
	}
}

function getSelectMultipleValue(select) {
	var options = select.options;
	var values = [];
	for(var i =0; i<options.length; i++) {
		if(options[i].selected) values.push(options[i].value);
	}
	return values;
	
}

function getChildrenIndex(child) {
  var bros = removeNodeType(child.parentNode.childNodes);
  for(var i = 0; i<bros.length; i++) if(bros[i] == child) return i;
  return false;
}

function removeNodeType(a) {
  var newone = new Array();
  for(var i = 0; i<a.length; i++) if(a[i].nodeType != 3) newone.push(a[i]);
  return newone;
}
function inArray(array, value) {
	for(i in array) if(array[i] == value) return true;
	return false;
}

function emptyForm(obj, unless){	
	obj.reset();
}

function getChildrenByAttribute(parent, attributeName, attributeValue) {
	if(!isArray(parent)) {
		if(parent[attributeName] == attributeValue) {
			return parent;
		}
	} else {
		for(var i = 0; i<parent.length; i++) {
			if(parent[i][attributeName] == attributeValue) {
				return parent[i];
			}
		}
	}
	return false;
}

function getChildrenArrayByAttribute(parent, attributeName, attributeValue) {
	var temp = [];
	if(!isArray(parent)) {
		if(parent[attributeName] == attributeValue) {
			temp.push(parent);
		}
	} else {
		for(var i = 0; i<parent.length; i++) {
			if(parent[i][attributeName] == attributeValue) {
				temp.push(parent[i]);
			}
		}
	}
	return temp;
}

function setContentInElement(element, text) {
	YUI().use('node', function(Y) {
		if (Y.UA.ie) {
			if (element.innerText == null)
				element.text = text; 
			else
				element.innerText = text;
		} else {
			element.textContent = text;
		}
	});
}

function getContentInElement(element) {
	var textContent;
	
	YUI().use('node', function(Y) {
		if (Y.UA.ie > 0) {
			if (element.text == null){
				
				if (element.textContent != null) {
					textContent = element.textContent;
					return;
				};
				textContent = element.innerText;
				return;
			}
				
			textContent = element.text;
			return;
			
		} else {
			textContent = element.textContent;
			return;
		}
	});
	return textContent;
}

function toUSADate(sDate,sChar){
	sChar = sChar||"/";
	var vTmpDate = sDate.split(sChar);
	if (vTmpDate[2].length==4){
		var sUSADate = vTmpDate[2]+sChar+vTmpDate[1]+sChar+vTmpDate[0];
	}else{
		sUSADate = sDate;
	};
	return sUSADate;
}
function toEUDate(sDate,sChar){
	sChar = sChar||"/";
	var vTmpDate = sDate.split(sChar);
	if (vTmpDate[0].length==4){
		var sEUDate = vTmpDate[2]+sChar+vTmpDate[1]+sChar+vTmpDate[0];
	}else{
		sEUDate = sDate;
	};
	return sEUDate;
}
function removeAllChildren(targetElement) {
	if (targetElement && targetElement.childNodes) {
		for (var rloop = targetElement.childNodes.length -1; rloop >= 0 ; rloop--) {
			targetElement.removeChild(targetElement.childNodes[rloop]);
		}
	}
}

function evalutateXPath(contextNode, xpathExpression) {
		
	var nodesname = xpathExpression.split('/');
	var curnode = contextNode;
	
	for (var i=0; i < nodesname.length; i++) {
		
		if (nodesname[i].indexOf('[') > -1) {
			
			var selector = nodesname[i].substring(nodesname[i].indexOf('[')).replace('[', '').replace(']', '');
			selector = selector.split('=');
			
			if(selector[0].indexOf(REat) > -1) {
			
				//filtro sugli attributi
				var selectorName  = selector[0].replace(REspace, '').replace(REat, '');				
				var selectorValue = selector[1];
				if (selector[1].indexOf("\"") > -1 || selector[1].indexOf("'") > -1)
					selectorValue = selectorValue.substring(1, selector[1].length - 1);
	 
				var name = nodesname[i].substring(0, nodesname[i].indexOf('['));
			
				if (curnode.length && curnode.length > 0)
					curnode = curnode[0];
			 
				curnode = curnode.getElementsByTagName(name);
	
				var ret = [];
				for (var j=0; j<curnode.length; j++) {
					if (curnode[j].getAttribute(selectorName) == selectorValue) {
						ret.push(curnode[j]);
						continue;
					}
				}
				
			} else {
				//filtro sul textnode
				var selectorName  = selector[0].replace(REspace, '').replace(REat, '');
				var selectorValue = selector[1].replace(REspace, '').replace(REquote, '');
	 
				var name = nodesname[i].substring(0, nodesname[i].indexOf('['));
			
				if (curnode.length && curnode.length > 0)
					curnode = curnode[0];
			 
				curnode = curnode.getElementsByTagName(name);
	
				var ret = [];
				for (var j=0; j<curnode.length; j++) {
					var child = curnode[j].childNodes;
					for(var c =0; c<child.length; c++) {
						var nodename = child[c].nodeName;
						var t = nodename.indexOf(":");
						if (t > 0) nodename = nodename.substring(t+1, nodename.length);
				
						if(nodename == selectorName && 
							getContentInElement(child[c]) == selectorValue) {
							ret.push(curnode[j]);
							break;
						}
					}
				}
			}
				
			curnode = ret;
		} else {
			
			if (curnode.length && curnode.length > 0)
				curnode = curnode[0];
			
			var found = new Array();
			for (var k=0; k < curnode.childNodes.length; k++) {
				var nodename = curnode.childNodes[k].nodeName;
				var t = nodename.indexOf(":");
				if (t > 0) nodename = nodename.substring(t+1, nodename.length);
				if (nodename == nodesname[i]) {
					found.push(curnode.childNodes[k]);
				}
			}

			curnode = found;
				
			if (curnode.length == 0)
				break;
			
		}
		
	}
		
	return curnode;
	
}

function xmlToJSON(xmlnode) {
	var out = "[";
	
	var subnodes = evalutateXPath(xmlnode, "string");

	for (var i = 0; i < subnodes.length; i++) {
		out = out + "{ string: '" + getContentInElement(subnodes[i]) + "' }";
		if ((i - 1) < subnodes.length)
			out = out + ",";
	}
	
	return eval(out + "]");
	
}
  

/**
*
*  AJAX IFRAME METHOD (AIM)
*  http://www.webtoolkit.info/
*
**/

AIM = {

	frame : function(c) {

		var n = 'f' + Math.floor(Math.random() * 99999);
		var d = document.createElement('DIV');
		d.innerHTML = '<iframe style="display:none" src="about:blank" id="'+n+'" name="'+n+'" onload="AIM.loaded(\''+n+'\')"></iframe>';
		document.body.appendChild(d);

		var i = document.getElementById(n);
		if (c && typeof(c.onComplete) == 'function') {
			i.onComplete = c.onComplete;
		}

		return n;
	},

	form : function(f, name) {
		f.setAttribute('target', name);
	},

	submit : function(f, c) {
		AIM.form(f, AIM.frame(c));
		if (c && typeof(c.onStart) == 'function') {
			return c.onStart();
		} else {
			return true;
		}
	},

	loaded : function(id) {
		
		//NOOP per ora
		return;
		
		var i = document.getElementById(id);
		if (i.contentDocument) {
			var d = i.contentDocument;
		} else if (i.contentWindow) {
			var d = i.contentWindow.document;
		} else {
			var d = window.frames[id].document;
		}
		if (d.location.href == "about:blank") {
			return;
		}

		if (typeof(i.onComplete) == 'function') {
			i.onComplete(d.body.innerHTML);
		}
	}
};


/*
function xmlFormatDate(elCell, oRecord, oColumn, oData) { 
    var oDate = oData; 
    var sMonth; 
    elCell.innerHTML = sadfdasd();
}
*/
	
/* 
 * 

Object.prototype.deep_clone = function(){
	eval("var tmp = " + this.toJSON());
	return tmp;
}
Object.prototype.toJSON = function(){
	var json = [];
	for(var i in this){
		if(!this.hasOwnProperty(i)) continue;
		//if(typeof this[i] == "function") continue;
		if(i == "parent") continue;
		json.push(
			i.toJSON() + " : " +
			((this[i] != null) ? this[i].toJSON() : "null")
		)
	}
	return "{\n " + json.join(",\n ") + "\n}";
}
Array.prototype.toJSON = function(){
	for(var i=0,json=[];i<this.length;i++)
		json[i] = (this[i] != null) ? this[i].toJSON() : "null";
	return "["+json.join(", ")+"]"
}

String.prototype.toJSON = function(){
	return '"' +
		this.replace(/(\\|\")/g,"\\$1")
		.replace(/\n|\r|\t/g,function(){
			var a = arguments[0];
			return  (a == '\n') ? '\\n':
					(a == '\r') ? '\\r':
					(a == '\t') ? '\\t': ""
		}) +
		'"'
}
Boolean.prototype.toJSON = function(){return this}
Function.prototype.toJSON = function(){return this}
Number.prototype.toJSON = function(){return this}
RegExp.prototype.toJSON = function(){return this}

// strict but slow
String.prototype.toJSON = function() {

	var tmp = this.split("");

	for(var i=0;i<tmp.length;i++) {
		var c = tmp[i];
		(c >= ' ') ?
			(c == '\\') ? (tmp[i] = '\\\\'):
			(c == '"')  ? (tmp[i] = '\\"' ): 0 :
		(tmp[i] = 
			(c == '\n') ? '\\n' :
			(c == '\r') ? '\\r' :
			(c == '\t') ? '\\t' :
			(c == '\b') ? '\\b' :
			(c == '\f') ? '\\f' :
			(c = c.charCodeAt(),('\\u00' + ((c>15)?1:0)+(c%16))));
	}
	return '"' + tmp.join("") + '"';
}

/*
 * Elimina spazi bianchi da una stringa
 */
function Trim(StrToTrim){
	if (typeof StrToTrim != "string"){
        return StrToTrim;
    }
    var StrBlank = StrToTrim.substring(0, 1);

    while (StrBlank == " "){
        StrToTrim = StrToTrim.substring(1, StrToTrim.length);
        StrBlank = StrToTrim.substring(0, 1);
    }

    StrBlank = StrToTrim.substring(StrToTrim.length - 1, StrToTrim.length);

    while (StrBlank == " "){
        StrToTrim = StrToTrim.substring(0, StrToTrim.length-1);
        StrBlank = StrToTrim.substring(StrToTrim.length-1, StrToTrim.length);
    }

    while (StrToTrim.indexOf("  ") != -1){
        StrToTrim = StrToTrim.substring(0, StrToTrim.indexOf("  "));
        StrToTrim += StrToTrim.substring(StrToTrim.indexOf("  ") + 1, StrToTrim.length);
    }

    return StrToTrim;
}

String.prototype.startsWith = function(str) {
	return (this.match("^"+str)==str);
};

function ce(tag,name){
	if (name && window.ActiveXObject){
		element = document.createElement('<'+tag+' name="'+name+'">');
	}else{
		element = document.createElement(tag);
		element.setAttribute('name',name);
	}
	return element;
};
	 
/*
 * Funzione per gestire il count down dei campi note:
 */

function limitText(limitField, limitNum,label) {
	var size = limitField.value.length; 
	var str  ="";
	
	if ((limitNum - size) >= 0) {
		str = limitNum - size;
		str += " "+getStringFromLanguageResource("cont_car2");
	} else {
		str = limitNum - size+1;
		str += " "+getStringFromLanguageResource("cont_car2");
		limitField.value = limitField.value.substring(0, limitNum);
	}
	label.innerHTML = str;		
}
		

function closeCalendarDiv(){
if(calendarDiv){
		if(calendarDiv.style.display=='block'){
			closeCalendar();
		}
	}	
}


function stringToBoolean(str){
	return str == "true";
}

Array.prototype.remove = function(obj) { 
   for ( i in this ) 
      if ( this[i] == obj ) { 
         this.splice( i, 1 ); 
         return; 
      } 
};

Array.prototype.removeByAttribute = function(attribute, value) {
	for(var i = 0; i<this.length; i++) {
		if(this[i][attribute] == value) {
			this.splice(i,1);
		}
	}
};

function openFollowMe(id){ 
		if(document.getElementById(id).style.display == 'none')
			document.getElementById(id).style.display = 'block';
		else
			document.getElementById(id).style.display = 'none';
	
};
	
function myReplaceAll(oldStr,findStr,repStr) {
	var srchNdx = 0; 
	var newStr = ""; 
	while (oldStr.indexOf(findStr,srchNdx) != -1){
		newStr += oldStr.substring(srchNdx,oldStr.indexOf(findStr,srchNdx));
		newStr += repStr;
		srchNdx = (oldStr.indexOf(findStr,srchNdx) + findStr.length);
	}
	newStr += oldStr.substring(srchNdx,oldStr.length);
	return newStr;
}	

/*
 * funzione ricorsiva per controllare l'identità di due oggetti
 */
function isEqualsObject(o1, o2) {
	//check same type
	if(o1.constructor.toString() != o2.constructor.toString()) return false;
	//simple type
	if(o1.constructor.toString().indexOf("function String()") != -1 ||
		o1.constructor.toString().indexOf("function Number()") != -1 ||
		o1.constructor.toString().indexOf("function Boolean()") != -1) {
			var b = o1 == o2;
			return b;
		}
		
	//array
	if(o1.constructor.toString().indexOf("function Array()") != -1) {
		//checking length
		if(o1.length != o2.length) return false;
		for(var i = 0; i<o1.length; i++) if(!isEqualsObject(o1[i], o2[i])) return false;
	return true;
	}
	if(o1.constructor.toString().indexOf("function Object()") != -1) {
		//checking length
		for(i in o1) if(o1.hasOwnProperty(i)) if(!o2[i] || !isEqualsObject(o1[i], o2[i])) return false;
		for(i in o2) if(o2.hasOwnProperty(i)) if(!o1[i] || !isEqualsObject(o1[i], o2[i])) return false;
		return true;
	}
}

function myReplaceAll(oldStr,findStr,repStr) {
	var srchNdx = 0; // srchNdx will keep track of where in the whole line
	// of oldStr are we searching.
	var newStr = ""; // newStr will hold the altered version of oldStr.
	while (oldStr.indexOf(findStr,srchNdx) != -1)
	// As long as there are strings to replace, this loop
	// will run.
	{
	newStr += oldStr.substring(srchNdx,oldStr.indexOf(findStr,srchNdx));
	// Put it all the unaltered text from one findStr to
	// the next findStr into newStr.
	newStr += repStr;
	// Instead of putting the old string, put in the
	// new string instead.
	srchNdx = (oldStr.indexOf(findStr,srchNdx) + findStr.length);
	// Now jump to the next chunk of text till the next findStr.
	}
	newStr += oldStr.substring(srchNdx,oldStr.length);
	// Put whatever's left into newStr.
	return newStr;
}

//funzione che rimuove un oggetto da un array
function Remove( obj )
{
   for ( i in this )
      if ( this[i] == obj )
      {
         this.splice( i, 1 );
         return;
      }
}//Remove
Array.prototype.Remove = Remove;


//HashMap implementation:

function Map()
{
    // members
    this.keyArray = new Array(); // Keys
    this.valArray = new Array(); // Values
        
    // methods
    this.put = put;
    this.get = get;
    this.size = size;  
    this.clear = clear;
    this.keySet = keySet;
    this.valSet = valSet;
    this.showMe = showMe;   // returns a string with all keys and values in map.
    this.findIt = findIt;
    this.remove = remove;
}

function put( key, val )
{
    var elementIndex = this.findIt( key );
    
    if( elementIndex == (-1) )
    {
        this.keyArray.push( key );
        this.valArray.push( val );
    }
    else
    {
        this.valArray[ elementIndex ] = val;
    }
}

function get( key )
{
    var result = null;
    var elementIndex = this.findIt( key );

    if( elementIndex != (-1) )
    {   
        result = this.valArray[ elementIndex ];
    }  
    
    return result;
}

function remove( key )
{
    var result = null;
    var elementIndex = this.findIt( key );

    if( elementIndex != (-1) )
    {
        this.keyArray = this.keyArray.removeAt(elementIndex);
        this.valArray = this.valArray.removeAt(elementIndex);
    }  
    
    return ;
}

function size()
{
    return (this.keyArray.length);  
}

function clear()
{
    for( var i = 0; i < this.keyArray.length; i++ )
    {
        this.keyArray.pop(); this.valArray.pop();   
    }
}

function keySet()
{
    return (this.keyArray);
}

function valSet()
{
    return (this.valArray);   
}

function showMe()
{
    var result = "";
    
    for( var i = 0; i < this.keyArray.length; i++ )
    {
        result += "Key: " + this.keyArray[ i ] + "\tValues: " + this.valArray[ i ] + "\n";
    }
    return result;
}

function findIt( key )
{
    var result = (-1);

    for( var i = 0; i < this.keyArray.length; i++ )
    {
        if( this.keyArray[ i ] == key )
        {
            result = i;
            break;
        }
    }
    return result;
}

function removeAt( index )
{
  var part1 = this.slice( 0, index);
  var part2 = this.slice( index+1 );

  return( part1.concat( part2 ) );
}
Array.prototype.removeAt = removeAt;

YUI.syncRequest = function(method, uri, callback, postData) {
/*YAHOO.util.Connect.syncRequest = function(method, uri, callback, postData) {*/
	var o,t,args = (callback && callback.argument)?callback.argument:null;

	if(this._isFileUpload){
		t = 'upload';
	}
	else if(callback.xdr){
		t = 'xdr';
	}

	o = this.getConnectionObject(t);
	if(!o){
		Y.log('Unable to create connection object.', 'error', 'Connection');
		return null;
	}
	else{

		// Intialize any transaction-specific custom events, if provided.
		if(callback && callback.customevents){
			this.initCustomEvents(o, callback);
		}

		if(this._isFormSubmit){
			if(this._isFileUpload){
				this.uploadFile(o, callback, uri, postData);
				return o;
			}

			// If the specified HTTP method is GET, setForm() will return an
			// encoded string that is concatenated to the uri to
			// create a querystring.
			if(method.toUpperCase() == 'GET'){
				if(this._sFormData.length !== 0){
					// If the URI already contains a querystring, append an ampersand
					// and then concatenate _sFormData to the URI.
					uri += ((uri.indexOf('?') == -1)?'?':'&') + this._sFormData;
				}
			}
			else if(method.toUpperCase() == 'POST'){
				// If POST data exist in addition to the HTML form data,
				// it will be concatenated to the form data.
				postData = postData?this._sFormData + "&" + postData:this._sFormData;
			}
		}

		if(method.toUpperCase() == 'GET' && (callback && callback.cache === false)){
			// If callback.cache is defined and set to false, a
			// timestamp value will be added to the querystring.
			uri += ((uri.indexOf('?') == -1)?'?':'&') + "rnd=" + new Date().valueOf().toString();
		}

		// Each transaction will automatically include a custom header of
		// "X-Requested-With: XMLHttpRequest" to identify the request as
		// having originated from Connection Manager.
		if(this._use_default_xhr_header){
			if(!this._default_headers['X-Requested-With']){
				this.initHeader('X-Requested-With', this._default_xhr_header, true);
				Y.log('Initialize transaction header X-Request-Header to XMLHttpRequest.', 'info', 'Connection');
			}
		}

		//If the transaction method is POST and the POST header value is set to true
		//or a custom value, initalize the Content-Type header to this value.
//		if((method.toUpperCase() === 'POST' && this._use_default_post_header) && this._isFormSubmit === false){
//			this.initHeader('Content-Type', this._default_post_header);
//			Y.log('Initialize header Content-Type to application/x-www-form-urlencoded; UTF-8 for POST transaction.', 'info', 'Connection');
//		}

		if(o.xdr){
			this.xdr(o, method, uri, callback, postData);
			return o;
		}
		
		//THIS IS THE ONLY DIFFERENT LINE FROM THE STANDARD asyncRequest()
		o.conn.open(method, uri, false);
		
		//Initialize all default and custom HTTP headers,
		if(this._has_default_headers || this._has_http_headers){
			this.setHeader(o);
		}
		
		this.handleReadyState(o, callback);
		o.conn.send(postData || '');
		Y.log('Transaction ' + o.tId + ' sent.', 'info', 'Connection');

		// Reset the HTML form data and state properties as
		// soon as the data are submitted.
		if(this._isFormSubmit === true){
			this.resetFormState();
		}

		// Fire global custom event -- startEvent
		this.startEvent.fire(o, args);

		if(o.startEvent){
			// Fire transaction custom event -- startEvent
			o.startEvent.fire(o, args);
		}

		return o;
	}
};

/*
http://www.JSON.org/json2.js
2009-09-29

Public Domain.

NO WARRANTY EXPRESSED OR IMPLIED. USE AT YOUR OWN RISK.

See http://www.JSON.org/js.html


This code should be minified before deployment.
See http://javascript.crockford.com/jsmin.html

USE YOUR OWN COPY. IT IS EXTREMELY UNWISE TO LOAD CODE FROM SERVERS YOU DO
NOT CONTROL.


This file creates a global JSON object containing two methods: stringify
and parse.

    JSON.stringify(value, replacer, space)
        value       any JavaScript value, usually an object or array.

        replacer    an optional parameter that determines how object
                    values are stringified for objects. It can be a
                    function or an array of strings.

        space       an optional parameter that specifies the indentation
                    of nested structures. If it is omitted, the text will
                    be packed without extra whitespace. If it is a number,
                    it will specify the number of spaces to indent at each
                    level. If it is a string (such as '\t' or '&nbsp;'),
                    it contains the characters used to indent at each level.

        This method produces a JSON text from a JavaScript value.

        When an object value is found, if the object contains a toJSON
        method, its toJSON method will be called and the result will be
        stringified. A toJSON method does not serialize: it returns the
        value represented by the name/value pair that should be serialized,
        or undefined if nothing should be serialized. The toJSON method
        will be passed the key associated with the value, and this will be
        bound to the value

        For example, this would serialize Dates as ISO strings.

            Date.prototype.toJSON = function (key) {
                function f(n) {
                    // Format integers to have at least two digits.
                    return n < 10 ? '0' + n : n;
                }

                return this.getUTCFullYear()   + '-' +
                     f(this.getUTCMonth() + 1) + '-' +
                     f(this.getUTCDate())      + 'T' +
                     f(this.getUTCHours())     + ':' +
                     f(this.getUTCMinutes())   + ':' +
                     f(this.getUTCSeconds())   + 'Z';
            };

        You can provide an optional replacer method. It will be passed the
        key and value of each member, with this bound to the containing
        object. The value that is returned from your method will be
        serialized. If your method returns undefined, then the member will
        be excluded from the serialization.

        If the replacer parameter is an array of strings, then it will be
        used to select the members to be serialized. It filters the results
        such that only members with keys listed in the replacer array are
        stringified.

        Values that do not have JSON representations, such as undefined or
        functions, will not be serialized. Such values in objects will be
        dropped; in arrays they will be replaced with null. You can use
        a replacer function to replace those with JSON values.
        JSON.stringify(undefined) returns undefined.

        The optional space parameter produces a stringification of the
        value that is filled with line breaks and indentation to make it
        easier to read.

        If the space parameter is a non-empty string, then that string will
        be used for indentation. If the space parameter is a number, then
        the indentation will be that many spaces.

        Example:

        text = JSON.stringify(['e', {pluribus: 'unum'}]);
        // text is '["e",{"pluribus":"unum"}]'


        text = JSON.stringify(['e', {pluribus: 'unum'}], null, '\t');
        // text is '[\n\t"e",\n\t{\n\t\t"pluribus": "unum"\n\t}\n]'

        text = JSON.stringify([new Date()], function (key, value) {
            return this[key] instanceof Date ?
                'Date(' + this[key] + ')' : value;
        });
        // text is '["Date(---current time---)"]'


    JSON.parse(text, reviver)
        This method parses a JSON text to produce an object or array.
        It can throw a SyntaxError exception.

        The optional reviver parameter is a function that can filter and
        transform the results. It receives each of the keys and values,
        and its return value is used instead of the original value.
        If it returns what it received, then the structure is not modified.
        If it returns undefined then the member is deleted.

        Example:

        // Parse the text. Values that look like ISO date strings will
        // be converted to Date objects.

        myData = JSON.parse(text, function (key, value) {
            var a;
            if (typeof value === 'string') {
                a =
/^(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2}(?:\.\d*)?)Z$/.exec(value);
                if (a) {
                    return new Date(Date.UTC(+a[1], +a[2] - 1, +a[3], +a[4],
                        +a[5], +a[6]));
                }
            }
            return value;
        });

        myData = JSON.parse('["Date(09/09/2001)"]', function (key, value) {
            var d;
            if (typeof value === 'string' &&
                    value.slice(0, 5) === 'Date(' &&
                    value.slice(-1) === ')') {
                d = new Date(value.slice(5, -1));
                if (d) {
                    return d;
                }
            }
            return value;
        });


This is a reference implementation. You are free to copy, modify, or
redistribute.
*/

/*jslint evil: true, strict: false */

/*members "", "\b", "\t", "\n", "\f", "\r", "\"", JSON, "\\", apply,
call, charCodeAt, getUTCDate, getUTCFullYear, getUTCHours,
getUTCMinutes, getUTCMonth, getUTCSeconds, hasOwnProperty, join,
lastIndex, length, parse, prototype, push, replace, slice, stringify,
test, toJSON, toString, valueOf
*/


//Create a JSON object only if one does not already exist. We create the
//methods in a closure to avoid creating global variables.
/*
if (!this.JSON) {
this.JSON = {};
}

(function () {

function f(n) {
    // Format integers to have at least two digits.
    return n < 10 ? '0' + n : n;
}

if (typeof Date.prototype.toJSON !== 'function') {

    Date.prototype.toJSON = function (key) {

        return isFinite(this.valueOf()) ?
               this.getUTCFullYear()   + '-' +
             f(this.getUTCMonth() + 1) + '-' +
             f(this.getUTCDate())      + 'T' +
             f(this.getUTCHours())     + ':' +
             f(this.getUTCMinutes())   + ':' +
             f(this.getUTCSeconds())   + 'Z' : null;
    };

    String.prototype.toJSON =
    Number.prototype.toJSON =
    Boolean.prototype.toJSON = function (key) {
        return this.valueOf();
    };
}

var cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
    escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
    gap,
    indent,
    meta = {    // table of character substitutions
        '\b': '\\b',
        '\t': '\\t',
        '\n': '\\n',
        '\f': '\\f',
        '\r': '\\r',
        '"' : '\\"',
        '\\': '\\\\'
    },
    rep;


function quote(string) {

//If the string contains no control characters, no quote characters, and no
//backslash characters, then we can safely slap some quotes around it.
//Otherwise we must also replace the offending characters with safe escape
//sequences.

    escapable.lastIndex = 0;
    return escapable.test(string) ?
        '"' + string.replace(escapable, function (a) {
            var c = meta[a];
            return typeof c === 'string' ? c :
                '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
        }) + '"' :
        '"' + string + '"';
}


function str(key, holder) {

//Produce a string from holder[key].

    var i,          // The loop counter.
        k,          // The member key.
        v,          // The member value.
        length,
        mind = gap,
        partial,
        value = holder[key];

//If the value has a toJSON method, call it to obtain a replacement value.

    if (value && typeof value === 'object' &&
            typeof value.toJSON === 'function') {
        value = value.toJSON(key);
    }

//If we were called with a replacer function, then call the replacer to
//obtain a replacement value.

    if (typeof rep === 'function') {
        value = rep.call(holder, key, value);
    }

//What happens next depends on the value's type.

    switch (typeof value) {
    case 'string':
        return quote(value);

    case 'number':

//JSON numbers must be finite. Encode non-finite numbers as null.

        return isFinite(value) ? String(value) : 'null';

    case 'boolean':
    case 'null':

//If the value is a boolean or null, convert it to a string. Note:
//typeof null does not produce 'null'. The case is included here in
//the remote chance that this gets fixed someday.

        return String(value);

//If the type is 'object', we might be dealing with an object or an array or
//null.

    case 'object':

//Due to a specification blunder in ECMAScript, typeof null is 'object',
//so watch out for that case.

        if (!value) {
            return 'null';
        }

//Make an array to hold the partial results of stringifying this object value.

        gap += indent;
        partial = [];

//Is the value an array?

        if (Object.prototype.toString.apply(value) === '[object Array]') {

//The value is an array. Stringify every element. Use null as a placeholder
//for non-JSON values.

            length = value.length;
            for (i = 0; i < length; i += 1) {
                partial[i] = str(i, value) || 'null';
            }

//Join all of the elements together, separated with commas, and wrap them in
//brackets.

            v = partial.length === 0 ? '[]' :
                gap ? '[\n' + gap +
                        partial.join(',\n' + gap) + '\n' +
                            mind + ']' :
                      '[' + partial.join(',') + ']';
            gap = mind;
            return v;
        }

//If the replacer is an array, use it to select the members to be stringified.

        if (rep && typeof rep === 'object') {
            length = rep.length;
            for (i = 0; i < length; i += 1) {
                k = rep[i];
                if (typeof k === 'string') {
                    v = str(k, value);
                    if (v) {
                        partial.push(quote(k) + (gap ? ': ' : ':') + v);
                    }
                }
            }
        } else {

//Otherwise, iterate through all of the keys in the object.

            for (k in value) {
                if (Object.hasOwnProperty.call(value, k)) {
                    v = str(k, value);
                    if (v) {
                        partial.push(quote(k) + (gap ? ': ' : ':') + v);
                    }
                }
            }
        }

//Join all of the member texts together, separated with commas,
//and wrap them in braces.

        v = partial.length === 0 ? '{}' :
            gap ? '{\n' + gap + partial.join(',\n' + gap) + '\n' +
                    mind + '}' : '{' + partial.join(',') + '}';
        gap = mind;
        return v;
    }
}

//If the JSON object does not yet have a stringify method, give it one.

if (typeof JSON.stringify !== 'function') {
    JSON.stringify = function (value, replacer, space) {

//The stringify method takes a value and an optional replacer, and an optional
//space parameter, and returns a JSON text. The replacer can be a function
//that can replace values, or an array of strings that will select the keys.
//A default replacer method can be provided. Use of the space parameter can
//produce text that is more easily readable.

        var i;
        gap = '';
        indent = '';

//If the space parameter is a number, make an indent string containing that
//many spaces.

        if (typeof space === 'number') {
            for (i = 0; i < space; i += 1) {
                indent += ' ';
            }

//If the space parameter is a string, it will be used as the indent string.

        } else if (typeof space === 'string') {
            indent = space;
        }

//If there is a replacer, it must be a function or an array.
//Otherwise, throw an error.

        rep = replacer;
        if (replacer && typeof replacer !== 'function' &&
                (typeof replacer !== 'object' ||
                 typeof replacer.length !== 'number')) {
            throw new Error('JSON.stringify');
        }

//Make a fake root object containing our value under the key of ''.
//Return the result of stringifying the value.

        return str('', {'': value});
    };
}


//If the JSON object does not yet have a parse method, give it one.

if (typeof JSON.parse !== 'function') {
    JSON.parse = function (text, reviver) {

//The parse method takes a text and an optional reviver function, and returns
//a JavaScript value if the text is a valid JSON text.

        var j;

        function walk(holder, key) {

//The walk method is used to recursively walk the resulting structure so
//that modifications can be made.

            var k, v, value = holder[key];
            if (value && typeof value === 'object') {
                for (k in value) {
                    if (Object.hasOwnProperty.call(value, k)) {
                        v = walk(value, k);
                        if (v !== undefined) {
                            value[k] = v;
                        } else {
                            delete value[k];
                        }
                    }
                }
            }
            return reviver.call(holder, key, value);
        }


//Parsing happens in four stages. In the first stage, we replace certain
//Unicode characters with escape sequences. JavaScript handles many characters
//incorrectly, either silently deleting them, or treating them as line endings.

        cx.lastIndex = 0;
        if (cx.test(text)) {
            text = text.replace(cx, function (a) {
                return '\\u' +
                    ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
            });
        }

//In the second stage, we run the text against regular expressions that look
//for non-JSON patterns. We are especially concerned with '()' and 'new'
//because they can cause invocation, and '=' because it can cause mutation.
//But just to be safe, we want to reject all unexpected forms.

//We split the second stage into 4 regexp operations in order to work around
//crippling inefficiencies in IE's and Safari's regexp engines. First we
//replace the JSON backslash pairs with '@' (a non-JSON character). Second, we
//replace all simple value tokens with ']' characters. Third, we delete all
//open brackets that follow a colon or comma or that begin the text. Finally,
//we look to see that the remaining characters are only whitespace or ']' or
//',' or ':' or '{' or '}'. If that is so, then the text is safe for eval.

        if (/^[\],:{}\s]*$/.
test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@').
replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']').
replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {

//In the third stage we use the eval function to compile the text into a
//JavaScript structure. The '{' operator is subject to a syntactic ambiguity
//in JavaScript: it can begin a block or an object literal. We wrap the text
//in parens to eliminate the ambiguity.

            j = eval('(' + text + ')');

//In the optional fourth stage, we recursively walk the new structure, passing
//each name/value pair to a reviver function for possible transformation.

            return typeof reviver === 'function' ?
                walk({'': j}, '') : j;
        }

//If the text is not JSON parseable, then a SyntaxError is thrown.

        throw new SyntaxError('JSON.parse');
    };
}
}());

*/

/************************************************************************************************************
Editable select
Copyright (C) 2005, 2011mber 2005  DTHMLGoodies.com, Alf Magne Kalleland

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

Dhtmlgoodies.com., hereby disclaims all copyright interest in this script
written by Alf Magne Kalleland.

Alf Magne Kalleland, 2006
Owner of DHTMLgoodies.com
	
************************************************************************************************************/	


// Path to arrow images
var arrowImage = './images/select_arrow.gif';	// Regular arrow
var arrowImageOver = './images/select_arrow_over.gif';	// Mouse over
var arrowImageDown = './images/select_arrow_down.gif';	// Mouse down


var selectBoxIds = 0;
var currentlyOpenedOptionBox = false;
var editableSelect_activeArrow = false;



function selectBox_switchImageUrl()
{
	if(this.src.indexOf(arrowImage)>=0){
		this.src = this.src.replace(arrowImage,arrowImageOver);	
	}else{
		this.src = this.src.replace(arrowImageOver,arrowImage);
	}
	
	
}

function selectBox_showOptions()
{
	if(editableSelect_activeArrow && editableSelect_activeArrow!=this){
		editableSelect_activeArrow.src = arrowImage;
		
	}
	editableSelect_activeArrow = this;
	
	var numId = this.id.replace(/[^\d]/g,'');
	var optionDiv = document.getElementById('selectBoxOptions' + numId);
	if(optionDiv.style.display=='block'){
		optionDiv.style.display='none';
		if(navigator.userAgent.indexOf('MSIE')>=0)document.getElementById('selectBoxIframe' + numId).style.display='none';
		this.src = arrowImageOver;	
	}else{			
		optionDiv.style.display='block';
		if(navigator.userAgent.indexOf('MSIE')>=0)document.getElementById('selectBoxIframe' + numId).style.display='block';
		this.src = arrowImageDown;	
		if(currentlyOpenedOptionBox && currentlyOpenedOptionBox!=optionDiv)currentlyOpenedOptionBox.style.display='none';	
		currentlyOpenedOptionBox= optionDiv;			
	}
}

function selectOptionValue()
{
	var parentNode = this.parentNode.parentNode;
	var textInput = parentNode.getElementsByTagName('INPUT')[0];
	textInput.value = this.innerHTML;	
	this.parentNode.style.display='none';	
	document.getElementById('arrowSelectBox' + parentNode.id.replace(/[^\d]/g,'')).src = arrowImageOver;
	
	if(navigator.userAgent.indexOf('MSIE')>=0)document.getElementById('selectBoxIframe' + parentNode.id.replace(/[^\d]/g,'')).style.display='none';
	
}
var activeOption;
function highlightSelectBoxOption()
{
	if(this.style.backgroundColor=='#316AC5'){
		this.style.backgroundColor='';
		this.style.color='';
	}else{
		this.style.backgroundColor='#316AC5';
		this.style.color='#FFF';			
	}	
	
	if(activeOption){
		activeOption.style.backgroundColor='';
		activeOption.style.color='';			
	}
	activeOption = this;
	
}

function createEditableSelect(dest)
{

	dest.className='selectBoxInput';		
	var div = document.createElement('DIV');
	div.style.styleFloat = 'left';
	div.style.width = dest.offsetWidth + 16 + 'px';
	div.style.position = 'relative';
	div.id = 'selectBox' + selectBoxIds;
	var parent = dest.parentNode;
	parent.insertBefore(div,dest);
	div.appendChild(dest);	
	div.className='selectBox';
	div.style.zIndex = 10000 - selectBoxIds;

	var img = document.createElement('IMG');
	img.src = arrowImage;
	img.className = 'selectBoxArrow';
	
	img.onmouseover = selectBox_switchImageUrl;
	img.onmouseout = selectBox_switchImageUrl;
	img.onclick = selectBox_showOptions;
	img.id = 'arrowSelectBox' + selectBoxIds;

	div.appendChild(img);
	
	var optionDiv = document.createElement('DIV');
	optionDiv.id = 'selectBoxOptions' + selectBoxIds;
	optionDiv.className='selectBoxOptionContainer';
	optionDiv.style.width = div.offsetWidth-2 + 'px';
	div.appendChild(optionDiv);
	
	if(navigator.userAgent.indexOf('MSIE')>=0){
		var iframe = document.createElement('<IFRAME src="about:blank" frameborder=0>');
		iframe.style.width = optionDiv.style.width;
		iframe.style.height = optionDiv.offsetHeight + 'px';
		iframe.style.display='none';
		iframe.id = 'selectBoxIframe' + selectBoxIds;
		div.appendChild(iframe);
	}
	
	if(dest.getAttribute('selectBoxOptions')){
		var options = dest.getAttribute('selectBoxOptions').split(';');
		var optionsTotalHeight = 0;
		var optionArray = new Array();
		for(var no=0;no<options.length;no++){
			var anOption = document.createElement('DIV');
			anOption.innerHTML = options[no];
			anOption.className='selectBoxAnOption';
			anOption.onclick = selectOptionValue;
			anOption.style.width = optionDiv.style.width.replace('px','') - 2 + 'px'; 
			anOption.onmouseover = highlightSelectBoxOption;
			optionDiv.appendChild(anOption);	
			optionsTotalHeight = optionsTotalHeight + anOption.offsetHeight;
			optionArray.push(anOption);
		}
		if(optionsTotalHeight > optionDiv.offsetHeight){				
			for(var no=0;no<optionArray.length;no++){
				optionArray[no].style.width = optionDiv.style.width.replace('px','') - 22 + 'px'; 	
			}	
		}		
		optionDiv.style.display='none';
		optionDiv.style.visibility='visible';
	}
	
	selectBoxIds = selectBoxIds + 1;
}	


function load_xml_content_string(xmlData) {
	if (window.ActiveXObject) {
		//for IE
		xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
		xmlDoc.async="false";
		xmlDoc.loadXML(xmlData);
		return xmlDoc;
	} else if (document.implementation && document.implementation.createDocument) {
		//for Mozila
		parser=new DOMParser();
		xmlDoc=parser.parseFromString(xmlData,"text/xml");
		return xmlDoc;
	}
}
	
