function clickedIt() {
	var url = getTextValue("url");
	var ceId = getTextValue("collectionExerciseId");
	var endpoint = getTextValue("endpoint");
	
	var xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function() {
		if (xhttp.readyState == 4) {
			updateResult("Status: " + xhttp.status + ", Response: " + xhttp.responseText);
		}
	}
	
	var metadata = new Object();
	metadata.url = encodeURIComponent(url);
	metadata.ceid = ceId;
	metadata.endpoint = endpoint;
	
	updateResult("");
	
	xhttp.open("POST", "/invokeEndpoint", true);
	xhttp.send(JSON.stringify(metadata));
}

function getTextValue(name) {
	return document.getElementById(name).value;
}

function updateResult(result) {
	document.getElementById("result").innerHTML = result;
}