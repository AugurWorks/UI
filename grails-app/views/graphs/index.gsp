<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Document Search</title>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
</head>
<body>
	<g:javascript src="jquery-2.0.3.js" />
	<g:javascript src="jquery.jqplot.js" />
	<g:javascript src="jqplot.canvasTextRenderer.js" />
	<g:javascript src="jqplot.canvasAxisLabelRenderer.js" />
	<g:javascript src="jqplot.CanvasAxisTickRenderer.js" />
	<g:javascript src="jqplot.DateAxisRenderer.js" />
	<g:javascript src="jquery-ui.js" />
	<div style="text-align: right">
		Username:
		${ service.authentication.name }
	</div>
	<g:if test="${flash.message}">
		<div class="message" role="status">
			${flash.message}
		</div>
	</g:if>
	<g:form id='form' onsubmit="onSubmit();">
			 Keyword: <g:textField type="text" id="keyword" name="keyword" value="${keyword}" />
			 Start date: <g:textField type="text" id="startDate" name="startDate" value="${startDate}" />
			 End date: <g:textField type="text" id="endDate" name="endDate" value="${endDate}" />
		<input type="submit" value="Go!">
	</g:form>
	<div class="accordion">
		<g:each in="${reply.data}" var="item">
			<h3>
				${ item.title }
			</h3>
			<div>
				<table>
					<tr>
						<th>Relevance</th>
						<th>Published</th>
						<th>Title</th>
						<th>Description</th>
					</tr>
					<tr>
						<td><g:formatNumber number="${ item.score }" type="number" maxFractionDigits="2" /></td>
						<td><g:formatDate format="MM/dd/yyyy" date="${ new Date(item.publishedDate) }" /></td>
						<td><g:link base="${ item.url }" target="_blank">
								${ item.title }
							</g:link></td>
						<td>
							${ item.description }
						</td>
					</tr>
				</table>
				<div class="nested">
					<h4>Entities</h4>
					<table>
						<thead>
							<tr>
								<g:sortableColumn defaultOrder="desc" property="disambiguated_name" title="Name" />
								<g:sortableColumn defaultOrder="desc" property="frequency" title="Frequency" />
								<g:sortableColumn defaultOrder="desc" property="type" title="Type" />
								<g:sortableColumn defaultOrder="desc" property="sentiment" title="Sentiment" />
								<g:sortableColumn defaultOrder="desc" property="significance" title="Significance" />
							</tr>
						</thead>
						<tbody>
							<g:each in="${item.entities.sort {x,y->
							if(params.sort){
								if(params.order=='asc'){
									x.(params.sort) <=> y.(params.sort)
								} else {
									y.(params.sort) <=> x.(params.sort)
								}
							} else {
								y.significance <=> x.significance
							}}}" status="i" var="entity">
								<tr>
									<td>
										${ entity.disambiguated_name }
									</td>
									<td>
										${ entity.frequency }
									</td>
									<td>
										${ entity.type }
									</td>
									<td><g:formatNumber number="${ entity.sentiment }" type="number" maxFractionDigits="2" /></td>
									<td><g:formatNumber number="${ entity.significance }" type="number" maxFractionDigits="2" /></td>
								</tr>
							</g:each>
						</tbody>
					</table>
				</div>
			</div>
		</g:each>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$(function() {
				$(".accordion").accordion({
					event : "click",
					active : false,
					collapsible : true,
					heightStyle : "content"
				});
				$(".nested").accordion({
					active : false,
					collapsible : true
				});
			});
		});
		var onSubmit = function() {
			var keyword = $('#keyword').value()
			var startDate = $('#startDate').value()
			var endDate = $('#endDate').value()
			console.log("/index?keyword=" + keyword + "&startDate=" + startDate
					+ "&endDate=" + endDate)
			window.location.href = "/index?keyword=" + keyword + "&startDate="
					+ startDate + "&endDate=" + endDate
		}
	</script>
</body>
</html>
