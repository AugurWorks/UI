<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Graph</title>
<style>
.jqplot-yaxis-label {
	margin-right: 40px;
}
</style>
</head>
<body>
	<g:javascript src="jquery-2.0.3.js" />
	<g:javascript src="jquery.jqplot.js" />
	<g:javascript src="jqplot.canvasTextRenderer.js" />
	<g:javascript src="jqplot.canvasAxisLabelRenderer.js" />
	<g:javascript src="jqplot.CanvasAxisTickRenderer.js" />
	<g:javascript src="jqplot.DateAxisRenderer.js" />
	<g:javascript src="jqplot.highlighter.js" />
	<g:javascript src="jqplot.cursor.js" />
	<g:javascript src="jqplot.dateAxisRenderer.js" />
	<div id='content' style='padding: 10px;'>
		<div style="text-align: right">
			Username:
			${ service.authentication.name }
		</div>
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>
		<g:form id='form'>
			 Stock: <g:textField type="text" id="stock" name="stock" value="${stock}" />
			 Start date: <g:textField type="text" id="startDate" name="startDate" value="${startDate}" />
			 End date: <g:textField type="text" id="endDate" name="endDate" value="${endDate}" />
			<input type="submit" value="Go!">
		</g:form>
		<div style="text-align: center; padding: 20px;">
			<div id="chart1"></div>
		</div>
		<script type="text/javascript">
				$(document).ready(function(){
					var raw = '${ stocks.getStock(stock, Integer.parseInt(startDate.substring(0, 2)) - 1, Integer.parseInt(startDate.substring(3, 5)), Integer.parseInt(startDate.substring(6, 10)), Integer.parseInt(endDate.substring(0, 2)) - 1, Integer.parseInt(endDate.substring(3, 5)), Integer.parseInt(endDate.substring(6, 10)), "d") }'
								if (raw == 'Stock Not Found') {
									$('#chart1').html('The stock is invalid.')
								} else {
									var line = $.parseJSON(raw.replace(
											/&quot;/g, '"'))
									var list = []
									$
											.each(
													line.data[0],
													function(index, value) {
														list
																.push([
																		value.date,
																		parseFloat(value.val) ])
													});
									$('#chart1').width('100%')
									$('#chart1').height('600px')
									var plot1 = $
											.jqplot(
													'chart1',
													[ list.reverse() ],
													{
														title : 'Default Date Axis',
														axesDefaults : {
															tickRenderer : $.jqplot.CanvasAxisTickRenderer,
															tickOptions : {
																angle : -30,
																fontSize : '10pt'
															}
														},
														axes : {
															xaxis : {
																labelRenderer : $.jqplot.CanvasAxisLabelRenderer,
																renderer : $.jqplot.DateAxisRenderer,
																label : 'Date',
																tickOptions : {
																	formatString : '%b %#d %y'
																}
															},
															yaxis : {
																tickOptions : {
																	formatString : '$%.2f'
																},
																labelRenderer : $.jqplot.CanvasAxisLabelRenderer,
																label : 'Price ($)'
															}
														},
														highlighter : {
															show : false
														},
														cursor : {
															show : true,
															tooltipLocation : 'sw'
														},
														series : [ {
															showMarker : false
														} ]
													});
								}
							});
		</script>
	</div>
</body>
</html>
