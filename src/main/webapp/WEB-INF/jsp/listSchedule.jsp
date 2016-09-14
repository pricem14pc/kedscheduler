<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<title>Scheduler | listSchedule</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
</head>
<body>

	<div class="container-fluid">
		<div class="row">
			<header>
				<h1>Scheduled tasks:</h1>
			</header>
		</div>
		<div class="row">
			<div class="col-xs-12 table-responsive"
				th:if="${keyEventDateList != null and not #lists.isEmpty(keyEventDateList)}">
				<table class="table table-striped table-bordered">
					<thead>
						<tr>
							<th>Survey Sid</th>
							<th>Survey Id</th>
							<th>Survey Name</th>
							<th>CE Id</th>
							<th>CE Label</th>
							<th>CE Display Name</th>
							<th>State Id</th>
							<th>Current State Desc.</th>
							<th>Event Key Sid</th>
							<th>Event Type</th>
							<th>Event Type Desc.</th>
							<th>Event Date</th>
							<th>Process State Id</th>
							<th>Process State Desc.</th>
							<th>Process State Date</th>
							<th>Attempt Count</th>
						</tr>
					</thead>
					<tbody>
						<tr th:each="record : ${keyEventDateList}"
							th:class="${record.currentProcessStateId == 5 or record.currentProcessStateId == 4} ? danger : none">
							<td th:text="${record.surveySid}"></td>
							<td th:text="${record.surveyId}"></td>
							<td th:text="${record.surveyName}"></td>
							<td th:text="${record.collectionExerciseSid}"></td>
							<td th:text="${record.collectionExerciseLabel}"></td>
							<td th:text="${record.collectionExerciseDisplayName}"></td>
							<td th:text="${record.collectionExerciseStateId}"></td>
							<td th:text="${record.collectionExerciseStateDescription}"></td>
							<td th:text="${record.eventKeySid}"></td>
							<td th:text="${record.eventTypeId}"></td>
							<td th:text="${record.eventTypeDescription}"></td>
							<td th:text="${record.eventDate}"></td>
							<td th:text="${record.currentProcessStateId}"></td>
							<td th:text="${record.currentProcessStateDescription}"></td>
							<td th:text="${record.currentProcessStateAsAt}"></td>
							<td th:text="${record.attemptCount}"></td>
						</tr>
					</tbody>
				</table>
			</div>
			<p th:if="${keyEventDateList == null or #lists.isEmpty(keyEventDateList)}">No results</p>
		</div>
	</div>
	<script type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>