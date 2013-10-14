<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Home</title>
<style>
.jqplot-yaxis-label {
	margin-right: 40px;
}
</style>
</head>
<body>
	<g:javascript src="jquery-2.0.3.js" />
	<g:javascript src="jquery.jqplot.js" />
	<g:javascript src="jqplot.canvasTextRenderer.js" />
	<g:javascript src="jqplot.canvasAxisLabelRenderer.js" />
	<g:javascript src="jqplot.CanvasAxisTickRenderer.js" />
	<g:javascript src="jqplot.DateAxisRenderer.js" />
	<g:javascript src="jqplot.highlighter.js" />
	<g:javascript src="jqplot.cursor.js" />
	<g:javascript src="jqplot.dateAxisRenderer.js" />
	<div id='content' style='padding: 10px;'>
		<div style="text-align: right">
			
		</div>
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>
		Current pages.
		<ul>
			<li><g:link controller="graphs">Graph</g:link></li>
			<li><g:link controller="infinite">Infinit.e</g:link></li>
		</ul>
	</div>
</body>
</html>
