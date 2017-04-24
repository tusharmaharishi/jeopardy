var xmlhttp

function isPositiveInteger(str) {
    var n = Math.floor(Number(str));
    return String(n) === str && n >= 1;
}

function inputValidation(input) {

	if ((isPositiveInteger(input))) {
		document.getElementById("errorMessage").innerHTML = "";
		document.getElementById("submit").disabled = false;
		return;
	}

	// 1. create an ajax object
	xmlhttp = GetXmlHttpObject();
	if (xmlhttp == null) {
		alert("Your browser does not support XMLHTTP!");
		return;
	}

	// Backend handler - response software component
	var url = "http://localhost:8080/Jeopardy/inputValidator";

	// URL rewriting
	url = url + "?StringSoFar=" + input;
	url = url + "&sid=" + Math.random();

	// 2. How to configure XMLHttpRequest object?
	// Register the callback function named "stateChanged"
	xmlhttp.onreadystatechange = stateChanged;

	// 3. How to make an asynchronous request?
	xmlhttp.open("get", url, true);

	// 4. How to send the request to the server?
	xmlhttp.send(null);

}

function stateChanged() {
	// The callback function processes the response from the server	
	if (xmlhttp.readyState == 4) {
		// Update the HTML DOM
		document.getElementById("errorMessage").innerHTML = xmlhttp.responseText;
		document.getElementById("submit").disabled = true;
	}
}

function GetXmlHttpObject() {
	// How to create an XMLHttpRequest object?
	if (window.XMLHttpRequest) // checks for browser compatibility 
		return new XMLHttpRequest;
	else if (window.ActiveXObject) // IE6-
		return new ActiveXObject("Microsoft.XMLHTTP");
	else
		//browser is extremely old and does not support AJAX
		return null;
}
