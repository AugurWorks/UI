<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Graph</title>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jqplot.css')}" type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.qtip.css')}" type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery-ui.css')}" type="text/css">
<style>
	.jqplot-table-legend {
		width: auto;
	}
</style>

</head>
<body>
	<g:javascript src="jquery-2.0.3.js" />
	<g:javascript src="jquery.qtip.min.js" />
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
	<g:javascript src="jquery-ui.min.js" />
	<div id='content' style='padding: 10px;'>
		<div class="message" id="invalidMessage" style="display: none;"></div>
		<div class="buttons">
			<div class="button-line">
				<div class="qtipText" title="Select a type of data to plot.">Select Input: <g:select name="input1" from="${ dataTypes }" optionKey="name" /></div>
				<div class="qtipText" title="Input a value such as USO for a stock or Oil for sentiment.">Input Value: <g:textField style="width: 90px;" type="text" name="input2" value="USO" /></div>
			</div>
			<div class="button-line">
				Start date: <g:textField style="width: 90px;" type="text" id="startDate" name="startDate" value="${startDate}" />
				End date: <g:textField style="width: 90px;" type="text" id="endDate" name="endDate" value="${endDate}" />
			</div>
		</div>
		<div class="button-line">
			<button class="buttons" onclick="add()">Add</button>
			<button class="buttons" onclick="clearTable()">Clear</button>
			<button class="buttons" onclick="validate()">Submit</button>
		</div>
		<br></br>
		<h4>Currently Added Inputs</h4>
		<div id="table"></div>
		<div style="text-align: center; padding: 20px;">
			<div id="chart1"></div>
		</div>
		<button class="buttons" class="button-reset">Reset Zoom</button>
		<script type="text/javascript">
			var initilized = false
			var req = new Object()
			counter = 0
			$(document).ready(function() {
				setDatePickers();
				add();
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

			// Adds a query to the request object and redraws the table
			function add() {
				req[counter] = {name: $('#input2').val().replace(" ","").toUpperCase(), dataType: $('#input1').val(), startDate: $('#startDate').val(), endDate: $('#endDate').val()}
				drawTable()
				counter += 1
			}

			// Clears the request object and redraws the table
			function clearTable() {
				req = new Object()
				drawTable()
			}

			// Draws a table showing current requests
			function drawTable() {
				var text = "<table><tr><th>Name</th><th>Data Type</th><th>Start Date</th><th>End Date</th><th>Remove</th></tr>"
				for (i in req) {
					text += '<tr><td>'
					text += req[i].name
					text += '</td><td>'
					text += req[i].dataType
					text += '</td><td>'
					text += req[i].startDate
					text += '</td><td>'
					text += req[i].endDate
					text += '</td><td><button class="buttons" onclick="removeReq(' + i + ')">Remove</button></td></tr>'
				}
				text += "</table>"
				$('#table').html(text)
			}

			function removeReq(i) {
				delete req[i]
				drawTable()
			}

			// Runs each time the 'Go!' button is clicked. Retrieves data from the server.
			function validate() {
				ajaxCall(req, "${g.createLink(controller:'data',action:'getData')}")
			}
			
			var dataSet;
			var inputArray = [];
			var nameArray = [];
			var seriesArray = []
			var plot1;
			var fullAjaxData;

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
				$('div .qtipText').qtip({
				    style: {
				    	widget: true,
				    	def: false
				    },
				    position: {
			            my: 'bottom left',
			            at: 'top right'
			        }
				});
				$('#info').qtip({
				    style: {
				    	widget: true,
				    	def: false
				    },
				    position: {
			            my: 'center bottom',
			            at: 'center top'
			        },
			        content: 'hey'
				});
			}
		</script>
	</div>
</body>
</html>
