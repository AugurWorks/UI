<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Search</title>
</head>
<body>
	<g:javascript src="search.js" />
	<div id='content' style='padding: 10px;'>
		<h1>Search For Correlations</h1>
		<p>
			Use this to search for correlations between datasets.
		</p>
		<div class="errors" id="errors" style="display: none;"></div>
		<h2>Data Sets</h2>
		<g:select name="sets" from="${ sets }" multiple="true" optionKey="id" />
		<h2>Stocks</h2>
		<g:select name="stocks" from="${ stocks }" multiple="true" optionKey="id" />
		<table>
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
		<button onclick="validate()">Submit</button>
	</div>
</body>
</html>
