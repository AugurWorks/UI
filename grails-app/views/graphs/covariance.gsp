<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Covariance</title>
<link rel="stylesheet" href="${resource(dir: 'js', file: 'jquery.jqplot.css')}" type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'covariance.css')}" type="text/css">

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
	<g:javascript src="jquery.jsanalysis.js" />
	<g:javascript src="datepickers.js" />
	<g:javascript src="ajaxData.js" />
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	<div id='content' style='padding: 10px;'>
		<div class="message" id="invalidMessage" style="display: none;"></div>
		Select: <g:select name="input1" from="${ dataTypes }" optionKey="name" />
		Input: <g:textField type="text" name="input2" value="USO" />
		<div id="start">Start date: <g:textField type="text" id="startDate" name="startDate" value="${startDate}" /></div>
		<div id="end">End date: <g:textField type="text" id="endDate" name="endDate" value="${endDate}" /></div>
		<div id="off">Offset: <g:textField type="text" id="offset" name="offset" value="0" /></div>
		<button onclick="add()">Add</button>
		<button onclick="clearTable()">Clear</button>
		<button onclick="validate()">Go!</button><br></br>
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
				req[0] = {name: 'DJIA', dataType: $('#input1').val(), startDate: $('#startDate').val(), endDate: $('#endDate').val()};
				req[1] = {name: 'T', dataType: $('#input1').val(), startDate: $('#startDate').val(), endDate: $('#endDate').val()};
				req[2] = {name: 'JPM', dataType: $('#input1').val(), startDate: $('#startDate').val(), endDate: $('#endDate').val()};
				req[3] = {name: 'USO', dataType: $('#input1').val(), startDate: $('#startDate').val(), endDate: $('#endDate').val()};
				drawTable();
				validate();
				clearTable();
			});

			// Adds a query to the request object and redraws the table
			function add() {
				if (Object.keys(req).length == 0) {
					req[counter] = {name: $('#input2').val().replace(" ",""), dataType: $('#input1').val(), startDate: $('#startDate').val(), endDate: $('#endDate').val()};
					$('#start').hide();
					$('#end').hide();
					$('#off').show();
				} else {
					req[counter] = {name: $('#input2').val().replace(" ",""), dataType: $('#input1').val(), startDate: calcNewDate($('#startDate').val(), parseInt($('#offset').val())), endDate: calcNewDate($('#endDate').val(), parseInt($('#offset').val()))};
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
				$('#end').show();
			}

			// Draws a table showing current requests
			function drawTable() {
				var text = "<table><tr><td>Name</td><td>Data Type</td><td>Start Date</td><td>End Date</td><td>Remove</td></tr>"
				for (i in req) {
					text += '<tr><td>'
					text += req[i].name
					text += '</td><td>'
					text += req[i].dataType
					text += '</td><td>'
					text += req[i].startDate
					text += '</td><td>'
					text += req[i].endDate
					text += '</td><td><button onclick="removeReq(' + i + ')">Remove</button></td></tr>'
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
				if (Object.keys(ajaxData).length > 1) {
					var vals = [];
					var html = '<table id="covariance"><tr><td></td>';
					for (i in ajaxData) {
						vals.push(ajaxData[i]['metadata']['name'] + ' - ' + ajaxData[i]['metadata']['dataType'])
						html += '<td>' + ajaxData[i]['metadata']['name'] + ' - ' + ajaxData[i]['metadata']['dataType'] + '</td>';
					}
					html += '</tr>';
					var array = []
					for (var i = 0; i < vals.length; i++){
						array.push([])
					}
					for (i2 in vals) {
						for (j2 in vals) {
							if (i2 < j2) {
								var cor = calcCorrelation(ajaxData, Object.keys(ajaxData)[i2], Object.keys(ajaxData)[j2])
								array[i2][j2] = cor
								array[j2][i2] = cor
							} else if (i2 == j2) {
								array[i2][j2] = 1
							}
						}
					}
					for (i in vals) {
						html += '<tr><td>' + vals[i] + '</td>'
						for (j in vals) {
							html += '<td style="color: rgb(' + Math.round(255 / 2 * (1 + array[i][j])) + ', ' + Math.round(255 / 2 * (1 + array[i][j])) + ', 255);'
							html += '"><b>' + array[i][j].toFixed(4) + '</b></td>'
						}
						html += '</tr>'
					}
					html += '</table>'
					$('#chart1').html(html)
				} else {
					$('#chart1').html('Please input two or more valid inputs.')
				}
			}
		</script>
	</div>
</body>
</html>
