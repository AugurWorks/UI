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
			<div class="message">
				${flash.message}
			</div>
		</g:if>
		<div class="buttons">
			<div class="button-line">
				Select 1: <g:select name="input1" from="${ dataTypes }" optionKey="name" />
				Keyword: <g:textField type="text" name="input2" value="Oil" />
			</div>
			<div class="button-line">
				Start date: <g:textField type="text" id="startDate" name="startDate" value="${startDate}" />
				End date: <g:textField type="text" id="endDate" name="endDate" value="${endDate}" />
			</div>
			<div class="button-line">
				Sort By: <g:select id="sort" name='sort' from='[[id:"name", name:"Name"], [id:"frequency", name:"Frequency"], [id:"type", name:"Type"], [id:"sentiment", name:"Sentiment"], [id:"significance", name:"Significance"]]' optionKey="id" optionValue="name"></g:select>
				Order: <g:select id="order" name='order' from='[[id:"asc", name:"Ascending"], [id:"desc", name:"Descending"]]' optionKey="id" optionValue="name"></g:select>
			</div>
		</div>
		<div class="button-line">
			<button class="buttons" style="font-size: large;" onclick="validate()">Submit</button>
		</div>
		<div id="accordian" class="accordion" style="margin-top: 20px;"></div>
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
			validate()
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
	</script>
</body>
</html>
