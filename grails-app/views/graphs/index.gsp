<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>Graph</title>
	</head>
	<body>
		<g:javascript src="jquery-2.0.3.js"/>
		<g:javascript src="jquery.jqplot.js"/>
		<g:javascript src="jqplot.canvasTextRenderer.js"/>
		<g:javascript src="jqplot.canvasAxisLabelRenderer.js"/>
		<g:javascript src="jqplot.CanvasAxisTickRenderer.js"/>
		<g:javascript src="jqplot.DateAxisRenderer.js"/>
		<div style="text-align: right">Username: ${ service.authentication.name }</div>
		<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
		</g:if>
		<form id='form' onsubmit="return formSubmit(this)">
			 Keyword: <input type="text" name="keyword">
			 Start date: <input type="text" name="start_date">
			 End date: <input type="text" name="end_date">
			 <input type="submit" value="Go!">
		</form>
		<div id="table_holder">
			<table border='1' id="table_body">
				<tr id="table_head">
					<th>Relevance</th>
					<th>Published</th>
					<th>Title</th>
					<th>Description</th>
				</tr>
			</table>
		</div>
		<script type="text/javascript">
			var formSubmit = function(form) {
				${ infinite.doLogin() }
				var answer = "${ infinite.test('oil', '07/15/2013', '07/16/2013') }"
				var jsonned = $.parseJSON(answer.replace(/&quot;/g,'"'));
				for (var i = 0; i < jsonned.data.length; i++) {
					$("#table_body").append(
							'<tr><td>' + jsonned.data[i].score + '</td><td>'
							 + jsonned.data[i].publishedDate + '</td><td>'
							 + '<a href=' + jsonned.data[i].url + '>' + 
							 	jsonned.data[i].title + '</a></td><td>'
							 + jsonned.data[i].description + '</td></tr>');
				}
				${ infinite.doLogout() }
			};
		</script>
	</body>
</html>
