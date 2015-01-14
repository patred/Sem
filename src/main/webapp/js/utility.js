/*
*  @autor: Giuseppe Cozzolino
*  @since: 06/02/2009
*
*/

var REspace = new RegExp(' ', "ig");
var REat = '@'; //new RegExp('@', "ig");
var REquote = new RegExp('\'', "ig");

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

function addOption(valore, obj, isEnable){
	var newElem1 = document.createElement("option");
    newElem1.setAttribute('value',valore);
	if(!isEnable) newElem1.className="unavailable";    
    var newText1 = document.createTextNode(valore);
    newElem1.appendChild(newText1);    
    obj.appendChild(newElem1);
};



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

//Create a YUI instance using the io-base module.
function syncRequest(uri, method, callback, postData) {
	var args = (callback && callback.argument)?callback.argument:null;
	var response;
	if(method == null || method == undefined)
		method = "GET";
	
	YUI().use("io-base", function(Y) {
	    var cfg, request;
	    // Create a configuration object for the synchronous transaction.
	    cfg = {
	    	method: method,
	        sync: true,
	        data : postData,
	        headers: {
	            'Content-Type': 'application/xml',
	        },
	    };

	    /*
	     * var request will contain the following fields, when the
	     * transaction is complete:
	     * - id
	     * - status
	     * - statusText
	     * - getResponseHeader()
	     * - getAllResponseHeaders()
	     * - responseText
	     * - responseXML
	     * - arguments
	     */
	    response = Y.io(uri, cfg);
	    function complete(id, o, args) {
	        var id = id; // Transaction ID.
	        var data = o.responseText; // Response data.
	        var args = args[1]; // 'ipsum'.
	      };
	      
	    Y.on('io:success', complete, Y);
	});
	
	return response;
};




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
	
