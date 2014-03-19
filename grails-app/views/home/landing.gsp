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
		<div id="coverWrapper">
			<div id="coverText" style="position: relative;">
				<table style="display: inline;">
					<tr>
						<td>
							<img src="/images/Logo.png" style="height: 150px;" />
						</td>
						<td>
							<p style="font-size: 20px; padding-left: 10px; color: #0068A9;">
								Reporting numbers,<br>one day early.
							</p>
						</td>
					</tr>
				</table>
			</div>
			<g:field type="text" name="ticker" value="DJIA" />
		</div>
		<svg id="coverImage"></svg>
		<div class="wrapper">
			<div class="box">
				<h1>Welcome to</h1>
				<h1 style="font-size: 90px; margin-top: 0;">AugurWorks</h1>
				<p class="subheader">We help investors like you visualize, integrate, and analyze financial and economic data. One day early.</p>
				<br>
				<form action="/j_spring_security_check" method="POST" id="loginForm" class="cssform" autocomplete="off">
					<div style="display: none;">
						<input type="text" class="text_" name="j_username" id="username" value="User">
						<input type="password" class="text_" name="j_password" id="password" value="user">
						<input type="checkbox" class="chk" name="_spring_security_remember_me" id="remember_me" checked>
					</div>
					<button type="submit" id="submit" style="width: 150px; height: 50px; font-size: x-large; margin-bottom: 10px;">Try It</button>
					<button style="width: 150px; height: 50px; font-size: x-large; margin-bottom: 10px;" onclick="window.location.href = '/login/index'; return false;">Login</button>
					<button style="width: 150px; height: 50px; font-size: x-large; margin-bottom: 10px; background-color: orange;" onclick="window.location.href = '/register/index'; return false;">Register</button>
				</form>
			</div>
		</div>
		<div class="wrapper" style="background-color: #DDDDDD;">
			<div class="box">
				<h1>What makes us different</h1>
				<p class="subheader"></p>
				<table class="contentTable" id="why" style="margin: 0 auto;">
					<tr>
						<td style="width: 25%">
							<h2>Pre-Integration</h2>
							<p class="text">
								Hundreds of data sources are available and pre-integrated, ready for you to mix and match them. Visualizations provide
								multiple ways to see the similarities and differences between sources, allowing intuition to help formulate which analysis
								should be performed.
							</p>
						</td>
						<td style="width: 25%">
							<h2>Time Savings</h2>
							<p class="text">
								With data sources only a click of a button away, there's no need search the Internet for every source you want, copy them into
								the same Excel file, then do the same thing tomorrow. AuguWorks saves you time by doing all that work beforehand, allowing you 
								to make the most out of the time you find in your busy schedule.
							</p>
						</td>
						<td style="width: 25%">
							<h2>Built-In Analysis</h2>
							<p class="text">
								It's helpful to get some intuition about the data by looking at plots, but here at AugurWorks we know that you really care 
								about the hard results. We provide multiple algorithms for you to analyze any combination of data you'd like to then display the 
								results in custom built visualizations.
							</p>
						</td>
						<td style="width: 25%">
							<h2>Community</h2>
							<p class="text">
								AugurWorks hopes to create a community where investors can interact with and learn from each other. To kick this off we've created
								a feed which displays all queries performed by users in real time. These queries are clickable and will make that same query so you
								can see what others are looking for. We plan on expanding this community aspect and would love to hear your ideas.
							</p>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="wrapper">
			<div class="box">
				<h1>How it works</h1>
				<p class="subheader"></p>
				<table class="contentTable" id="how" style="margin: 0 auto;">
					<tr>
						<td style="width: 25%">
							<h2>Visualize</h2>
							<p class="text">
								AugurWorks provides four different methods for you to visualize numerical data: line graph, correlation plot, covariance matrix,
								and calendar view. Any and all data sources can be mixed and matched with these visualizations, allowing you to see exactly what 
								you want in exactly the way you want to. We plan on bringing more visualizations online too.
							</p>
						</td>
						<td style="width: 25%">
							<h2>Integrate</h2>
							<p class="text">
								Integration is the crux of AugurWorks. By standardizing data structures and movements we've been able to leverage each visualization
								or algorithm for each data source and vice versa. This also means that as soon as we create a new plot for one source, we've created a 
								new plot for all sources. Also, as soon as a new data source is added it's instantly usable by any visualization or algorithm.
							</p>
						</td>
						<td style="width: 25%">
							<h2>Analyze</h2>
							<p class="text">
								Data mining and predictive analytics algorithms provide the backbone of the AugurWorks analytics suite. Curretly we have a limited number 
								of algorithms available, but we'll be working hard to create more so you can slice and dice the data however you'd like.
							</p>
						</td>
						<td style="width: 25%">
							<h2>Socialize</h2>
							<p class="text">
								Currently the least developed component of AugurWorks, the community will help users connect with others and share ideas. As more users use
								AugurWorks we'll create more ways to connect and further develop the most used avenues.
							</p>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="wrapper" style="background-color: #DDDDDD;">
			<div class="box">
				<h1>How we did it</h1>
				<p class="subheader"></p>
				<table class="contentTable" id="what" style="margin: 0 auto;">
					<tr>
						<td style="width: 25%">
							<h2>Implementation</h2>
							<p class="text">
								The implementation is a significant portion of what makes AugurWorks great. By creating standard, yet flexible data structures we've created 
								highly integrated, yet expandable data avenues between the server and client. This uniform traffic is the reason all visualizations work with all
								data and vice versa.
							</p>
						</td>
						<td style="width: 25%">
							<h2>Architecture</h2>
							<p class="text">
								The architecture, created using <a href="http://grails.org/">Grails</a>, uses the <a href="http://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller">
								Model-View-Controller</a> software pattern to manage relationships between data objects, server logic, and user interfaces. Utilizing 
								this modern web architecture we've been able to create a sophisticated, usable website rapidly, with much more to come.
							</p>
						</td>
						<td style="width: 25%">
							<h2>Algorithms</h2>
							<p class="text">
								Our current analysis suite includes a <a href="http://en.wikipedia.org/wiki/Decision_tree_learning">Decision Tree</a> algorithm and 
								<a href="http://en.wikipedia.org/wiki/Linear_regression">Linear Regression</a> modeling. The next algorithm we will be integrating is
								a <a href="http://en.wikipedia.org/wiki/Artificial_neural_network">Neural Net</a> and plan on implementing even more.
								<a href="/home/feedback">Let us know</a> if you have a favorite algorithm you'd like us to integrate.
							</p>
						</td>
						<td style="width: 25%">
							<h2>Expansion</h2>
							<p class="text">
								AugurWorks is more than just a site with a single set of data sources, it's a framework which can be leveraged with external sources and
								visualizations. AugurWorks can help you and your company integrate custom sources into the framework to allow you to utilize the analytical
								power and information in the standard AugurWorks configuration. <a href="/home/feedback">Contact us</a> for more information.
							</p>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="wrapper">
			<div class="box">
				<h1>Meet the Team</h1>
				<table id="team" style="margin: 0 auto;">
					<tr>
						<g:each in="${ members }">
							<td style="width: 33%;">
								<h2>${ it.name } - ${ it.position }</h2>
								<table>
									<tr>
										<td>
											<img class="team" src="/images/${ it.imageName }" />
										</td>
										<td>
											<p class="text" style="padding-left: 10px; padding-right: 30px;">
												${ it.description }
											</p>
										</td>
									</tr>
								</table>
							</td>
						</g:each>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<script>
		var collapsed = false;
		var tables = ["#why", "#how", "#what"];
		
		$(function() {
			reset(5000)
		})
		
		$(window).resize(function() {
			reset(0)
		})
		
		$(document).keypress(function(e) {
		    if (e.which == 13) {
		        reset(5000)
		    }
		});
		
		function reset(time, ticker) {
			$('#coverImage').html('')
			generateImage('coverImage', time, $('#ticker').val());
			if ($(window).width() > 1600 && collapsed) {
				collapsed = false

				for (i in tables) {
					var me = tables[i];
					$(me).children().children('tr:eq(1)').each(function(){
						var html = '<td style="width: 25%">';
						html += $('td:eq(0)', this).html() + '</td><td style="width: 25%">';
						html += $('td:eq(1)', this).html() + '</td>';
						$(me + ' > tbody > tr:eq(0)').append(html)
						$('td:eq(1)', this).remove()
						$('td:eq(0)', this).remove()
						$(this).remove()
					})
					$(me).width('100%')
				}
				$('#team > tbody').prepend('<tr></tr>')
				$('#team > tbody').children('tr').each(function(i, me) {
					$(me).children('td').each(function() {
						$('#team > tbody > tr:eq(0)').append('<td width="33%;">' + $(this).html() + '<td>')
					})
					if (i != 0) {
						$(me).remove()
					}
					$('#team td').each(function() {
						if ($(this).html().length == 0) {
							$(this).remove()
						}
					})
				})
				
				$('#team').width('100%')
				$('.box').width(1600)
				$('.subheader').width(900)
			} else if ($(window).width() <= 1600 && !collapsed) {
				collapsed = true
				for (i in tables) {
					var me = tables[i];
					$(me).children().children('tr').each(function(){
						var html = '<td>';
						html += $('td:eq(2)', this).html() + '</td><td>';
						html += $('td:eq(3)', this).html() + '</td>';
						$('<tr>').insertAfter(this).append(html)
						$('td:eq(3)', this).remove()
						$('td:eq(2)', this).remove()
					})
					$(me).width(800)
				}
				$('#team > tbody').append('<tr></tr><tr></tr>')
				$('#team > tbody > tr:eq(0)').children('td').each(function(i, me) {
					$('#team > tbody > tr:eq(' + i + ')').append('<td>' + $(me).html() + '</td>')
					$(me).remove()
				})
				$('#team').width(500)
				$('.box').width(800)
				$('.subheader').width(600)
			}
		}
	</script>
</body>
</html>
