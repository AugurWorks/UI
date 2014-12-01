<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="landing">
	<title>AugurWorks</title>
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'landing.css')}" type="text/css">
	<link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.qtip.css')}" type="text/css">
	<g:javascript src="jQuery/jquery-2.0.3.js" />
	<g:javascript src="d3.v3.js" />
	<g:javascript src="landing.js" />
	<g:javascript src="jQuery/jquery.qtip.min.js" />
</head>
<body>
	<div id='content'>
		<div id="coverWrapper">
			<div id="coverText" style="position: relative;">
				<table style="display: inline;">
					<tr>
						<td>
							<img id="logo" src="/images/Logo.png" style="height: 150px;" />
							<div id="logoText" style="display: none;">
								<div style="padding-bottom: 10px; font-size: 1.1em;">/ˈôgərwərks/</div>
								<p style="padding-bottom: 5px;"><i>noun</i></p>
								<ol style="padding-bottom: 5px;">
									<li style="padding-bottom: 5px;">
										a company that observes natural signs, especially the behavior of markets, interpreting these as an indication of societal approval or disapproval of a proposed action.
									</li>
								</ol>
								<p><i>"AugurWork's symbol represents the head of a <a href="http://en.wikipedia.org/wiki/Lituus" target="_blank">lituus</a>, an augural staff."</i></p>
							</div>
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
				<h1 style="font-size: 90px; margin-top: 0;">AugurWorks<sup style="font-size: .25em;">BETA</sup></h1>
				<p class="subheader">We help investors like you visualize, integrate, and analyze financial and economic data. One day early.</p>
				<br>
				<form action="/j_spring_security_check" method="POST" id="loginForm" class="cssform" autocomplete="off">
					<div style="display: none;">
						<input type="text" class="text_" name="j_username" id="username" value="Trial">
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
							<h2>Simplicity</h2>
							<p class="text">
								We know that not everyone has the time or expertise to be a professional Day Trader, so our goal is simple – to put big analytic 
								power into the hands of everyone by making our service simple and intuitive. AugurWorks saves you time by doing all the hard work, 
								allowing you to make the most out of the time you find in your busy schedule.
							</p>
						</td>
						<td style="width: 25%">
							<h2>Data</h2>
							<p class="text">
								Hundreds of data sources are available and pre-integrated, ready to go to work. However, the real power comes from the integration 
								of intelligence gathered from social media and news feeds with more traditional historical data and statics. We combine 
								mathematical analysis with emotional intelligence, such a public sentiment regarding a current event.
							</p>
						</td>
						<td style="width: 25%">
							<h2>Built-In Analysis</h2>
							<p class="text">
								It's helpful to get some intuition about the data by looking at plots and graphs, but here at AugurWorks we know that you really 
								care about the hard results. We provide multiple predictive algorithms for you to analyze any combination of data you'd like to 
								then display the results in custom built visualizations. We don’t pick market winners and losers. Our algorithms look ahead to 
								see what direction a stock will go before it changes. Overall stock trends are largely unimportant to the AugurWorks analytic engines.
							</p>
						</td>
						<td style="width: 25%">
							<h2>Community</h2>
							<p class="text">
								AugurWorks is currently developing a community service as part of the platform where investors can directly collaborate, share ideas, 
								and learn from each other. To kick this off we've created a feed which displays all queries performed by users in real time. These 
								queries are clickable and will make that same query so you can see what others are looking for, as well as the results. We plan on 
								expanding this community aspect over the next several weeks and would love to hear your ideas.
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
								AugurWorks initially provides four different methods for you to visualize numerical data: line graph, correlation plot, covariance matrix,
								and calendar view. Any and all data sources can be mixed and matched with these visualizations, allowing you to see exactly what 
								you want in exactly the way you want to. We plan to release more visualizations over the next several months.
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
								
								<!-- Data mining and predictive analytics algorithms provide the backbone of the AugurWorks analytics engine. Currently we have a limited number 
								of algorithms available, but we are working hard to develop and integrate additional capabilities to provide the “answer”, making your 
								make-buy-sell decision easy. -->
							</p>
						</td>
						<td style="width: 25%">
							<h2>Socialize</h2>
							<p class="text">
								Currently the least developed component of AugurWorks, the community will help users connect with others, share ideas, and share results. As more 
								users begin using the AugurWorks service, we can create more ways to connect and further develop the most used avenues.
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
								One of the key objectives of the architecture is to ingest, analyze, and visualize virtually any data source available, regardless if it is blogs, 
								news, historical stock data, or government statistics. With this in mind, we are able to optimize the algorithm input data providing meaningful, 
								accurate answers.
							</p>
						</td>
						<td style="width: 25%">
							<h2>Algorithms</h2>
							<p class="text">
								Our Beta analysis suite includes 3 main algorithms of which the predictions are based. 1) An If-Then_Else algorithm based on a 
								<a href="http://en.wikipedia.org/wiki/Decision_tree_learning">Decision Tree</a>, 2) Best-Guess algorithm based on 
								<a href="http://en.wikipedia.org/wiki/Linear_regression">Linear Regression</a> modeling, which provides fast feedback on the quality of the inputs, and 3) 
								a Machine Learning algorithm based on <a href="http://en.wikipedia.org/wiki/Artificial_neural_network">Neural Networks</a>, which apply artificial intelligence techniques. 
								<a href="/home/feedback">Let us know</a> if you have a favorite algorithm you'd like us to integrate.
							</p>
						</td>
						<td style="width: 25%">
							<h2>Expansion</h2>
							<p class="text">
								AugurWorks is more than just a site with a single set of data sources, it's a framework which can be leveraged for other domains. 
								We can help you, or your company integrate custom sources into the framework to allow you to utilize the analytical
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
			$('#logo').qtip({
				content: {
					text: $('#logoText'),
					title: 'au·gur·works'
				},
				position: {
					my: 'top left',
					at: 'bottom left'
				},
				show: {
					event: 'click'
				},
				hide: {
					event: 'click mouseleave',
					fixed: true
				},
				style: {
					classes: 'qtip-landing qtip-rounded'
				}
			})
		})
		
		$(window).resize(function() {
			reset(0)
		})
		
		$(document).keypress(function(e) {
		    if (e.which == 13) {
		        reset(5000)
		    }
		});
	</script>
</body>
</html>
