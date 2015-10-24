<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Adjacency Matrix</title>
<style>
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
	<asset:javascript src="d3.min.js" />
	<asset:javascript src="datepickers.js" />
	<asset:javascript src="sorting.js" />
	<asset:javascript src="nodes.js" />
	<asset:javascript src="plots/matrix.js" />
	<div id='content' style="padding: 10px;">
		<g:if test="${flash.message}">
			<div class='errors'>
				${flash.message}
			</div>
		</g:if>
		<g:render template="../layouts/menu" />
		<div id="matrix" class="matrix" style="width: 100%; text-align: center;"></div>
		<div style="text-align: center;">
			<div id="0" class="info"><table><tr><td><img style="width: 20px; padding: 3px; display: inline-block;" src="/assets/info.png"></td><td>How do I use it?</td></tr></table></div>
			<div id="1" class="info"><table><tr><td><img style="width: 20px; padding: 3px; display: inline-block;" src="/assets/info.png"></td><td>What does it show?</td></tr></table></div>
			<div id="2" class="info"><table><tr><td><img style="width: 20px; padding: 3px; display: inline-block;" src="/assets/info.png"></td><td>What does it mean?</td></tr></table></div>
		</div>
	</div>
	<script type="text/javascript">
		var dataSet = [];
		var fullData;

		// Sets initial values and sets the root accordian for the first time.
		$(document).ready(function() {
			setDatePickers()
			validate();
			qtip();
		});

		// Function runs after AJAX call is completed. Resets accordian.
		function ajaxComplete(ajaxData) {
			fullData = ajaxData;
			if (fullData.success == false) {
				alert(fullData.message);
				return;
			}
			dataSet = ajaxData[0].data;
			var matrixData = formatAllData(dataSet, 30, null);
			setMatrix(matrixData, true, 150, true)
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
			qtipHtml[0] = '<h1>How do I use it?</h1>';
			qtipHtml[0] += '<p>';
			qtipHtml[0] += 'Start by selecting an input type, an input values, and a date range.';
			qtipHtml[0] += ' Once the inputs have been submitted a matrix will appear showing the relationships between the top 30 entity results.';
			qtipHtml[0] +='</p>';
			
			qtipHtml[1] = '<h1>What does it show?</h1>';
			qtipHtml[1] += '<p>';
			qtipHtml[1] += 'The matrix page provided a view into how entities are related.';
			qtipHtml[1] += ' Entities within news stories are aggregated and the top 30 are displayed in a matrix.';
			qtipHtml[1] +='</p>';
			
			qtipHtml[2] = '<h1>What does it mean?</h1>';
			qtipHtml[2] += '<p>';
			qtipHtml[2] += 'The matrix cells show the number of stories both column and row entities were present.';
			qtipHtml[2] += ' Highly correlated entities will show up with a dark cell and can help users discover hidden connections between entities.';
			qtipHtml[2] += '</p>';
		}
	</script>
</body>
</html>
