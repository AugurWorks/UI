<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Feed</title>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'about.css')}" type="text/css">
<style>
	th:hover, tr:hover {
		background: #E1F2B6;
	}
	tr:hover {
		cursor: pointer;
	}
</style>
</head>
<body>
	<div style="padding: 15px;">
		<h1>Watch the Feed</h1>
		<p>Click on a row to see the query.</p>
		<table id='main'>
			<tr>
				<th>User</th>
				<th>Date</th>
				<th>Page</th>
				<th>Inputs</th>
				<th>Views</th>
			</tr>
			<g:each in="${ requests }">
				<tr id="${ it.page }_${ it.id }" class="clickable">
					<td>${ it.user }</td>
					<td>${ it.requestDate }</td>
					<td>${ it.page.capitalize() }</td>
					<td>${ it.dataSets.size() }</td>
					<td>${ it.views }</td>
				</tr>
			</g:each>
		</table>
		<script>
			$(document).ready(function() {
				
			})
			
			$('.clickable').click(function() {
				console.log('Here')
				window.location.href = '/graphs/' + $(this).attr('id').split('_').join('/')
			})
		</script>
	</div>
</body>
</html>