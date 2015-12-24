<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Graph</title>
<asset:stylesheet href="graphs/calendar.css" />
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
	<asset:javascript src="d3.min.js" />
	<asset:javascript src="plots/calendar.js" />
	<div id='content' style='padding: 10px;'>
		<g:render template="../layouts/menu" />
		<div style="text-align: center; padding: 20px;">
			<div id="chart1"></div>
		</div>
		<g:render template="../layouts/qtip" />
		<script type="text/javascript">
			counter = 0
			$(document).ready(function() {
				setDatePickers();
				drawTable();
				ajaxCall(req, "${g.createLink(controller:'data',action:'ajaxData')}")
				qtip();
			});

			// Resize the plot on window resize
			window.onresize = resize
			function resize() {
				var size = Math.min(window.innerWidth - 150, window.innerHeight - 100)
				$('#chart1').width('100%');
				if (plot1) {
					$('#chart1').empty();
				}
				plotCalendar('chart1', fullAjaxData[Object.keys(fullAjaxData)[0]].dates, fullAjaxData[Object.keys(fullAjaxData)[0]].metadata)
			}

			// Function runs after AJAX call is completed. Creates additional data sets (daily change, change since start) and replots the graph.
			function ajaxComplete(ajaxData) {
				fullAjaxData = ajaxData
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

			function qtip() {
				refreshQtip()
				qtipHtml[0] = '<h1>How do I use it?</h1>';
				qtipHtml[0] += '<p>';
				qtipHtml[0] += 'The calendar page only requires one input. Once you submit the inputs a calendar heatmap will be created.';
				qtipHtml[0] += ' Hovering over each calendar cell will show additional information about the day.';
				qtipHtml[0] +='</p>';
				
				qtipHtml[1] = '<h1>What does it show?</h1>';
				qtipHtml[1] += '<p>';
				qtipHtml[1] += 'The calendar is an easy way to get an intuitive view of data over a long period of time.';
				qtipHtml[1] += " Each day's value is converted to a value: red for the lowest, yellow for the middle, green for the highest, and combinations of each for in between.";
				qtipHtml[1] += ' Hovering over each day shows additional information such as the exact day and value.';
				qtipHtml[1] +='</p>';
				
				qtipHtml[2] = '<h1>What does it mean?</h1>';
				qtipHtml[2] += '<p>';
				qtipHtml[2] += 'Shown is a heatmap of daily values over a long period of time.';
				qtipHtml[2] += ' Users can quickly identify the maximum and minimum values in a data sets as well as get a feel for how extreme the distribution of values is.';
				qtipHtml[2] += ' A uniform color distribution might mean that values are consistently within a range or that there are few sudden shanges.';
				qtipHtml[2] += ' Seeing mostly yellow values most values are within a certain range, but there are a few extreme values (i.e. sudden price increases or decreases).';
				qtipHtml[2] += ' A distribution like this might warrent further investigation to find what the cause of the extreme changes are.';
				qtipHtml[2] += '</p>';
			}
		</script>
	</div>
</body>
</html>
