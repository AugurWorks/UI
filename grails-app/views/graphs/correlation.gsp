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
	<g:javascript src="jquery.jsanalysis.js" />
	<g:javascript src="datepickers.js" />
	<g:javascript src="stockAjax.js" />
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	<div id='content' style='padding: 10px;'>
		<div class="message" id="invalidMessage" style="display: none;"></div>
		Stock 1: <g:textField type="text" id="stock1" name="stock1" value="USO" />
		Stock 2: <g:textField type="text" id="stock2" name="stock2" value="DJIA" />
		Start date: <g:textField type="text" id="startDate" name="startDate" value="${startDate}" />
		End date: <g:textField type="text" id="endDate" name="endDate" value="${endDate}" />
		Second stock day offset: <g:textField type="text" id="offset" name="offset" value="0" />
		<button onclick="validate()">Go!</button>
		<div style="text-align: center; padding: 20px;">
			<div id="chart1"></div>
		</div>
		<div id="correlation"></div>
		<div id="function"></div>
		<button class="button-reset">Reset Zoom</button>
		<script type="text/javascript">
				
			var initilized = false
			$(document).ready(function() {
				setDatePickers()
				validate()
			});
			
			function validate() {
				var stock1 = $('#stock1').val().replace(" ","")
				var stock2 = $('#stock2').val().replace(" ","")
				var startDate = $('#startDate').val()
				var endDate = $('#endDate').val()
				var offset = parseInt($('#offset').val())
				var req = new Object()
				req[stock1] = {startDate: startDate, endDate: endDate};
				var s = new Date(startDate)
				var n = new Date(s.getFullYear(), s.getMonth(), s.getDate() + offset);
				var e = new Date(endDate)
				var m = new Date(e.getFullYear(), e.getMonth(), e.getDate() + offset);
				req[stock2] = {startDate: n.getMonth() + 1 + "/" + n.getDate() + "/" + n.getFullYear(), endDate: m.getMonth() + 1 + "/" + m.getDate() + "/" + m.getFullYear()};
				stockAjax(req, 'stock', 'invalidMessage', "${g.createLink(controller:'graphs',action:'stockData')}")
			}
			
			var dataSet;
			var formattedDataSet;
			var stockArray = [];
			var regressionSet;
			var dateSet;
			var plot1;

			function stockAjaxComplete(ajaxObject) {
				dataSet = ajaxObject.dataSet
				stockArray = ajaxObject.stockArray
				seriesArray = ajaxObject.seriesArray
				if (!initilized) {
					plot()
					initilized = true
				} else {
					replot()
				}
			}
			
			function replot(type) {
				$('#chart1').empty();
				plot(type);
			}

			function formatData(data) {
				var formatted = []
				var first = []
				var second = []
				dateSet = []
				for (i in data[0][0]) {
					first.push(data[0][0][i][1])
					second.push(data[0][1][i][1])
					dateSet.push([data[0][0][i][0].substring(0, 10), data[0][1][i][0].substring(0, 10)])
					formatted.push([data[0][0][i][1], data[0][1][i][1]])
				}
				$('#correlation').text('Correlation is ' + $.corr_coeff(first, second).toFixed(3))
				var regressionFnt = $.linear_reg_eq(second, first)
				var yint = regressionFnt(0)
				var coeff = regressionFnt(1) - yint
				$('#function').text('Function is ' + coeff.toFixed(4) + "*x + " + yint.toFixed(4))
				formattedDataSet = []
				regressionSet = []
				for (i in first) {
					regressionSet.push([first[i], regressionFnt(first[i])])
				}
				formattedDataSet = [formatted, regressionSet]
			}
			
			function plot() {
				if (dataSet[0].length != 2) {
					$('#chart1').html('The stock combination is invalid.')
				} else {
					$('#chart1').width('100%')
					$('#chart1').height($('#chart1').width() - 100)
					formatData(dataSet)
					var label, formatStr;
					formatStr = '$%.2f'
					plot1 = $.jqplot(
						'chart1',
						formattedDataSet,
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
									label : stockArray[0] + ' Price',
									tickOptions : {
										formatString : formatStr
									}
								},
								yaxis : {
									tickOptions : {
										formatString : formatStr
									},
									labelRenderer : $.jqplot.CanvasAxisLabelRenderer,
									label : stockArray[1] + ' Price',
								}
							},
							highlighter : {
								sizeAdjust: 7.5,
								tooltipLocation: 'nw',
								tooltipOffset: 10,
								show : true,
						        tooltipContentEditor: function (str, seriesIndex, pointIndex, plot) {
			
						            var val1 = plot.series[seriesIndex].data[pointIndex][0];
						            var val2 = plot.series[seriesIndex].data[pointIndex][1];
						            var stock1 = stockArray[0];
						            var stock2 = stockArray[1];
						            var date1 = dateSet[pointIndex][0]
						            var date2 = dateSet[pointIndex][1]
						            var diff = (val2 - regressionSet[pointIndex][1]) / val2 * 100
			
						            var html = "<div>";
						            html += stock1 + ": $" + val1;
						            html += "<br>";
						            if (seriesIndex == 0) {
						            	html += "Date: " + date1;
							            html += "<br>";
							            html += stock2 + ": $" + val2;
							            html += "<br>";
							            html += "Date: " + date2;
							            html += "<br>";
							            html += "Difference: ";
								        html += diff.toFixed(2) + "%";
								    } else {
										html += "Predicted " + stock2 + ": $" + val2.toFixed(2);
									}
						            
						            html += "</div>";
						            return html;
						        }
							},
						    cursor : {
						    	show: true,
						    	zoom: true
						    },
							series : [{
								showLine: false,
								markerOptions : {
									style: "x",
									size: 6
								}
							}, {
								showLine: true,
								showMarker : false
							}]
						});
					$('.button-reset').click(function() { plot1.resetZoom() });
				}
			}
		</script>
	</div>
</body>
</html>
