<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>About</title>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'about.css')}" type="text/css">
</head>
<body>
	<div id='about'>
		<h1>About AugurWorks</h1>
		<p>
			AugurWorks was founded in December 2012 by Drew Showers, Brian Conn, and Stephen Freiberg. We’re currently working on using algorithms and open source data to provide financial advice to everyday users.
		</p>
		<h1>Meet the Team</h1>
		<table id='main'>
			<g:each in="${ members }" var="member">
				<tr>
					<td style="width: 140px;">
						<img alt="${ member.name }" src="${resource(dir: 'images', file: member.imageName)}" style="width: 100%;">
					</td>
					<td>
						<p style="font-size: 22px;">
							<b>${ member.name }</b>
						</p>
						<p style="font-size: 18px;">
							${ member.position }
						</p>
						<p>
							${ member.subPosition }
						</p>
						<a href="mailto:${ member.emailAddress }">
							${ member.emailAddress }
						</a>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<p>
							${ member.description }
						</p>
					</td>
				</tr>
			</g:each>
		</table>
	</div>
</body>
</html>