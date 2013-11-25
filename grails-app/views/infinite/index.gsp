<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Document Search</title>
</head>
<body>
	<g:javascript src="jquery-ui.js" />
	<g:javascript src="jquery.blockUI.js" />
	<g:javascript src="datepickers.js" />
	<g:javascript src="sorting.js" />
	<div id='content' style="padding: 10px;">
		<g:if test="${flash.message}">
			<div class='errors'>
				${flash.message}
			</div>
		</g:if>
		<div class="buttons">
			<div class="button-line">
				<div class="qtipText" title="Select a type of data.">Select 1: <g:select name="input1" from="${ dataTypes }" optionKey="name" /></div>
				<div class="qtipText" title="Input a keyword to query.">Keyword: <g:textField type="text" name="input2" value="Oil" /></div>
			</div>
			<div class="button-line">
				Start date: <g:textField type="text" id="startDate" name="startDate" value="${startDate}" />
				End date: <g:textField type="text" id="endDate" name="endDate" value="${endDate}" />
			</div>
			<div class="button-line">
				<div class="qtipText" title="Select an attribute to sort entities by.">Sort By: <g:select id="sort" name='sort' from='[[id:"name", name:"Name"], [id:"frequency", name:"Frequency"], [id:"type", name:"Type"], [id:"sentiment", name:"Sentiment"], [id:"significance", name:"Significance"]]' optionKey="id" optionValue="name"></g:select></div>
				<div class="qtipText" title="Select an order to sort entities by.">Order: <g:select id="order" name='order' from='[[id:"asc", name:"Ascending"], [id:"desc", name:"Descending"]]' optionKey="id" optionValue="name"></g:select></div>
			</div>
		</div>
		<div class="button-line">
			<button class="buttons" style="font-size: large;" onclick="validate()">Submit</button>
		</div>
		<div id="accordian" class="accordion" style="margin-top: 20px;"></div>
		<div style="text-align: center;">
			<div id="0" class="info"><table><tr><td><img style="width: 20px; padding: 3px; display: inline-block;" src="${resource(dir: 'images', file: 'info.png')}"></td><td>How do I use it?</td></tr></table></div>
			<div id="1" class="info"><table><tr><td><img style="width: 20px; padding: 3px; display: inline-block;" src="${resource(dir: 'images', file: 'info.png')}"></td><td>What does it show?</td></tr></table></div>
			<div id="2" class="info"><table><tr><td><img style="width: 20px; padding: 3px; display: inline-block;" src="${resource(dir: 'images', file: 'info.png')}"></td><td>What does it mean?</td></tr></table></div>
		</div>
	</div>
	<script type="text/javascript">
		var dataSet = [];

		// Sets initial values and sets the root accordian for the first time.
		$(document).ready(function() {
			$('#sort').val("significance")
			$('#order').val("desc")
			setDatePickers()
			$(function() {
				$(".accordion").accordion({
					event : "click",
					active : false,
					collapsible : true,
					heightStyle : "content"
				});
			});
			validate();
			qtip();
		});

		// Runs each time the 'Go!' button is clicked. Retrieves data from the server.
		function validate() {
			var req = new Object()
			req[0] = {name: $('#input2').val().replace(" ",""), dataType: $('#input1').val(), startDate: $('#startDate').val(), endDate: $('#endDate').val()}
			ajaxCall(req, "${g.createLink(controller:'data', action:'getData')}")
		}

		// Function runs after AJAX call is completed. Resets accordian.
		function ajaxComplete(ajaxData) {
			dataSet = ajaxData[0].data
			setAccordian()
		}

		// Resets the accodian if the sorting or order changes.
		$('#sort').change(setAccordian)
		$('#order').change(setAccordian)

		// Iterates through data and sets the accordian.
		function setAccordian() {
			var sorter;
			var sortBy = $('#sort').val()
			var orderBy = $('#order').val()
			if (sortBy == "name") {
				sorter = sortByName
			} else if (sortBy == "frequency") {
				sorter = sortByFrequency
			} else if (sortBy == "type") {
				sorter = sortByType
			} else if (sortBy == "sentiment") {
				sorter = sortBySentiment
			} else {
				sorter = sortBySignificance
			}
			var str = ""
			$.each(
					dataSet,
					function(index, value) {
						str += "<h3>"
						str += value.title
						str += "</h3><div><table><tr><th>Relevance</th><th>Published</th><th>Title</th><th>Description</th></tr><tr><td>"
						str += value.score.toFixed(2)
						str += "</td><td>"
						str += value.publishedDate
						str += "</td><td><a href='"
						str += value.url
						str += "'>"
						str += value.title
						str += "</a></td><td>"
						str += value.description
						str += "</td></tr></table><div class='nested'><h4>Entities</h4><table><thead><tr><th>Name</th><th>Frequency</th><th>Type</th><th>Sentiment</th><th>Significance</th></tr></thead><tbody>"
						if (orderBy == "asc") {
							$.each(
								value.entities.sort(sorter),
								function(index, entity) {
									str += stringCreator(index, entity)
								})
						} else {
							$.each(
								value.entities.sort(sorter).reverse(),
								function(index, entity) {
									str += stringCreator(index, entity)
								})
						}
						str += "</tbody></table></div></div>"
					});
			$('#accordian').empty().append(str)
			$(".nested").accordion({
					active : false,
					collapsible : true
				});
			$('#accordian').accordion("refresh")
		}

		// Support function to create the HTML string for an entity.
		function stringCreator(index, entity) {
			str = "<tr><td>"
			str += entity.disambiguated_name
			str += "</td><td>"
			str += entity.frequency
			str += "</td><td>"
			str += entity.type
			str += "</td><td>"
			str += entity.sentiment
			str += "</td><td>"
			str += entity.significance.toFixed()
			str += "</td></tr>"
			return str
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
