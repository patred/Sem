var httpRequest;
var result;
var username;
var password;

function alertContents() {
	result = document.getElementById("result");
	if (httpRequest.readyState == 4) {
		if (httpRequest.status == 200) {
			prepareHomePage();
			// result.innerHTML = "Logged!";
		} else if (httpRequest.status == 401) {
			console.log("Unauthorzed!");
			result.innerHTML = "Unauthorzed!";
		} else {
			console.log('There was a problem with the request.');
			result.innerHTML = "There was a problem with the request.!";
		}
	}
}

function login(callback) {
	username = $('#username').val();
	password = $('#password').val();

	$.ajax({
		url : "services/login",
		type : "POST",
		data : "username=" + username + "&password=" + password,
		headers : {
			"Accept" : "application/json",
			"Content-Type" : "application/json"
		},
		success : function(data, textStatus, jqXHR) {
			if (callback != undefined)
				callback(data);
			console.log("user: " + data.user);
			console.log("role: " + data.role);
			handlePage(data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			var data = {
				error : "generic"
			};
			try {
				data = $.parseJSON(jqXHR.responseText);
			} catch (e) {
				// nothing
			}
			if (callback != undefined)
				callback(data);
			$('#result').html(data);
		},
		statusCode : {
			401 : function() {
				console.log('Unauthorzed!');
				$('#result').html("Non Autorizzato!");
			},
			503 : function() {
				console.log('There was a problem with the request: ServerOffline');
				$('#result').html("Ci sono stati problemi con il server!");
			},
			500 : function() {
				console.log('There was a problem with the request: ServerError');
				$('#result').html("Ci sono stati problemi con il server!");
			},
			502 : function() {
				console.log('There was a problem with the request: ServerError');
				$('#result').html("Ci sono stati problemi con il server!");
			}
		}
	});
}

function handlePage(data){
	$('.welcome').html('Benvenuto ' + data.user);
	$('.welcome').css("visibility","visible");
	$('.logout').css("visibility","visible");
	$('#login').hide();
	$('#hormenu').show();
	$('#main').show();
}

function isLoggedAjax() {
	$.ajax({
		dataType : "json",
		url : 'services/login',
		data : {},
		success : function(data) {
			console.log("user: " + data.user);
			console.log("role: " + data.role);
		}
	});
}