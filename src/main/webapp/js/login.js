var httpRequest;
var result;
var username;
var password;

function alertContents() {
	result = document.getElementById("result");
	if (httpRequest.readyState == 4) {
		if (httpRequest.status == 200) {
			prepareHomePage();
			//result.innerHTML = "Logged!";
		} else if (httpRequest.status == 401) {
			console.log("Unauthorzed!");
			result.innerHTML = "Unauthorzed!";
		} else {
			console.log('There was a problem with the request.');
			result.innerHTML = "There was a problem with the request.!";
		}
	}
}

function sendRequest() {
	
	if (window.XMLHttpRequest) { // Mozilla, Safari, ...
		httpRequest = new XMLHttpRequest();
	} else if (window.ActiveXObject) { // IE
		httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	username = document.getElementById("username");
	password = document.getElementById("password");

	httpRequest.onreadystatechange = alertContents;
	httpRequest.open('POST', 'services/login', true);
	httpRequest.setRequestHeader("Content-Type", "application/json");
	httpRequest.send("username="+username.value+"&password="+password.value);
}

function prepareHomePage() {
	console.log("Logged!");
	if(httpRequest != null) {
		console.log(httpRequest);
	}
}

function isLogged() {
	if (window.XMLHttpRequest) { // Mozilla, Safari, ...
		httpRequest = new XMLHttpRequest();
	} else if (window.ActiveXObject) { // IE
		httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	httpRequest.onreadystatechange = alertContents;
	httpRequest.open('GET', 'services/login', true);
	httpRequest.setRequestHeader("Content-Type", "application/json");
}
