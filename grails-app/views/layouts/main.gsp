<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<%@ page import="grails.util.Environment" %>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="Grails"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="/assets/favicon.ico" type="image/x-icon">
		<asset:stylesheet href="main.css" />
		<asset:stylesheet href="menu.css" />
		<asset:stylesheet href="mobile.css" />
		<asset:stylesheet href="jquery.jqplot.css" />
		<asset:stylesheet href="jquery.qtip.css" />
		<asset:stylesheet href="jquery-ui.css" />
		<asset:stylesheet href="chosen.css" />
		<asset:javascript src="jQuery/jquery-2.0.3.js" />
		<asset:javascript src="jQuery/jquery.qtip.min.js" />
		<asset:javascript src="jQuery/jquery-ui.js" />
		<asset:javascript src="jQuery/jquery.blockUI.js" />
		<asset:javascript src="datepickers.js" />
		<asset:javascript src="ajaxData.js" />
		<asset:javascript src="chosen.jquery.js" />
		<g:layoutHead/>
		<g:if test="${ Environment.current.getName().equalsIgnoreCase("production") }">
			<script type="text/javascript">
				if (window.location.host.toLowerCase().indexOf('beta') != -1) {
					  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
					  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
					  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
					  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

					  ga('create', 'UA-38986739-3', 'augurworks.net');
					  ga('send', 'pageview');
				} else {
					(function(i, s, o, g, r, a, m) {
						i['GoogleAnalyticsObject'] = r;
						i[r] = i[r] || function() {
							(i[r].q = i[r].q || []).push(arguments)
						}, i[r].l = 1 * new Date();
						a = s.createElement(o), m = s.getElementsByTagName(o)[0];
						a.async = 1;
						a.src = g;
						m.parentNode.insertBefore(a, m)
					})(window, document, 'script',
							'//www.google-analytics.com/analytics.js', 'ga');
		
					ga('create', 'UA-38986739-2', 'augurworks.net');
					ga('send', 'pageview');
				}
			</script>
		</g:if>
	</head>
	<body>
		<div id="augurworksLogo" role="banner"><a href="/home"><img style="height: 70px; padding: 10px;" src="/assets/augurworks_logo.png" alt="AugurWorks"/></a></div>
		<div id="menuHeader">
			<ul id="menu">
				<li>
					<g:link controller="home" action="landing">Home</g:link> 
				<li>
					<g:link controller="analysis" action="neuralnet">Go Predict</g:link>
					<ul>
				        <li><g:link controller="analysis" action="neuralnetlist">Results</g:link></li>
					</ul>
				</li>
				<li>
					<g:link controller="graphs" action="sentiment">News and Events</g:link>
				</li>
				<li>
				    <g:link controller="home" action="feed">Community</g:link>
				</li>
					<li>
						<a href="http://augurworks.com/?cat=3" target="_blank">Blog</a>
					</li>
				
				<li>
					<a>Tools</a>
					<ul>
					    <li><g:link controller="analysis" action="decisiontree">If-Then-Else</g:link></li>
					    <li><g:link controller="analysis" action="linearregression">Modeling</g:link></li>
					    <li><g:link controller="analysis" action="neuralnet">Learning</g:link></li>
					    <!-- <li><g:link controller="analysis" action="neuralnetlist">NN List</g:link></li> -->
						<li><g:link controller="graphs">Line Graph</g:link></li>
						<li><g:link controller="graphs" action="matrix">Matrix</g:link></li>
						<li><g:link controller="graphs" action="correlation">Correlation</g:link></li>
						<li><g:link controller="graphs" action="covariance">Covariance</g:link></li>
						<li><g:link controller="graphs" action="calendar">Calendar</g:link></li>
					</ul>
				</li>
				<li>
					<a>More</a> 
					<ul>
						<li><a href="/tutorial/index#intro">Docs</a></li>
						<li><g:link controller="tutorial" action="tutorial">Tutorials</g:link></li>
					</ul>
				</li>
				<sec:ifLoggedIn>
					<sec:ifAnyGranted roles="ROLE_ADMIN">
						<li>
							<a>Admin</a>
							<ul>
								<li><g:link controller="home" action="controllers">Controllers</g:link></li>
								<li><g:link controller="search">Search</g:link></li>
								<li><g:link controller="search" action="check">Results</g:link></li>
							</ul>
						</li>
					</sec:ifAnyGranted>
					<li style="float: right;"><a href="/j_spring_security_logout">Logout</a></li>
					<li style="float: right;"><a href="#"><sec:username /></a></li>
				</sec:ifLoggedIn>
				<sec:ifNotLoggedIn>
					<li style="float: right;"><g:link controller="login">Login</g:link></li>
				</sec:ifNotLoggedIn>
				<li style="float: right;"><g:link controller="home" action="feedback">Feedback</g:link></li>
			</ul>
		</div>
		<g:layoutBody/>
		<div class="footer" role="contentinfo">
			<div style="display: inline-block; margin: 0 auto;">
				<table style="border: none;">
					<tr>
						<td>AugurWorks LLC</td>
						<td><a href='/home/terms' style=''>Terms & Conditions</a></td>
					</tr>
				</table>
			</div>
		</div>
		<g:javascript library="application"/>
	</body>
</html>
