<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Search</title>
</head>
<body>
	<div id='content' style='padding: 10px;'>
		<h1>Search For Correlations</h1>
		<p>
			Use this to search for correlations between datasets.
		</p>
		<div class="errors" id="errors" style="display: none;"></div>
		<g:form name="search" action="submit">
			<h2>Data Sets</h2>
			<g:select name="sets" from="${ sets }" multiple="true" optionKey="id" />
			<h2>Stocks</h2>
			<g:select name="stocks" from="${ stocks }" multiple="true" optionKey="id" />
			<table>
				<tr>
					<td>Search Set Name:</td>
					<td><g:field type="text" name="name" /></td>
				</tr>
				<tr>
					<td>Aggregation:</td>
					<td><g:select name="agg" from="${ agg }" /></td>
				</tr>
				<tr>
					<td>Start Date:</td>
					<td><g:field type="date" name="start" /></td>
				</tr>
				<tr>
					<td>End Date:</td>
					<td><g:field type="date" name="end" /></td>
				</tr>
				<tr>
					<td>Offset Range:</td>
					<td><g:field type="number" name="offset" value="0" min="0" /></td>
				</tr>
			</table>
			<g:submitButton name="Submit" style="display: none;" />
		</g:form>
		<button onclick="validate()">Submit</button>
	</div>
	<script>
		$(function() {
			$('#sets').chosen();
			$('#stocks').chosen();
		});
	
		function validate() {
			var params = {sets: $('#sets').val(), stocks: $('#stocks').val(), start: $('#start').val(), end: $('#end').val(), offset: $('#offset').val(), agg: $('#agg').val(), name: $('#name').val()};
			var errors = [];
			if (!params.sets && !params.stocks) {
				errors.push('No datasets chosen.')
			}
			if (!params.start || new Date(params.start).getTime() > new Date().getTime()) {
				errors.push('Invalid start date.')
			}
			if (!params.end || new Date(params.end).getTime() > new Date().getTime()) {
				errors.push('Invalid end date.')
			}
			if (params.start && params.end && new Date(params.start).getTime() > new Date(params.end).getTime()) {
				errors.push('Start date after end date.')
			}
			if (!params.offset) {
				errors.push('Invalid offset.')
			}
			if (errors.length > 0) {
				$('#errors').show();
				$('#errors').html('<ul><li>' + errors.join('</li><li>') + '</li></ul>');
			} else {
				$('#errors').hide();
				$('#Submit').click();
			}
		}
	</script>
</body>
</html>
