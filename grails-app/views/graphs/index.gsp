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
	<g:javascript src="jqplot.canvasAxisTickRenderer.js" />
	<g:javascript src="jqplot.highlighter.js" />
	<g:javascript src="jqplot.cursor.js" />
	<g:javascript src="jqplot.dateAxisRenderer.js" />
	<g:javascript src="jqplot.enhancedLegendRenderer.js" />
	<g:javascript src="datepickers.js" />
	<g:javascript src="stockAjax.js" />
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	<div id='content' style='padding: 10px;'>
		<div class="message" id="invalidMessage" style="display: none;"></div>
		Stock: <g:textField type="text" id="stock" name="stock" value="USO, DJIA" />
		Start date: <g:textField type="text" id="startDate" name="startDate" value="${startDate}" />
		End date: <g:textField type="text" id="endDate" name="endDate" value="${endDate}" />
		<button onclick="validate()">Go!</button>
		<div style="text-align: center; padding: 20px;">
			<div id="chart1"></div>
		</div>
		<button class="button-reset">Reset Zoom</button>
		<button class="settings" onclick="replot(1)">Price</button>
		<button class="settings" onclick="replot(2)">Daily Change</button>
		<button class="settings" onclick="replot(3)">Change Over Period</button>
		<script type="text/javascript">
				
			var initilized = false
			var today = '${endDate}'
			$(document).ready(function() {
				setDatePickers()
				validate()
			});
			
			function validate() {
				var stocks = $('#stock').val()
				var startDate = $('#startDate').val()
				var endDate = $('#endDate').val()
				stockAjax(stocks, startDate, endDate, 'stock', 'invalidMessage', "${g.createLink(controller:'graphs',action:'stockData')}")
			}
			
			var dataSet;
			var stockArray = [];
			var seriesArray = []
			var plot1;

			function stockAjaxComplete(ajaxObject) {
				dataSet = ajaxObject.dataSet
				stockArray = ajaxObject.stockArray
				seriesArray = ajaxObject.seriesArray
				if (!initilized) {
					plot(3)
					initilized = true
				} else {
					replot(3)
				}
			}
			
			function replot(type) {
				$('#chart1').empty();
				plot(type);
			}
			
			function plot(type) {
				if (dataSet[type - 1].length == 0) {
					$('#chart1').html('The stock is invalid.')
				} else {
					$('#chart1').width('100%')
					$('#chart1').height('600px')
					var label, formatStr;
					if (type > 1) {
						labelVal = 'Percentage (%)'
						formatStr = '%.0f%'
					} else {
						labelVal = 'Price ($)'
						formatStr = '$%.2f'
					}
					plot1 = $.jqplot(
						'chart1',
						dataSet[type - 1],
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
			}
		</script>
	</div>
</body>
</html>
