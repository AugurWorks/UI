<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Covariance</title>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'covariance.css')}" type="text/css">
<style type="text/css">
	#legend {
		background: -webkit-linear-gradient(left, #FF0000, #FFFFFF, #0000FF); /* For Safari */
		background: -o-linear-gradient(left, #FF0000, #FFFFFF, #0000FF); /* For Opera 11.1 to 12.0 */
		background: -moz-linear-gradient(left, #FF0000, #FFFFFF, #0000FF); /* For Firefox 3.6 to 15 */
		background: linear-gradient(left, #FF0000, #FFFFFF, #0000FF); /* Standard syntax */
	}
	.legend {
		width: 200px;
		height: 30px;
		text-align: center;
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
	<g:javascript src="jquery.blockUI.js" />
	<g:javascript src="jquery.jsanalysis.js" />
	<g:javascript src="jquery-ui.min.js" />
	<div id='content' style='padding: 10px;'>
		<div class='errors' id="invalidMessage" style="display: none;"></div>
		<div class="buttons">
			<div class="button-line">
				<div class="qtipText" title="Select a type of data to plot.">Select: <g:select name="input1" from="${ dataTypes }" optionKey="name" /></div>
				<div class="qtipText" title="Input a value such as USO or Tesla for a stock or Oil for sentiment.">Input: <g:textField type="text" name="input2" value="USO" /></div>
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
			<button class="buttons" onclick="add($('#input2').val().toUpperCase(), $('#input1').val(), $('#startDate').val(), $('#endDate').val(), getTickerUrl, $('#offset').val())">Add</button>
			<button class="buttons" onclick="clearTable()">Clear</button>
		</div>
		<br></br>
		<div id="results"></div>
		<h1 style="text-align: center;" id="message"></h1>
		<h4>Currently Added Inputs</h4>
		<div id="table"></div>
		<div class="button-line">
			<button class="buttons" style="font-size: large;" onclick="validate()">Submit</button>
		</div>
		<div style="text-align: center; padding: 20px;">
			<div id="chart1"></div>
		</div>
		<div>
			<h3>Legend</h3>
			<div id="legend" class="legend"></div>
			<div class="legend"><div style="float: left; display: inline-block;">-1</div><div style="margin: 0 auto; display: inline-block;">0</div><div style="float: right; display: inline-block;">1</div></div>
		</div>
		<div style="text-align: center;">
			<div id="0" class="info"><table><tr><td><img style="width: 20px; padding: 3px; display: inline-block;" src="${resource(dir: 'images', file: 'info.png')}"></td><td>How do I use it?</td></tr></table></div>
			<div id="1" class="info"><table><tr><td><img style="width: 20px; padding: 3px; display: inline-block;" src="${resource(dir: 'images', file: 'info.png')}"></td><td>What does it show?</td></tr></table></div>
			<div id="2" class="info"><table><tr><td><img style="width: 20px; padding: 3px; display: inline-block;" src="${resource(dir: 'images', file: 'info.png')}"></td><td>What does it mean?</td></tr></table></div>
		</div>
		<script type="text/javascript">
			var initilized = false;
			var corBool = true;
			var single = false;
			var req = new Object();
			var tempReq = new Object()
			var getTickerUrl = "${g.createLink(controller:'data', action:'getTicker')}";
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
				if (Object.keys(req).length == 0) {
					$('#off').hide();
					$('#start').show();
				}
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
			var invalid = [];
			var valid = [];
			var ajaxData = [];

			// Function runs after AJAX call is completed. Creates additional data sets (daily change, change since start) and replots the graph.
			function ajaxComplete(data) {
				invalid = [];
				valid = [];
				ajaxData = data;
				for (i in ajaxData) {
					if (ajaxData[i].metadata.valid) {
						valid.push(i);
					} else {
						invalid.push(i);
					}
				}
				if (valid.length > 1) {
					drawCorTable();
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

			function drawCorTable() {
				var vals = [];
				var html = '<table id="covariance"><tr><td><button class="buttons" onclick="toggle()">Toggle Cor/Cov</button><div id="tabVal">Correlation</div></td>';
				for (i in valid) {
					vals.push(ajaxData[valid[i]].metadata.req.name + ' - ' + ajaxData[valid[i]].metadata.req.dataType);
					html += '<td><div>' + ajaxData[valid[i]].metadata.req.name + ' - ' + ajaxData[valid[i]].metadata.req.dataType + '</div>';
					html += '<div>Offset: ' + ajaxData[valid[i]].metadata.req.offset + '</div></td>';
				}
				html += '</tr>';
				var corr = [];
				var cov = [];
				for (var i = 0; i < vals.length; i++){
					corr.push([])
					cov.push([])
				}
				for (i2 in vals) {
					for (j2 in vals) {
						if (i2 < j2) {
							var cor = calcCorrelation(ajaxData, valid[i2], valid[j2]);
							corr[i2][j2] = cor[0]
							corr[j2][i2] = cor[0]
							cov[i2][j2] = cor[1]
							cov[j2][i2] = cor[1]
						} else if (i2 == j2) {
							var cor = calcCorrelation(ajaxData, valid[i2], valid[j2]);
							corr[i2][j2] = 1
							cov[i2][j2] = cor[1]
						}
					}
				}
				var temp = []
				if (corBool) {
					temp = corr;
				} else {
					temp = cov;
				}
				for (i in vals) {
					html += '<tr><td><div>' + vals[i] + '</div>';
					html += '<div>Offset: ' + ajaxData[valid[i]].metadata.req.offset + '</div></td>';
					for (j in vals) {
						if (corr[i][j] > 0) {
							html += '<td style="color: rgb(' + Math.round(255 * (1 - corr[i][j])) + ', ' + Math.round(255 * (1 - corr[i][j])) + ', 255);';
						} else {
							html += '<td style="color: rgb(' + 176 + Math.round(79 * (1 + corr[i][j])) + ', ' + Math.round(255 * (1 + corr[i][j])) + ', ' + Math.round(255 * (1 + corr[i][j])) + ');';
						}
						html += '"><b>' + temp[i][j].toFixed(4) + '</b></td>'
					}
					html += '</tr>'
				}
				html += '</table>'
				$('#chart1').html(html);
			}

			function toggle() {
				corBool = !corBool;
				drawCorTable();
				if (!corBool) {
					$('#tabVal').html('Covariance');
				} else  {
					$('#tabVal').html('Correlation');
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
				html[0] += 'Add a new plot line by selecting an input type, typing an input value, and clicking the "Add" button.';
				html[0] += ' Added inputs are shown in the "Currently Added Inputs" table and can be removed with the "Remove" button.';
				html[0] += ' You can also clear all inputs by clicking the "Clear" button.';
				html[0] += ' After adding the first input the date range inputs will be replaced with an offset input.';
				html[0] += ' This is because all inputs must have the same set length to be compared, so only and offset can be set.';
				html[0] += ' Once all inputs have been added press the "Submit" button.';
				html[0] +='</p>';
				html[0] +='<br></br>';
				html[0] += '<p>';
				html[0] += 'After the data has been calculated a button will appear in the top left corner which will toggle the table values between correlation and covariance.';
				html[0] +='</p>';
				
				html[1] = '<h1>What does it show?</h1>';
				html[1] += '<p>';
				html[1] += 'The covariance table shows either the covariance or correlation (there is a toggle button) between each combination of data sets.';
				html[1] += ' Darker blue means more positive correlation and darker red means more negative correlation. Lighter text means less correlation.';
				html[1] +='</p>';
				
				html[2] = '<h1>What does it mean?</h1>';
				html[2] += '<p>';
				html[2] += '<a href="http://en.wikipedia.org/wiki/Correlation_and_dependence" target="_blank">Correlations</a> are measures of how related two datasets are and are always between -1 and 1.';
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
				html[2] += '<br></br>';
				html[2] += '<p>';
				html[2] += '<a href="http://en.wikipedia.org/wiki/Covariance" target="_blank">Covariance</a> is a non-normalized correlation.';
				html[2] += ' It is not as helpful of a value for comparing data sets, but can be used in calculating risk profiles for portfolios.';
				html[2] += '</p>';
				html[2] += '<br></br>';
				html[2] += '<p>';
				html[2] += ' <a href="http://en.wikipedia.org/wiki/Modern_portfolio_theory#Risk_and_expected_return" target="_blank">Modern portfolio theory</a> provides a formula for calculating a portfolio\'s risk, but the formula is just a simple <a href="http://en.wikipedia.org/wiki/Variance#Sum_of_correlated_variables" target="_blank">variance calculation between correlated variables</a>.';
				html[2] += ' When diversifying a portfolio a larger correlation is bad because the addition of the security does not reduce the portfolio\'s risk through diversification as much as adding a security which is uncorrelated with the rest of the portfolio.';
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
