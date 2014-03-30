<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Decision Tree</title>
<style>
	.jqplot-table-legend {
		width: auto;
	}
	.node circle {
	  cursor: pointer;
	  fill: #fff;
	  stroke: steelblue;
	  stroke-width: 1.5px;
	}
	.node text {
	  font-size: 15px;
	}
	path.link {
	  fill: none;
	  stroke: #ccc;
	  stroke-width: 1.5px;
	}
</style>
</head>
<body>
	<g:javascript src="d3.min.js" />
	<g:javascript src="plots/decisionTree.js" />
	<div id='content' style='padding: 10px;'>
		<g:render template="../layouts/menu" />
		<div style="text-align: center; padding: 5px;">
			<div id="chart1"></div>
		</div>
		<button class="buttons" class="button-reset">Reset</button>
		<g:render template="../layouts/qtip" />
		<script type="text/javascript">
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
				$('#chart1').height('600px');
				if(plot1) {
					$('#chart1').empty();
				}
				plot()
			}

			// Function runs after AJAX call is completed. Creates additional data sets (daily change, change since start) and replots the graph.
			function ajaxComplete(ajaxData) {
				console.log(ajaxData)
				drawTree(ajaxData, $('#chart1').width(), Math.min($(window).height() * .8, $('#chart1').width()))
			}

			// Refreshes the plot.
			function replot() {
				$('#chart1').empty();
				resize()
			}

			function qtip() {
				refreshQtip()
				var html = []
				html[0] = '<h1>How do I use it?</h1>';
				html[0] += '<p>';
				html[0] += 'Add a new plot line by selecting an input type, typing an input value, and clicking the "Add" button.';
				html[0] += ' Added inputs are shown in the "Currently Added Inputs" table and can be removed with the "Remove" button.';
				html[0] += ' You can also clear all inputs by clicking the "Clear" button.';
				html[0] += ' After adding all inputs press the "Submit" button.';
				html[0] +='</p>';
				html[0] +='<br></br>';
				html[0] += '<p>';
				html[0] += 'Once the inputs have been plotted you can hover over each data point to get additional information.';
				html[0] += ' Dragging across the graph will zoom into that area and double clicking the graph or clicking the "Reset Zoom" button will reset the zoom.';
				html[0] +='</p>';
				
				html[1] = '<h1>What does it show?</h1>';
				html[1] += '<p>';
				html[1] += 'The graph plot shows a simple graph over time of any number of inputs.';
				html[1] += ' Inputs can have different units so the first unit of the first input set will appear on the primary (left) axis and the second unique unit will appear on the secondary (right) axis.';
				html[1] += ' Hovering over a data point will reveal additional information such as set name, date, and value at that point.';
				html[1] += ' Clicking on a set name within the legend will toggle the display of that set.';
				html[1] +='</p>';
				
				html[2] = '<h1>What does it mean?</h1>';
				html[2] += '<p>';
				html[2] += 'The graph provides a visual representation of a data set and creates an easy to use and intuitive interface for data.';
				html[2] += ' It also provides a visual comparison between different sets which can then be compared more thoroughly on the correlation or covariance pages.';
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
