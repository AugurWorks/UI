<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>Graph</title>
	</head>
	<body>
		<g:javascript library="jquery" plugin="jquery"/>
		<r:require module="jquery-ui"/>
		<jqplot:resources/>
		<jqplot:plugin name="canvasTextRenderer"/>
		<jqplot:plugin name="canvasAxisLabelRenderer"/>
		<div style="text-align: right">Username: ${ service.authentication.name }</div>
		<div id="chart1" style="height:400px; width:300px;"></div>
	</body>
	<script id="source" language="javascript" type="text/javascript">
		$(document).ready(function() {
			var plot1 = $.jqplot('#chart1', [[3,7,9,1,4,6,8,2,5]]);
		});
	</script>
</html>
