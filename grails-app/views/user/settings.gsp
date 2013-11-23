<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Settings</title>
</head>
<body>
	<div id='content' style='padding: 10px;'>
		Settings
		<ul>
			<li>Username: ${ service.authentication.name }</li>
			<li>Privileges: ${ service.authentication.authorities }</li>
			<li>Logged In: ${ service.loggedIn }</li>
			<li>Email: ${ user.email }</li>
		</ul>
	</div>
</body>
</html>