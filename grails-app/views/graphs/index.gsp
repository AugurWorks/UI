<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Graph</title>
<style>
	.jqplot-table-legend {
		width: auto;
	}
</style>

</head>
<body>
	<asset:javascript src="jqplot/jquery.jqplot.js" />
	<asset:javascript src="jqplot/jqplot.canvasTextRenderer.js" />
	<asset:javascript src="jqplot/jqplot.canvasAxisLabelRenderer.js" />
	<asset:javascript src="jqplot/jqplot.canvasAxisTickRenderer.js" />
	<asset:javascript src="jqplot/jqplot.highlighter.js" />
	<asset:javascript src="jqplot/jqplot.cursor.js" />
	<asset:javascript src="jqplot/jqplot.dateAxisRenderer.js" />
	<asset:javascript src="jqplot/jqplot.enhancedLegendRenderer.js" />
	<div id='content' style='padding: 10px;'>
		<g:render template="../layouts/menu" />
		<div style="text-align: center; padding: 5px;">
			<div id="chart1"></div>
		</div>
		<button class="buttons" class="button-reset">Reset Zoom</button>
		<g:render template="../layouts/qtip" />
		<script type="text/javascript">
			counter = 0
			$(document).ready(function() {
				setDatePickers();
				drawTable();
				validate();
				qtip();
			});

			// Resize the plot on window resize
			window.onresize = resize
			function resize() {
				var size = Math.min(window.innerWidth - 150, window.innerHeight - 100)
				$('#chart1').width('100%');
				$('#chart1').height('600px');
				if(plot1) {
					$('#chart1').empty();
				}
				plot()
			}

			// Function runs after AJAX call is completed. Creates additional data sets (daily change, change since start) and replots the graph.
			function ajaxComplete(ajaxData) {
				fullAjaxData = ajaxData
				ajaxObject = setPlotData(ajaxData, 'input', 'invalidMessage')
				dataSet = ajaxObject.dataSet
				inputArray = ajaxObject.inputArray
				nameArray = ajaxObject.nameArray
				seriesArray = ajaxObject.seriesArray
				if (!initilized) {
					resize()
					initilized = true
				} else {
					replot()
				}
			}

			// Refreshes the plot.
			function replot() {
				$('#chart1').empty();
				resize()
			}

			// Plots the graph for the first time.
			function plot() {
				if (dataSet.length == 0) {
					$('#chart1').html('The input is invalid.')
				} else {
					var axes = new Object()
					axes.xaxis = {
							labelRenderer : $.jqplot.CanvasAxisLabelRenderer,
							renderer : $.jqplot.DateAxisRenderer,
							label : 'Date',
							tickOptions : {
								formatString : '%b %#d %y'
							}
						}
					var units = []
					seriesArray = []
					for (i in inputArray) {
						var unit = fullAjaxData[inputArray[i]].metadata.unit
						if (units.indexOf(unit) == -1) {
							var formatStr = '%.2f';
							var labelVal;
							if (unit == '$') {
								formatStr = unit + formatStr
								labelVal = 'Price'
							} else if (unit == '%') {
								formatStr = formatStr + unit
								labelVal = 'Percentage'
							} else {
								formatStr += ' ' + unit
								labelVal = unit
							}
							if (units.length == 0) {
								axes.yaxis = {
										tickOptions : {
											formatString : formatStr
										},
										labelRenderer : $.jqplot.CanvasAxisLabelRenderer,
										label : labelVal
									}
							} else if (units.length == 1) {
								axes.yaxis2 = {
										tickOptions : {
											formatString : formatStr
										},
										labelRenderer : $.jqplot.CanvasAxisLabelRenderer,
										label : labelVal
									}
							}
							units.push(unit)
						}
						var y = 'yaxis';
						if (units.indexOf(unit) == 1) {
							y = 'y2axis'
						}
						seriesArray.push({
							showMarker : false,
							yaxis: y
						})
					}
					plot1 = $.jqplot(
						'chart1',
						dataSet,
						{
							title : 'Graph',
							axesDefaults : {
								tickRenderer : $.jqplot.CanvasAxisTickRenderer,
								tickOptions : {
									angle : -30,
									fontSize : '10pt'
								}
							},
							axes : axes,
							highlighter : {
								sizeAdjust: 7.5,
								tooltipLocation: 'nw',
								tooltipOffset: 10,
								show : true,
						        tooltipContentEditor: function (str, seriesIndex, pointIndex, plot) {
			
						            var date = plot.series[seriesIndex].data[pointIndex][0];
						            var val = plot.series[seriesIndex].data[pointIndex][1];
						            var name = nameArray[seriesIndex];
			
						            var html = "<div>";
						            html += name;
						            html += "<br>";
						            html += str;
						            html += "</div>";
						            return html;
						        }
							},
							grid : {
								background: '#EEEEEE',
								borderWidth: 0
							},
						    cursor : {
						    	show: true,
						    	zoom: true
						    },
							series : seriesArray,
						    legend: {
						    	renderer: $.jqplot.EnhancedLegendRenderer,
						        show: true,
						        labels: nameArray,
						        location: 'nw'
						    }
						});
					$('.button-reset').click(function() { plot1.resetZoom() });
				}
			}

			function qtip() {
				refreshQtip()
				qtipHtml[0] = '<h1>How do I use it?</h1>';
				qtipHtml[0] += '<p>';
				qtipHtml[0] += 'Add a new plot line by selecting an input type, typing an input value, and clicking the "Add" button.';
				qtipHtml[0] += ' Added inputs are shown in the "Currently Added Inputs" table and can be removed with the "Remove" button.';
				qtipHtml[0] += ' You can also clear all inputs by clicking the "Clear" button.';
				qtipHtml[0] += ' After adding all inputs press the "Submit" button.';
				qtipHtml[0] +='</p>';
				qtipHtml[0] +='<br></br>';
				qtipHtml[0] += '<p>';
				qtipHtml[0] += 'Once the inputs have been plotted you can hover over each data point to get additional information.';
				qtipHtml[0] += ' Dragging across the graph will zoom into that area and double clicking the graph or clicking the "Reset Zoom" button will reset the zoom.';
				qtipHtml[0] +='</p>';
				
				qtipHtml[1] = '<h1>What does it show?</h1>';
				qtipHtml[1] += '<p>';
				qtipHtml[1] += 'The graph plot shows a simple graph over time of any number of inputs.';
				qtipHtml[1] += ' Inputs can have different units so the first unit of the first input set will appear on the primary (left) axis and the second unique unit will appear on the secondary (right) axis.';
				qtipHtml[1] += ' Hovering over a data point will reveal additional information such as set name, date, and value at that point.';
				qtipHtml[1] += ' Clicking on a set name within the legend will toggle the display of that set.';
				qtipHtml[1] +='</p>';
				
				qtipHtml[2] = '<h1>What does it mean?</h1>';
				qtipHtml[2] += '<p>';
				qtipHtml[2] += 'The graph provides a visual representation of a data set and creates an easy to use and intuitive interface for data.';
				qtipHtml[2] += ' It also provides a visual comparison between different sets which can then be compared more thoroughly on the correlation or covariance pages.';
				qtipHtml[2] += '</p>';
			}
		</script>
	</div>
</body>
</html>
