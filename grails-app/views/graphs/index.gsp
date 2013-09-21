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
		<div style="text-align: right">Username: ${ service.authentication.name }</div>
		<div id="chart1" style="height:400px; width:300px;"></div>
		<script type="text/javascript">
			$(document).ready(function() {
				var plot1 = $.jqplot('chart1', [[3,7,9,1,4,6,8,2,5]]);
			});
		</script>
	</body>
</html>