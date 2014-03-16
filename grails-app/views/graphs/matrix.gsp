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
			var matrixData = formatAllData(dataSet, $('#nodeNum').val() == -1 ? null : $('#nodeNum').val(), null);
			setMatrix(matrixData, true, 150)
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
			html[0] += 'Start by selecting an input type, an input values, and a date range.';
			html[0] += ' Entities can also be sorted by the "Sort By" and "Order" criteria.';
			html[0] += ' After adding all inputs press the "Submit" button.';
			html[0] +='</p>';
			html[0] +='<br></br>';
			html[0] += '<p>';
			html[0] += 'Once the inputs have been submitted a data accordian will be created which can be clicked on and expanded.';
			html[0] += ' Inside each data set there is an "Entities" tab which can also be expanded.';
			html[0] +='</p>';
			
			html[1] = '<h1>What does it show?</h1>';
			html[1] += '<p>';
			html[1] += 'The Sentiment page shows news story and Tweets which match the input keyword.';
			html[1] += ' The stories are ranked in descending order of relevance and contain information about publish date, title, description, and a link to the story.';
			html[1] += ' The entitles accordian provides information about the top entities within the story and information about them.';
			html[1] +='</p>';
			
			html[2] = '<h1>What does it mean?</h1>';
			html[2] += '<p>';
			html[2] += 'Each story has information about it, but the real information comes from the entities within them.';
			html[2] += ' The frequency reflects the number of time each entity shows up in the article and the type is what type of entity it is.';
			html[2] += ' The sentiment and significance are values generated from the sentiment analysis engine we use.';
			html[2] += ' The significance is a generated value of how significant the entity is within the article and the sentiment is a measure of how the article "feels" about the entity.';
			html[2] += ' This means that an entity which has a large, positive sentiment is written about positivly and vice versa.';
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
</body>
</html>