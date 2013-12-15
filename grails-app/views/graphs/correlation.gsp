<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Correlation</title>
<style>
	.jqplot-table-legend {
		width: auto;
	}
</style>

</head>
<body>
	<g:javascript src="jqplot/jquery.jqplot.js" />
	<g:javascript src="jqplot/jqplot.canvasTextRenderer.js" />
	<g:javascript src="jqplot/jqplot.canvasAxisLabelRenderer.js" />
	<g:javascript src="jqplot/jqplot.canvasAxisTickRenderer.js" />
	<g:javascript src="jqplot/jqplot.highlighter.js" />
	<g:javascript src="jqplot/jqplot.cursor.js" />
	<g:javascript src="jqplot/jqplot.dateAxisRenderer.js" />
	<g:javascript src="jqplot/jqplot.enhancedLegendRenderer.js" />
	<g:javascript src="jquery.jsanalysis.js" />
	<g:javascript src="jquery.blockUI.js" />
	<g:javascript src="jquery-ui.min.js" />
	<div id='content' style='padding: 10px;'>
		<div class='errors' id="invalidMessage" style="display: none;"></div>
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
			<button class="buttons" style="font-size: large;" onclick="validate()">Submit</button>
		</div>
		<div style="text-align: center; padding: 20px; margin: 0 auto;">
			<div id="chart1" style="margin: 0 auto;"></div>
		</div>
		<button class="button-reset buttons">Reset Zoom</button>
		<div style="text-align: center;">
			<div id="0" class="info"><table><tr><td><img style="width: 20px; padding: 3px; display: inline-block;" src="${resource(dir: 'images', file: 'info.png')}"></td><td>How do I use it?</td></tr></table></div>
			<div id="1" class="info"><table><tr><td><img style="width: 20px; padding: 3px; display: inline-block;" src="${resource(dir: 'images', file: 'info.png')}"></td><td>What does it show?</td></tr></table></div>
			<div id="2" class="info"><table><tr><td><img style="width: 20px; padding: 3px; display: inline-block;" src="${resource(dir: 'images', file: 'info.png')}"></td><td>What does it mean?</td></tr></table></div>
		</div>
		<script type="text/javascript">
				
			var initilized = false
			var single = false
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
			var day = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
			var title;

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
				var regressionFnt = $.linear_reg_eq(second, first)
				var yint = regressionFnt(0)
				var coeff = regressionFnt(1) - yint
				title = '<div style="margin-left: 100px;"><h1>Correlation Plot</h1><div style="font-size: small;">Corr = ' + $.corr_coeff(first, second).toFixed(3) + ', Linear Regression - (' + nameArray[0] + ') = ' + coeff.toFixed(4) + '(' + nameArray[1] + ') + ' + yint.toFixed(4) + '</div></div>'
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
							title : title,
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
							grid : {
								background: '#EEEEEE',
								borderWidth: 0
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
			            my: 'bottom left',
			            at: 'top right'
			        }
				});
				var html = []
				html[0] = '<h1>How do I use it?</h1>';
				html[0] += '<p>';
				html[0] += 'Start by selecting two input types, two input values, and a date range.';
				html[0] += ' Also, if you want a time offset between the inputs (explained further in "What does it show?") input an integer in the "Offset" field.';
				html[0] += ' Once all fields have been set press the "Submit" button.';
				html[0] +='</p>';
				html[0] +='<br></br>';
				html[0] += '<p>';
				html[0] += 'Once the inputs have been plotted you can hover over each data point to get additional information.';
				html[0] += ' Dragging across the graph will zoom into that area and double clicking the graph or clicking the "Reset Zoom" button will reset the zoom.';
				html[0] +='</p>';
				
				html[1] = '<h1>What does it show?</h1>';
				html[1] += '<p>';
				html[1] += 'The <a href="http://en.wikipedia.org/wiki/Correlation_and_dependence" target="_blank">correlation</a> plot provides a scatter plot of two data sets.';
				html[1] += ' Also plotted is a <a href="http://en.wikipedia.org/wiki/Linear_regression" target="_blank">linear regression</a> line which fits the data sets.';
				html[1] += ' Hovering over a data point reveals additional information such as the specific dates and values of that point.';
				html[1] += ' Hovering over the fit line reveals the x-axis set value and the predicted y-axis value based on the linear regression.';
				html[1] +='</p>';
				
				html[2] = '<h1>What does it mean?</h1>';
				html[2] += '<p>';
				html[2] += 'A set where all data points are closely packed near the fit line is highly correlated whereas a set which is very spread out in uncorrelated.';
				html[2] += ' The sign of the slope of the fit line determines the sign of the set correlation, so a large, negative correlation does not mean a set is uncorrelated, just negatively correlated.';
				html[2] += '</p>';
				html[2] += '<br></br>';
				html[2] += '<p>';
				html[2] += ' If a set is positively correlated it means that an increase in one value often occurs with an increase in the other. Negative correlation means an increase in one often occurs with a decrease in the other.';
				html[2] += ' The larger the absolute value of the correlation, the stronger the connection between the two values. A correlation with absolute value of 1 means that one value can exactly determine the other using the linear regression equation.';
				html[2] += '</p>';
				html[2] += '<br></br>';
				html[2] += '<p>';
				html[2] += 'For example, if one dataset is the stock price of USO and the other is the stock price of DJIA offset by one day and their correlation is 1 then today\'s price of USO could be plugged into the linear regression equation to exactly predict tomorro\'s price of DJIA.';
				html[2] += '</p>';
				$('.info').qtip({
				    style: {
				    	widget: true,
				    	def: false,
				    	width: '70%'
				    },
				    position: {
			            my: 'bottom center',
			            at: 'top center',
				    	target: $('#1')
			        },
			        content: {
				        text: function() {
					        return html[parseInt($(this).attr('id'))];
				        }
			        },
			        hide: {
			        	fixed: true
			        }
				});
			}
		</script>
	</div>
</body>
</html>
