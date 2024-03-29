<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>News & Events</title>
<style>
	.arrow-up {
		width: 0; 
		height: 0; 
		border-left: 12px solid transparent;
		border-right: 12px solid transparent;
		
		border-bottom: 20px solid green;
	}
	
	.arrow-down {
		width: 0; 
		height: 0; 
		border-left: 12px solid transparent;
		border-right: 12px solid transparent;
		
		border-top: 20px solid red;
	}
</style>
</head>
<body>
	<asset:javascript src="datepickers.js" />
	<asset:javascript src="sorting.js" />
	<div id='content' style="padding: 10px;">
		<g:render template="../layouts/menu" />
		<div id="accordian" class="accordion" style="margin-top: 20px;"></div>
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
			validate()
			qtip();
		});

		// Function runs after AJAX call is completed. Resets accordian.
		function ajaxComplete(ajaxData) {
			if (ajaxData[0]) {
				fullData = ajaxData[0];
				dataSet = ajaxData[0].data;
				setAccordian();
			}
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
			var str = "";
			try {
				$.each(
						dataSet,
						function(index, value) {
							str += "<h3>"
							str += value[fullData.metadata.title];
							if (parseFloat(value[fullData.metadata.icon]) > 0) {
								str += '<div style="display: inline; float: right;">Sentiment</div><div style="display: inline; float: right;"><div class="arrow-up"></div></div>';
							} else if (parseFloat(value[fullData.metadata.icon]) < 0) {
								str += '<div style="display: inline; float: right;">Sentiment</div><div style="display: inline; float: right;"><div class="arrow-down"></div></div>';
							}
							str += '</h3><div>';
							for (i in fullData.metadata.data.sort()) {
								str += "<h4><u>" + fullData.metadata.data[i].title + "</u></h4>";
								str += '<p>';
								if (fullData.metadata.data[i].url) {
									str += "<a href='" + value[fullData.metadata.data[i].url] + "' target=\"_blank\">" + value[fullData.metadata.data[i].id] + "</a>";
								} else {
									str += value[fullData.metadata.data[i].id]
								}
								str += '</p>';
							}
							
							if (fullData.metadata.sub && value[fullData.metadata.sub.id] != undefined && value[fullData.metadata.sub.id].length > 0) {
								str += "<div class='nested'><h4>" + fullData.metadata.sub.title + "</h4><table><tr>";
								for (j in fullData.metadata.sub.data) {
									str += "<th>";
									str += fullData.metadata.sub.data[j].title
									str += "</th>";
								}
								str += "</tr>";
								for (j in value[fullData.metadata.sub.id]) {
									str += "<tr>";
									for (k in fullData.metadata.sub.data) {
										str += "<td>";
										str += value[fullData.metadata.sub.id][j][fullData.metadata.sub.data[k].id];
										str += "</td>";
									}
									str += "</tr>";
								}
							}
							str += "</table></div></div>"
						});
				$('#accordian').empty().append(str)
				$(".nested").accordion({
						active : false,
						collapsible : true
					});
				$('#accordian').accordion("refresh")
			} catch (e) {
				console.log(e)
				$('#accordian').html('Sorry, no data was returned for that query. If you think this is an error please contact <a href="mailto:feedback@augurworks.com">feedback@augurworks.com</a>.')
			}
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
