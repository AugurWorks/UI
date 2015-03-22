<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Neural Net List</title>
</head>
<body>
    <div id='content' style='padding: 10px;'>
        <h1>Previous Nets</h1>
        <ul style="margin-left: 25px;">
        	<g:each in="${ nets }" var="cur">
        		<g:if test="${ cur.neuralNet.stop }">
        			<li><a href="/analysis/neuralnet/${ cur.id }">${ cur.user.username + ', ' + cur.requestDate.format('yyyy-MM-dd') + ': ' + cur.dataSets.collect { it.name }.join(', ') }</a></li>
        		</g:if>
        		<g:else>
        			<li>${ cur.user.username + ', ' + cur.requestDate.format('yyyy-MM-dd') + ': ' + cur.dataSets.collect { it.name }.join(', ') }</li>
        		</g:else>
        	</g:each>
        </ul>
    </div>
</body>
</html>
