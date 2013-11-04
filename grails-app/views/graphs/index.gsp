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
	<g:javascript src="jquery.blockUI.js" />
	<g:javascript src="datepickers.js" />
	<g:javascript src="ajaxData.js" />
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	<div id='content' style='padding: 10px;'>
		<div class="message" id="invalidMessage" style="display: none;"></div>
		Select: <g:select name="input1" from="${ dataTypes }" optionKey="name" />
		Input: <g:textField type="text" name="input2" value="USO" />
		Start date: <g:textField type="text" id="startDate" name="startDate" value="${startDate}" />
		End date: <g:textField type="text" id="endDate" name="endDate" value="${endDate}" />
		<button onclick="add()">Add</button>
		<button onclick="validate()">Go!</button><br></br>
		<h4>Currently Added Inputs</h4>
		<div id="table"></div>
		<div style="text-align: center; padding: 20px;">
			<div id="chart1"></div>
		</div>
		<button class="button-reset">Reset Zoom</button>
		<script type="text/javascript">
			var initilized = false
			var req = new Object()
			$(document).ready(function() {
				setDatePickers()
				add()
				validate()
				req = new Object()
				drawTable()
			});

			// Resize the plot on window resize
			window.onresize = resize
			function resize() {
				var size = Math.min(window.innerWidth - 150, window.innerHeight - 100)
				$('#chart1').width('100%')
				$('#chart1').height('600px')
				if(plot1) {
					$('#chart1').empty();
				}
				plot()
			}

			function add() {
				req[$('#input2').val().replace(" ","")] = {dataType: $('#input1').val(), startDate: $('#startDate').val(), endDate: $('#endDate').val()}
				drawTable()
			}

			function drawTable() {
				var text = "<table><tr><td>Name</td><td>Data Type</td><td>Start Date</td><td>End Date</td></tr>"
				for (i in req) {
					text += '<tr><td>'
					text += i
					text += '</td><td>'
					text += req[i].dataType
					text += '</td><td>'
					text += req[i].startDate
					text += '</td><td>'
					text += req[i].endDate
					text += '</td></tr>'
				}
				text += "</table>"
				$('#table').html(text)
			}

			// Runs each time the 'Go!' button is clicked. Retrieves data from the server.
			function validate() {
				ajaxCall(req, "${g.createLink(controller:'data',action:'getData')}")
			}
			
			var dataSet;
			var inputArray = [];
			var seriesArray = []
			var plot1;
			var fullAjaxData;

			// Function runs after AJAX call is completed. Creates additional data sets (daily change, change since start) and replots the graph.
			function ajaxComplete(ajaxData) {
				fullAjaxData = ajaxData
				ajaxObject = setPlotData(ajaxData, 'input', 'invalidMessage')
				dataSet = ajaxObject.dataSet
				inputArray = ajaxObject.inputArray
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
						var unit = fullAjaxData[inputArray[i]]['metadata']['unit']
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
						            var input = inputArray[seriesIndex];
			
						            var html = "<div>";
						            html += input;
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
						        labels: inputArray,
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
