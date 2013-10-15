<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>About</title>
<style>
.jqplot-yaxis-label {
	margin-right: 40px;
}
</style>
</head>
<body>
	<div id='content' style='padding: 10px;'>
		<g:if test="${flash.message}">
			<div class="message" role="status">
				${flash.message}
			</div>
		</g:if>
		About us.
	</div>
</body>
</html>