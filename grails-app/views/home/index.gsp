<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Home</title>
</head>
<body>
	<div id='content' style='padding: 10px;'>
		<h1>Welcome to AugurWorks!</h1>
		<ul>
			<li><g:link controller="graphs">Graph</g:link></li>
			<li><g:link controller="graphs" action="correlation">Correlation</g:link></li>
			<li><g:link controller="infinite">Infinit.e</g:link></li>
			<g:if test="${ service?.currentUser?.authorities?.any { it.authority == "ROLE_ADMIN" } }">
				<li><g:link controller="home" action="controllers">Controllers</g:link></li>
			</g:if>
		</ul>
	</div>
</body>
</html>
