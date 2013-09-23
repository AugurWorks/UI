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
		<div id="chart1" style="height: 400px; width: 900px;"></div>
		<script type="text/javascript">
			$(document).ready(function() {
				var plot1 = $.jqplot('chart1', ${ stocks.getStock("GOOG", 0, 1, 2010, 0, 1, 2012, "d") });
			});
		</script>
	</body>
</html>