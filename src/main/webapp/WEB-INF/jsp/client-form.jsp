<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<script>
function clickedIt() {
    var ceId = getTextValue("collectionExerciseId");
    var endpoint = getTextValue("endpoint");
    
    var xhttp = new XMLHttpRequest();
    
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState == 4) {
            updateResult("Status: " + xhttp.status + ", Response: " + xhttp.responseText);
        }
    }
    
    var metadata = new Object();
    metadata.ceid = ceId;
    metadata.endpoint = endpoint;
    
    updateResult("");
    
    xhttp.open("POST", "/rrm-kedsched/scheduler/invokeEndpoint", true);
    xhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhttp.send(JSON.stringify(metadata));
}

function forceTask() {

    var xhttp = new XMLHttpRequest();
	    
	xhttp.onreadystatechange = function() {
	    if (xhttp.readyState == 4) {
	        updateResult("Status: " + xhttp.status + ", Response: " + xhttp.responseText);
	    }
	}
	updateResult("");
	    
	xhttp.open("GET", "/rrm-kedsched/scheduler/forceTask", true);
	xhttp.send();
}

function getTextValue(name) {
    return document.getElementById(name).value;
}

function updateResult(result) {
    document.getElementById("result").innerHTML = result;
}
</script>
<title>Survey Service Client</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<style type="text/css">
    div {       
        padding-bottom: 10px;
    }
</style>


</head>
<body>

    <div>
        <h1>Survey Service Client</h1>
        <h2 th:text="${message}"></h2>
    </div>

    <div>
        <table>
            <tr>
                <td>Collection Exercise Id</td>
                <td><input id="collectionExerciseId" required="required"/></td>
            </tr>
            <tr>
                <td>Endpoint</td>
                <td>
                    <select id="endpoint">
                        <option>generateLetters</option>
                        <option>generateEmails</option>
                        <option>generateReminders</option>                        
                    </select>
                </td>
            </tr>       
            <tr>
                <td>Result</td>
                <td id="result"/>
            </tr>
        </table>
    </div>
    
    <div>
        <button onclick="clickedIt()">Invoke survey-service API</button>
    </div>
    
     <div>
        <button onclick="forceTask()">Force 'scheduled' task execution</button>
    </div>
    
</body>
</html>