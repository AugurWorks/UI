<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="landing">
<title>AugurWorks</title>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'landing.css')}" type="text/css">
<g:javascript src="jQuery/jquery-2.0.3.js" />
<g:javascript src="d3.v3.js" />
<g:javascript src="landing.js" />
</head>
<body>
	<div id='content'>
		<svg id="coverImage"></svg>
		<div id="coverWrapper">
			<div id="coverText">
				<table style="display: inline;">
					<tr>
						<td>
							<img src="/images/Logo.png" style="height: 200px;" />
						</td>
						<td>
							<p style="font-size: 20px; padding-left: 10px;">
								Stuff
							</p>
						</td>
					</tr>
				</table>
				
			</div>
		</div>
		<div class="wrapper">
			<div class="box">
				<h1>Welcome to</h1>
				<h1 style="font-size: 90px; margin-top: 0;">AugurWorks</h1>
				<p class="subheader">AugurWorks is a site which helps investors visualize, integrate, and analyze financial and economic data.</p>
				<button style="width: 150px; height: 50px; font-size: x-large; margin-bottom: 10px;" onclick="window.location.href = '/home'">Try It</button>
				<button style="width: 150px; height: 50px; font-size: x-large; margin-bottom: 10px;" onclick="window.location.href = '/register/index'">Register</button>
			</div>
		</div>
		<div class="wrapper" style="background-color: #DDDDDD;">
			<div class="box">
				<h1>Why do you care?</h1>
				<p class="subheader">latin</p>
				<table>
					<tr>
						<td>
							<h2>Pre-Integration</h2>
							<p class="text">
								latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin
							</p>
						</td>
						<td>
							<h2>Time Savings</h2>
							<p class="text">
								latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin
							</p>
						</td>
						<td>
							<h2>Built-In Analysis</h2>
							<p class="text">
								latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin
							</p>
						</td>
						<td>
							<h2>Community</h2>
							<p class="text">
								latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin
							</p>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="wrapper">
			<div class="box">
				<h1>How?</h1>
				<p class="subheader">latin</p>
				<table>
					<tr>
						<td>
							<h2>Visualize</h2>
							<p class="text">
								latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin
							</p>
						</td>
						<td>
							<h2>Integrate</h2>
							<p class="text">
								latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin
							</p>
						</td>
						<td>
							<h2>Analyze</h2>
							<p class="text">
								latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin
							</p>
						</td>
						<td>
							<h2>Socialize</h2>
							<p class="text">
								latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin
							</p>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="wrapper" style="background-color: #DDDDDD;">
			<div class="box">
				<h1>What?</h1>
				<p class="subheader">latin</p>
				<table>
					<tr>
						<td>
							<h2>Implementation</h2>
							<p class="text">
								latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin
							</p>
						</td>
						<td>
							<h2>Architecture</h2>
							<p class="text">
								latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin
							</p>
						</td>
						<td>
							<h2>Algorithms</h2>
							<p class="text">
								latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin
							</p>
						</td>
						<td>
							<h2>Expansion</h2>
							<p class="text">
								latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin
							</p>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="wrapper">
			<div class="box">
				<h1>Meet the Team</h1>
				<p class="subheader">The team is made up of people</p>
				<table>
					<tr>
						<td style="width: 33%;">
							<h2>Brian Conn</h2>
							<table>
								<tr>
									<td>
										<img class="team" src="/images/Brian_Gray.jpg" />
									</td>
									<td>
										<p class="text" style="padding-left: 10px; padding-right: 30px;">
											latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin
										</p>
									</td>
								</tr>
							</table>
						</td>
						<td style="width: 33%;">
							<h2>Stephen Freiberg</h2>
							<table>
								<tr>
									<td>
										<img class="team" src="/images/Stephen_Gray.jpg" />
									</td>
									<td>
										<p class="text" style="padding-left: 10px;">
											latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin
										</p>
									</td>
								</tr>
							</table>
						</td>
						<td style="width: 33%;">
							<h2>Drew Showers</h2>
							<table>
								<tr>
									<td>
										<img class="team" src="/images/Drew_Gray.jpg" />
									</td>
									<td>
										<p class="text" style="padding-left: 10px;">
											latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin latin
										</p>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<script>
		$(function() {
			generateImage('coverImage')
		})
	</script>
</body>
</html>
