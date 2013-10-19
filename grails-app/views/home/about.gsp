<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>About</title>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'about.css')}" type="text/css">
</head>
<body>
	<div id='about'>
		<table id='main'>
			<g:each in="${ members }">
				<tr>
					<td>
						<p style="font-size: 20px;">
							<b>${ it.name }</b>
						</p>
					</td>
				</tr>
			</g:each>
		</table>
		<p>
			Augurworks was founded in December 2012 by Drew Showers, Brian Conn, and Stephen Freiberg. We’re currently working on using algorithms and open source data to provide financial advice to everyday users.
		</p>
	</div>
</body>
</html>