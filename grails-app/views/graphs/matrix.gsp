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
	<g:javascript src="d3.min.js" />
	<g:javascript src="datepickers.js" />
	<g:javascript src="sorting.js" />
	<g:javascript src="nodes.js" />
	<g:javascript src="plots/matrix.js" />
	<div id='content' style="padding: 10px;">
		<g:if test="${flash.message}">
			<div class='errors'>
				${flash.message}
			</div>
		</g:if>
		<g:render template="../layouts/menu" />
		<div id="matrix" class="matrix" style="width: 100%; text-align: center;"></div>
		<div style="text-align: center;">
			<div id="0" class="info"><table><tr><td><img style="width: 20px; padding: 3px; display: inline-block;" src="${resource(dir: 'images', file: 'info.png')}"></td><td>How do I use it?</td></tr></table></div>
			<div id="1" class="info"><table><tr><td><img style="width: 20px; padding: 3px; display: inline-block;" src="${resource(dir: 'images', file: 'info.png')}"></td><td>What does it show?</td></tr></table></div>
			<div id="2" class="info"><table><tr><td><img style="width: 20px; padding: 3px; display: inline-block;" src="${resource(dir: 'images', file: 'info.png')}"></td><td>What does it mean?</td></tr></table></div>
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
			qtipHtml[0] += ' Entities can also be sorted by the "Sort By" and "Order" criteria.';
			qtipHtml[0] += ' After adding all inputs press the "Submit" button.';
			qtipHtml[0] +='</p>';
			qtipHtml[0] +='<br></br>';
			qtipHtml[0] += '<p>';
			qtipHtml[0] += 'Once the inputs have been submitted a data accordian will be created which can be clicked on and expanded.';
			qtipHtml[0] += ' Inside each data set there is an "Entities" tab which can also be expanded.';
			qtipHtml[0] +='</p>';
			
			qtipHtml[1] = '<h1>What does it show?</h1>';
			qtipHtml[1] += '<p>';
			qtipHtml[1] += 'The Sentiment page shows news story and Tweets which match the input keyword.';
			qtipHtml[1] += ' The stories are ranked in descending order of relevance and contain information about publish date, title, description, and a link to the story.';
			qtipHtml[1] += ' The entitles accordian provides information about the top entities within the story and information about them.';
			qtipHtml[1] +='</p>';
			
			qtipHtml[2] = '<h1>What does it mean?</h1>';
			qtipHtml[2] += '<p>';
			qtipHtml[2] += 'Each story has information about it, but the real information comes from the entities within them.';
			qtipHtml[2] += ' The frequency reflects the number of time each entity shows up in the article and the type is what type of entity it is.';
			qtipHtml[2] += ' The sentiment and significance are values generated from the sentiment analysis engine we use.';
			qtipHtml[2] += ' The significance is a generated value of how significant the entity is within the article and the sentiment is a measure of how the article "feels" about the entity.';
			qtipHtml[2] += ' This means that an entity which has a large, positive sentiment is written about positivly and vice versa.';
			qtipHtml[2] += '</p>';
		}
	</script>
</body>
</html>
