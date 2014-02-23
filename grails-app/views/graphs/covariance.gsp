<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Covariance</title>
<link rel="stylesheet" href="${resource(dir: 'css/graphs', file: 'covariance.css')}" type="text/css">
<style type="text/css">
	.background {
	  fill: #eee;
	}
	
	line {
	  stroke: #fff;
	}
	
	text.active {
	  fill: red;
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
	<g:javascript src="jQuery/jquery.jsanalysis.js" />
	<g:javascript src="plots/matrix.js" />
	<g:javascript src="d3.min.js" />
	<div id='content' style='padding: 10px;'>
		<g:render template="../layouts/menu" />
		<div id="matrix" class="matrix" style="width: 95%; text-align: center;"></div>
		<g:render template="../layouts/qtip" />
		<script type="text/javascript">
			var corBool = true;
			counter = 4;
			$(document).ready(function() {
				setDatePickers()
				drawTable();
				ajaxCall(req, "${g.createLink(controller:'data',action:'ajaxData')}")
				$('#start').hide();
				qtip();
			});

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
					drawCorTable(ajaxData);
				}
				var html = '';
				if (valid.length < 2) {
					html += 'Please input two or more valid inputs. ';
				}
				if (invalid.length == 1) {
					html += ajaxData[invalid[0]].metadata.req.name + ' is invalid.';
				} else if (invalid.length > 1) {
					for (i in Object.keys(invalid)) {
						html += ajaxData[invalid[i]].metadata.req.name + ', '
					}
					html = html.substring(0, html.length - 2) + ' are invalid.';
				}
				$('#message').html(html);
			}

			function drawCorTable(ajaxData) {
				var vals = [];
				var matrixData = {'nodes': [], 'links': []};
				for (i in valid) {
					vals.push(ajaxData[valid[i]].metadata.req.name + ' - ' + ajaxData[valid[i]].metadata.req.dataType);
					matrixData.nodes.push({'name': ajaxData[valid[i]].metadata.req.name + '-' + ajaxData[valid[i]].metadata.req.dataType + ', O: ' + ajaxData[valid[i]].metadata.req.offset, 'group': ajaxData[valid[i]].metadata.req.dataType, 'offset': ajaxData[valid[i]].metadata.req.offset})
				}
				for (i2 in vals) {
					for (j2 in vals) {
						if (i2 < j2) {
							var cor = calcCorrelation(ajaxData, valid[i2], valid[j2]);
							matrixData.links.push({'source': parseInt(i2), 'target': parseInt(j2), 'value': cor[0]})
						}
					}
				}
				setMatrix(matrixData, false, 100)
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
				refreshQtip()
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
