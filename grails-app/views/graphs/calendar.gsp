<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Graph</title>
<link rel="stylesheet" href="${resource(dir: 'css/graphs', file: 'calendar.css')}" type="text/css">
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
	<g:javascript src="d3.min.js" />
	<g:javascript src="plots/calendar.js" />
	<div id='content' style='padding: 10px;'>
		<div class='errors' id="invalidMessage" style="display: none;"></div>
		<div class="buttons">
			<div class="button-line">
				<div class="qtipText" title="Select a type of data to plot.">Select Input Type: <g:select name="input1" from="${ dataTypes }" optionKey="name" /></div>
				<div class="qtipText" title="Input a value such as USO or Tesla for a stock or Oil for sentiment." id="inputDiv2">Input Value: <g:textField style="width: 90px;" type="text" name="input2" value="USO" /></div>
			</div>
			<div class="button-line">
				Start date: <g:textField style="width: 90px;" type="text" id="startDate" name="startDate" value="${startDate}" />
				End date: <g:textField style="width: 90px;" type="text" id="endDate" name="endDate" value="${endDate}" />
			</div>
		</div>
		<div class="button-line">
			<button id="submit" class="buttons" onclick="add($('#input2').val().toUpperCase(), $('#input1').val(), $('#startDate').val(), $('#endDate').val(), getTickerUrl, null)">Submit</button>
		</div>
		<div id="results"></div>
		<div style="text-align: center; padding: 20px;">
			<div id="chart1"></div>
		</div>
		<div style="text-align: center;">
			<div id="0" class="info"><table><tr><td><img style="width: 20px; padding: 3px; display: inline-block;" src="${resource(dir: 'images', file: 'info.png')}"></td><td>How do I use it?</td></tr></table></div>
			<div id="1" class="info"><table><tr><td><img style="width: 20px; padding: 3px; display: inline-block;" src="${resource(dir: 'images', file: 'info.png')}"></td><td>What does it show?</td></tr></table></div>
			<div id="2" class="info"><table><tr><td><img style="width: 20px; padding: 3px; display: inline-block;" src="${resource(dir: 'images', file: 'info.png')}"></td><td>What does it mean?</td></tr></table></div>
		</div>
		<script type="text/javascript">
			var initilized = false
			var single = true
			var req = new Object()
			var tempReq = new Object()
			var getTickerUrl = "${g.createLink(controller:'data', action:'getTicker')}";
			req[0] = {name: $('#input2').val().toUpperCase(), dataType: $('#input1').val(), startDate: $('#startDate').val(), endDate: $('#endDate').val(), longName: 'United States Oil'}
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
				if (plot1) {
					$('#chart1').empty();
				}
				plotCalendar('chart1', fullAjaxData[Object.keys(fullAjaxData)[0]].dates, fullAjaxData[Object.keys(fullAjaxData)[0]].metadata)
			}

			// Clears the request object and redraws the table
			function clearTable() {
				req = new Object()
				drawTable()
			}

			// Draws a table showing current requests
			function drawTable() {
				var text = "<table><tr><th>Name</th><th>Data Type</th><th>Start Date</th><th>End Date</th><th>Remove</th></tr>";
				for (i in req) {
					var name = req[i].name
					if (req[i].longName) {
						name += ' - ' + req[i].longName;
					}
					text += '<tr><td>'
					text += name
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

			// Runs each time the 'Go!' button is clicked. Retrieves data from the server.
			function validate() {
				ajaxCall(req, "${g.createLink(controller:'data', action:'getData')}")
			}
			
			var dataSet;
			var dataTypes = $.parseJSON("${dataTypeJson}".replace( /\&quot;/g, '"' ))
			var inputArray = [];
			var nameArray = [];
			var seriesArray = []
			var plot1;
			var fullAjaxData;

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

			$('#input1').change(function() {
				changeInput('#input1', '#inputDiv2', 'input2', 'USO', dataTypes)
			})

			// Refreshes the plot.
			function replot() {
				$('#chart1').empty();
				resize()
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
				/*html[0] += 'Add a new plot line by selecting an input type, typing an input value, and clicking the "Add" button.';
				html[0] += ' Added inputs are shown in the "Currently Added Inputs" table and can be removed with the "Remove" button.';
				html[0] += ' You can also clear all inputs by clicking the "Clear" button.';
				html[0] += ' After adding all inputs press the "Submit" button.';
				html[0] +='</p>';
				html[0] +='<br></br>';
				html[0] += '<p>';
				html[0] += 'Once the inputs have been plotted you can hover over each data point to get additional information.';
				html[0] += ' Dragging across the graph will zoom into that area and double clicking the graph or clicking the "Reset Zoom" button will reset the zoom.';*/
				html[0] +='</p>';
				
				html[1] = '<h1>What does it show?</h1>';
				html[1] += '<p>';
				/*html[1] += 'The graph plot shows a simple graph over time of any number of inputs.';
				html[1] += ' Inputs can have different units so the first unit of the first input set will appear on the primary (left) axis and the second unique unit will appear on the secondary (right) axis.';
				html[1] += ' Hovering over a data point will reveal additional information such as set name, date, and value at that point.';
				html[1] += ' Clicking on a set name within the legend will toggle the display of that set.';*/
				html[1] +='</p>';
				
				html[2] = '<h1>What does it mean?</h1>';
				html[2] += '<p>';
				/*html[2] += 'The graph provides a visual representation of a data set and creates an easy to use and intuitive interface for data.';
				html[2] += ' It also provides a visual comparison between different sets which can then be compared more thoroughly on the correlation or covariance pages.';*/
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