<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Graph</title>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jqplot.css')}" type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.qtip.min.css')}" type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery-ui.min.css')}" type="text/css">
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
	<g:javascript src="jquery.jsanalysis.js" />
	<g:javascript src="jquery.blockUI.js" />
	<g:javascript src="datepickers.js" />
	<g:javascript src="ajaxData.js" />
	<g:javascript src="jquery-ui.min.js" />
	<div id='content' style='padding: 10px;'>
		<div class="message" id="invalidMessage" style="display: none;"></div>
		<div class="buttons">
			<div class="button-line">
				<div class="qtipText" title="Input type for the x-axis.">Input 1: <g:select name="input1" from="${ dataTypes }" optionKey="name" /></div>
				Value: <g:textField style="width: 90px;" type="text" name="input2" value="USO" />
			</div>
			<div class="button-line">
				<div class="qtipText" title="Input type for the y-axis.">Input 2: <g:select name="input3" from="${ dataTypes }" optionKey="name" /></div>
				Value: <g:textField style="width: 90px;" type="text" name="input4" value="DJIA" />
			</div>
			<div class="button-line">
				Start date: <g:textField style="width: 90px;" type="text" id="startDate" name="startDate" value="${startDate}" />
				End date: <g:textField style="width: 90px;" type="text" id="endDate" name="endDate" value="${endDate}" />
				<div class="qtipText" title="Input a number of business days for this set to be offset from the initial dataset date range.">Offset: <input style="width: 60px;" type="number" id="offset" name="offset" value="0" /></div>
			</div>
		</div>
		<div class="button-line">
			<button class="buttons" onclick="validate()">Submit</button>
		</div>
		<div style="text-align: center; padding: 20px; margin: 0 auto;">
			<div id="chart1" style="margin: 0 auto;"></div>
		</div>
		<div id="correlation"></div>
		<div id="function"></div>
		<button class="button-reset">Reset Zoom</button>
		<script type="text/javascript">
				
			var initilized = false
			var src = "${resource(dir: 'images', file: 'spinner.gif')}"

			// Gets data on load.
			$(document).ready(function() {
				setDatePickers();
				validate();
				qtip();
			});

			// Resize the plot on window resize
			window.onresize = resize
			function resize() {
				var size = Math.min(window.innerWidth - 150, window.innerHeight - 100)
				$('#chart1').width(size)
				$('#chart1').height(size - 100)
				if(plot1) {
					$('#chart1').empty();
				}
				plot();
			}

			// Runs each time the 'Go!' button is clicked. Retrieves data from the server.
			function validate() {
				var startDate = $('#startDate').val();
				var endDate = $('#endDate').val();
				var offset = parseInt($('#offset').val());
				var req = new Object();
				req[0] = {dataType: $('#input1').val(), startDate: startDate, endDate: endDate, name: $('#input2').val().replace(" ","")}
				req[1] = {dataType: $('#input3').val(), startDate: calcNewDate(startDate, offset), endDate: calcNewDate(endDate, offset), name:$('#input4').val().replace(" ","")}
				ajaxCall(req, "${g.createLink(controller:'data',action:'getData')}")
			}
			
			var dataSet;
			var formattedDataSet;
			var inputArray = [];
			var nameArray = [];
			var regressionSet;
			var dateSet;
			var plot1;
			var fullAjaxData;
			var day = ['M', 'Tu', 'W', 'Tr', 'F', 'Sa', 'Su']

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
			function replot(type) {
				$('#chart1').empty();
				resize()
			}

			//  Formats additional data and computes statistics.
			function formatData(data) {
				var formatted = []
				var first = []
				var second = []
				dateSet = []
				for (i in data[0]) {
					if (data[0][i] && data[1][i]) {
						first.push(data[0][i][1])
						second.push(data[1][i][1])
						dateSet.push([data[0][i][0].substring(0, 10), data[1][i][0].substring(0, 10)])
						formatted.push([data[0][i][1], data[1][i][1]])
					} else {
						console.log('Different length')
					}
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

			// Plots the graph for the first time.
			function plot() {
				if (dataSet.length != 2) {
					$('#chart1').html('The input combination is invalid.')
				} else {
					formatData(dataSet)
					var label, formatStr;
					formatStr = '$%.2f'
					plot1 = $.jqplot(
						'chart1',
						formattedDataSet,
						{
							title : 'Correlation Plot',
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
									label : nameArray[0],
									tickOptions : {
										formatString : '%.2f, ' + fullAjaxData[inputArray[0]]['metadata']['unit']
									}
								},
								yaxis : {
									tickOptions : {
										formatString : '%.2f, ' + fullAjaxData[inputArray[1]]['metadata']['unit']
									},
									labelRenderer : $.jqplot.CanvasAxisLabelRenderer,
									label : nameArray[1],
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
						            var input1 = inputArray[0];
						            var input2 = inputArray[1];
						            var date1 = dateSet[pointIndex][0]
						            var date2 = dateSet[pointIndex][1]
						            var diff = (val2 - regressionSet[pointIndex][1]) / Math.abs(regressionSet[pointIndex][1]) * 100
						            var name1 = nameArray[0];
						            var name2 = nameArray[1];

						            var s1 = new Date(date1)
						            var s2 = new Date(date2)
			
						            var html = "<div>";
						            html += name1 + ": "  + val1.toFixed(3) + ", " + fullAjaxData[input1]['metadata']['unit'];
						            html += "<br>";
						            if (seriesIndex == 0) {
						            	html += "Date: " + day[s1.getDay()] + ", " + date1;
							            html += "<br>";
							            html += name2 + ": "  + val2.toFixed(3) + ", " + fullAjaxData[input2]['metadata']['unit'];
							            html += "<br>";
							            html += "Date: " + day[s2.getDay()] + ", " + date2;
							            html += "<br>";
							            html += "Difference: ";
								        html += diff.toFixed(2) + "%";
								    } else {
										html += "Predicted " + input2 + ": $" + val2.toFixed(2);
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

			function qtip() {
				$('div .qtipText').qtip({
				    style: {
				    	widget: true,
				    	def: false
				    },
				    position: {
			            my: 'left bottom',
			            at: 'right top'
			        }
				});
			}
		</script>
	</div>
</body>
</html>
