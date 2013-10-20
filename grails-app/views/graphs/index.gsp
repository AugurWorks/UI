<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Graph</title>
<link rel="stylesheet" href="${resource(dir: 'js', file: 'jquery.jqplot.css')}" type="text/css">
<style>
	.jqplot-table-legend {
		width: auto;
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
	<g:javascript src="jqplot.cursor.js" />
	<g:javascript src="jqplot.enhancedLegendRenderer.js" />
	<div id='content' style='padding: 10px;'>
		<g:if test="${flash.message}">
			<div class="message">
				${flash.message}
			</div>
		</g:if>
		<g:form id='form'>
			 Stock: <g:textField type="text" id="stock" name="stock" value="${stock.split('<break>').join(', ')}" />
			 Start date: <g:textField type="text" id="startDate" name="startDate" value="${startDate}" />
			 End date: <g:textField type="text" id="endDate" name="endDate" value="${endDate}" />
			<input type="submit" value="Go!">
		</g:form>
		<div style="text-align: center; padding: 20px;">
			<div id="chart1"></div>
		</div>
		<button class="button-reset">Reset Zoom</button>
		<script type="text/javascript">
				$(document).ready(function(){
					var raw = '${ rawData }'
					var rawArray = raw.split('&lt;break&gt;')
					var listArray = []
					var seriesArray = []
					var percentageArray = []
					var stockArray = []
					for (raw in rawArray) {
						if (raw != 'Stock Not Found') {
							var line = $.parseJSON(rawArray[raw].replace(/&quot;/g, '"'))
							var list = []
							var percentage = []
							$.each(
								line.data[0],
								function(index, value) {
									list.push([value.date, parseFloat(value.val) ])
									if (list.length > 1) {
										percentage.push([value.date, (list[list.length - 2][1] - list[list.length - 1][1]) / list[list.length - 1][1] * 100])
									}
								});
							listArray.push(list.reverse())
							percentageArray.push(percentage.reverse())
							stockArray.push('${stock}'.split('&lt;break&gt;')[raw])
							seriesArray.push({
								showMarker : false
							})
						}
					}
					if (listArray.length == 0) {
						$('#chart1').html('The stock is invalid.')
					} else {
						$('#chart1').width('100%')
						$('#chart1').height('600px')
						var values, label, formatStr;
						if (listArray.length > 1) {
							values = percentageArray
							labelVal = 'Percentage (%)'
							formatStr = '%.0f%'
						} else {
							values = listArray
							labelVal = 'Price ($)'
								formatStr = '$%.2f'
						}
						var plot1 = $.jqplot(
							'chart1',
							values,
							{
								title : 'Stock Prices',
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
											formatString : formatStr
										},
										labelRenderer : $.jqplot.CanvasAxisLabelRenderer,
										label : labelVal
									}
								},
								highlighter : {
									sizeAdjust: 7.5,
									tooltipLocation: 'nw',
									tooltipOffset: 10,
									show : true,
							        tooltipContentEditor: function (str, seriesIndex, pointIndex, plot) {

							            var date = plot.series[seriesIndex].data[pointIndex][0];
							            var val = plot.series[seriesIndex].data[pointIndex][1];
							            var stock = stockArray[seriesIndex];

							            var html = "<div>";
							            html += stock;
							            html += "<br>";
							            html += str;
							            html += "</div>";
							            return html;
							        }
								},
							    cursor : {
							    	show: true,
							    	zoom: true
							    },
								series : seriesArray,
							    legend: {
							    	renderer: $.jqplot.EnhancedLegendRenderer,
							        show: true,
							        labels: stockArray,
							        location: 'nw'
							    }
							});
						$('.button-reset').click(function() { plot1.resetZoom() });
					}
				});
		</script>
	</div>
</body>
</html>
