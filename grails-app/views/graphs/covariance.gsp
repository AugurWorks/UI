<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Covariance</title>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jqplot.css')}" type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'covariance.css')}" type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.qtip.min.css')}" type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery-ui.min.css')}" type="text/css">

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
	<g:javascript src="jquery.jsanalysis.js" />
	<g:javascript src="datepickers.js" />
	<g:javascript src="ajaxData.js" />
	<g:javascript src="jquery-ui.min.js" />
	<div id='content' style='padding: 10px;'>
		<div class="message" id="invalidMessage" style="display: none;"></div>
		<div class="buttons">
			<div class="button-line">
				<div class="qtipText" title="Select a type of data to plot.">Select: <g:select name="input1" from="${ dataTypes }" optionKey="name" /></div>
				<div class="qtipText" title="Input a value such as USO for a stock or Oil for sentiment.">Input: <g:textField type="text" name="input2" value="USO" /></div>
			</div>
			<div class="button-line">
				<div id="start">
					Start date: <g:textField style="width: 90px;" type="text" id="startDate" name="startDate" value="${startDate}" />
					End date: <g:textField style="width: 90px;" type="text" id="endDate" name="endDate" value="${endDate}" />
				</div>
				<div id="off" class="qtipText" title="Input a number of business days for this set to be offset from the initial dataset date range.">Offset: <input style="width: 60px;" type="number" id="offset" name="offset" value="0" /></div>
			</div>
		</div>
		<div class="button-line">
			<button class="buttons" onclick="add()">Add</button>
			<button class="buttons" onclick="clearTable()">Clear</button>
			<button class="buttons" onclick="validate()">Submit</button>
		</div>
		<br></br>
		<h1 style="text-align: center;" id="message"></h1>
		<h4>Currently Added Inputs</h4>
		<div id="table"></div>
		<div style="text-align: center; padding: 20px;">
			<div id="chart1"></div>
		</div>
		<script type="text/javascript">
			var initilized = false;
			var req = new Object();
			counter = 4;
			$(document).ready(function() {
				setDatePickers()
				req[0] = {name: 'DJIA', dataType: $('#input1').val(), startDate: $('#startDate').val(), endDate: $('#endDate').val(), offset: 0};
				req[1] = {name: 'T', dataType: $('#input1').val(), startDate: $('#startDate').val(), endDate: $('#endDate').val(), offset: 0};
				req[2] = {name: 'JPM', dataType: $('#input1').val(), startDate: $('#startDate').val(), endDate: $('#endDate').val(), offset: 0};
				req[3] = {name: 'USO', dataType: $('#input1').val(), startDate: $('#startDate').val(), endDate: $('#endDate').val(), offset: 0};
				drawTable();
				validate();
				$('#start').hide();
				qtip();
			});

			// Adds a query to the request object and redraws the table
			function add() {
				if (Object.keys(req).length == 0) {
					req[counter] = {name: $('#input2').val().replace(" ",""), dataType: $('#input1').val(), startDate: $('#startDate').val(), endDate: $('#endDate').val(), offset: 0};
					$('#start').hide();
					$('#off').show();
				} else {
					req[counter] = {name: $('#input2').val().replace(" ",""), dataType: $('#input1').val(), startDate: calcNewDate($('#startDate').val(), parseInt($('#offset').val())), endDate: calcNewDate($('#endDate').val(), parseInt($('#offset').val())), offset: $('#offset').val()};
				}
				drawTable();
				counter += 1;
			}

			// Clears the request object and redraws the table
			function clearTable() {
				req = new Object();
				drawTable();
				$('#off').hide();
				$('#start').show();
			}

			// Draws a table showing current requests
			function drawTable() {
				var text = "<table><tr><th>Name</th><th>Data Type</th><th>Start Date</th><th>End Date</th><th>Offset</th><th>Remove</th></tr>"
				for (i in req) {
					text += '<tr><td>'
					text += req[i].name
					text += '</td><td>'
					text += req[i].dataType
					text += '</td><td>'
					text += req[i].startDate
					text += '</td><td>'
					text += req[i].endDate
					text += '</td><td>'
					text += req[i].offset
					text += '</td><td><button class="buttons" onclick="removeReq(' + i + ')">Remove</button></td></tr>'
				}
				text += "</table>"
				$('#table').html(text)
			}

			function removeReq(i) {
				delete req[i];
				drawTable();
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
				var invalid = [];
				var valid = [];
				for (i in ajaxData) {
					if (ajaxData[i].metadata.valid) {
						valid.push(i);
					} else {
						invalid.push(i);
					}
				}
				if (valid.length > 1) {
					var vals = [];
					var html = '<table id="covariance"><tr><td></td>';
					for (i in valid) {
						vals.push(ajaxData[valid[i]].metadata.req.name + ' - ' + ajaxData[valid[i]].metadata.req.dataType);
						html += '<td><div>' + ajaxData[valid[i]].metadata.req.name + ' - ' + ajaxData[valid[i]].metadata.req.dataType + '</div>';
						html += '<div>Offset: ' + ajaxData[valid[i]].metadata.req.offset + '</div></td>';
					}
					html += '</tr>';
					var array = []
					for (var i = 0; i < vals.length; i++){
						array.push([])
					}
					for (i2 in vals) {
						for (j2 in vals) {
							if (i2 < j2) {
								var cor = calcCorrelation(ajaxData, valid[i2], valid[j2])
								array[i2][j2] = cor
								array[j2][i2] = cor
							} else if (i2 == j2) {
								array[i2][j2] = 1
							}
						}
					}
					for (i in vals) {
						html += '<tr><td><div>' + vals[i] + '</div>';
						html += '<div>Offset: ' + ajaxData[valid[i]].metadata.req.offset + '</div></td>';
						for (j in vals) {
							if (array[i][j] > 0) {
								html += '<td style="color: rgb(' + Math.round(255 * (1 - array[i][j])) + ', ' + Math.round(255 * (1 - array[i][j])) + ', 255);';
							} else {
								html += '<td style="color: rgb(' + 176 + Math.round(79 * (1 + array[i][j])) + ', ' + Math.round(255 * (1 + array[i][j])) + ', ' + Math.round(255 * (1 + array[i][j])) + ');';
							}
							html += '"><b>' + array[i][j].toFixed(4) + '</b></td>'
						}
						html += '</tr>'
					}
					html += '</table>'
					$('#chart1').html(html)
				}
				var html = '';
				if (valid.length < 2) {
					html += 'Please input two or more valid inputs. ';
				}
				if (invalid.length == 1) {
					html += ajaxData[invalid[0]].metadata.req.name + ' is invalid.';
				} else if (invalid.length > 1) {
					for (i in invalid) {
						html += ajaxData[invalid[1]].metadata.req.name + ', '
					}
					html = html.substring(0, html.length - 2) + ' are invalid.';
				}
				$('#message').html(html);
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
