<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>Document Search</title>
		<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	</head>
	<body>
		<g:javascript src="jquery-2.0.3.js"/>
		<g:javascript src="jquery.jqplot.js"/>
		<g:javascript src="jqplot.canvasTextRenderer.js"/>
		<g:javascript src="jqplot.canvasAxisLabelRenderer.js"/>
		<g:javascript src="jqplot.CanvasAxisTickRenderer.js"/>
		<g:javascript src="jqplot.DateAxisRenderer.js"/>
		<g:javascript src="jquery-ui.js"/>
		<div style="text-align: right">Username: ${ service.authentication.name }</div>
		<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
		</g:if>
		<g:form id='form' onsubmit="onSubmit();">
			 Keyword: <g:textField type="text" id="keyword" name="keyword" value="${keyword}"/>
			 Start date: <g:textField type="text" id="startDate" name="startDate" value="${startDate}"/>
			 End date: <g:textField type="text" id="endDate" name="endDate" value="${endDate}"/>
			 <input type="submit" value="Go!">
		</g:form>
		<!--<div id="table_holder">
			<table border='1' id="table_body">
				<tr id="table_head">
					<th>Relevance</th>
					<th>Published</th>
					<th>Title</th>
					<th>Description</th>
				</tr>
			</table>
		</div>-->
		<div id="accordion">
		
		</div>
		<script type="text/javascript">
			$(document).ready(function(){	
				var jsonned = $.parseJSON("${ reply }".replace(/&quot;/g,'"').replace(/[\t\n\r]/g,""));
				for (var i = 0; i < jsonned.data.length; i++) {
					var date = new Date(jsonned.data[i].publishedDate);
					var dateString = (date.getMonth() + 1) + "/" + date.getDate() + "/" + date.getFullYear();
					var relevance = parseFloat(jsonned.data[i].score).toFixed(2)
					//$("#table_body").append(
					//		'<tr><td>' + relevance + '</td><td>'
					//		 + dateString + '</td><td>'
					//		 + '<a href=' + jsonned.data[i].url + '>' + 
					//		 	jsonned.data[i].title + '</a></td><td>'
					//		 + jsonned.data[i].description + '</td></tr>');
					$("#accordion").append(
						'<h3>' + jsonned.data[i].title + '</h3>'
						+ '<div><table><tr><th>Relevance</th><th>Published</th><th>Title</th><th>Description</th></tr>'
						+ '<tr><td>' + relevance + '</td><td>'
						+ dateString + '</td><td>'
						+ '<a href=' + jsonned.data[i].url + '>'
						+ jsonned.data[i].title + '</a></td><td>'
						+ jsonned.data[i].description + '</td></tr></div>'
					)
				}
				$(function() {
					$( "#accordion" ).accordion();
				});
			});
			var onSubmit = function() {
				var keyword = $('#keyword').value()
				var startDate = $('#startDate').value()
				var endDate = $('#endDate').value()
				console.log("/index?keyword=" + keyword + "&startDate=" + startDate  + "&endDate=" + endDate)
				window.location.href = "/index?keyword=" + keyword + "&startDate=" + startDate  + "&endDate=" + endDate
			}
		</script>
	</body>
</html>
