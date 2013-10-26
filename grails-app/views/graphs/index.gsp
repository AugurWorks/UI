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
	<g:javascript src="custom.js" />
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	<div id='content' style='padding: 10px;'>
		<div class="message" id="message" style="display: none;">
			${flash.message}
		</div>
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
			$(function() {
			    	$("#startDate").datepicker({maxDate: -2, onSelect: setDateLimits});
				});

			$(function() {
			    	$("#endDate").datepicker({maxDate: 0, onSelect: setDateLimits});
			  	});
			function setDateLimits(date, inst) {
				var d = new Date(date)
				if (inst.id == "endDate") {
					var n = new Date(d.getFullYear(), d.getMonth(), d.getDate() - 2);
					console.log(n)
					$("#startDate").datepicker("option", "maxDate", n.getMonth() + 1 + "/" + n.getDate() + "/" + n.getFullYear())
				} else {
					var n = new Date(d.getFullYear(), d.getMonth(), d.getDate() + 2);
					console.log(n)
					$("#endDate").datepicker("option", "minDate", n.getMonth() + 1 + "/" + n.getDate() + "/" + n.getFullYear())
				}
			}
				
			var initilized = false
			var today = '${endDate}'
			$(document).ready(function() {
				validate()
				initilized = true
			});
			
			function validate() {
				var stocks = $('#stock').val()
				var startDate = $('#startDate').val()
				var endDate = $('#endDate').val()
				var parsed = checkDates([startDate, endDate])
				var resp = $.ajax({
					url: "${g.createLink(controller:'graphs',action:'stockData')}",
					dataType: 'json',
				    data: {
					    stock : stocks,
				        startDate : startDate,
				        endDate : endDate
				    },
				    success: function(data) {
					    setData(data)
				    },
				    error: function(request, status, error) {
					    console.log(request)
				        alert(error)
				    },
				    complete: function() {
					    if (!initilized) {
					    	plot(3)
					    } else {
							replot(3)
						}
				    }
				});
			}
			
			var dataSet;
			var stockArray = [];
			var seriesArray = []
			var plot1;
			
			function setData(data) {
				var set = data[0].root
				var listArray1 = []
				var listArray2 = []
				var listArray3 = []
				var invalid = []
				for (stock in set) {
					seriesArray.push({
						showMarker : false
					})
					var list1 = []
					var list2 = []
					var list3 = []
					var temp = []
					var len = Object.keys(set[stock]).length
					if (set[stock].val != undefined) {
						invalid.push(stock)
					} else {
						
						$.each(
								set[stock],
								function(index, value) {
									temp.push([index, parseFloat(value) ])
								});
						var counter = 0
						$.each(
								set[stock],
								function(index, value) {
									list1.push([index, parseFloat(value)])
									if (counter > 0) {
										list2.push([index, (temp[counter][1] - temp[counter - 1][1]) / temp[counter - 1][1] * 100])
										list3.push([index, (temp[counter][1] - temp[len - 1][1]) / temp[len - 1][1] * 100])
									}
									counter += 1
								});
						stockArray.push(stock)
						listArray1.push(list1)
						listArray2.push(list2)
						listArray3.push(list3)
					}
				}
				dataSet = [listArray1, listArray2, listArray3]
				if (invalid.length == 1) {
					$('#message').text(invalid[0] + " is invalid.")
					$('#message').show()
				} else if (invalid.length > 1) {
					$('#message').text(invalid.join(", ") + " are invalid.")
					$('#message').show()
				} else {
					$('#message').hide()
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
