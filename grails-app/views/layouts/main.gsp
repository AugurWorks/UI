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
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'menu.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jqplot.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.qtip.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery-ui.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'chosen.css')}" type="text/css">
		<g:javascript src="jQuery/jquery-2.0.3.js" />
		<g:javascript src="jQuery/jquery.qtip.min.js" />
		<g:javascript src="jQuery/jquery-ui.js" />
		<g:javascript src="jQuery/jquery.blockUI.js" />
		<g:javascript src="datepickers.js" />
		<g:javascript src="ajaxData.js" />
		<g:javascript src="chosen.jquery.js" />
		<g:layoutHead/>
		<r:layoutResources />
		<g:if test="${ Environment.current.getName().equalsIgnoreCase("production") }">
			<script type="text/javascript">
				if (window.location.pathname.toLowerCase().indexOf('alpha') != -1) {
					var _gaq = _gaq || [];
					_gaq.push([ '_setAccount', 'UA-38986739-1' ]);
					_gaq.push([ '_trackPageview' ]);
					(function() {
						var ga = document.createElement('script');
						ga.type = 'text/javascript';
						ga.async = true;
						ga.src = ('https:' == document.location.protocol ? 'https://ssl'
								: 'http://www')
								+ '.google-analytics.com/ga.js';
						var s = document.getElementsByTagName('script')[0];
						s.parentNode.insertBefore(ga, s);
					})();
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
		<div id="augurworksLogo" role="banner"><a href="/home"><img style="height: 70px; padding: 10px;" src="${resource(dir: 'images', file: 'augurworks_logo.png')}" alt="AugurWorks"/></a></div>
		<div id="menuHeader">
			<ul id="menu">
				<li>
					<g:link controller="home">Home</g:link>
					<ul>
						<li><a href="/">Landing</a></li>
					</ul>
				</li>
				<li>
					<g:link controller="graphs">Graphs</g:link>
					<ul>
						<li><g:link controller="graphs">Line Graph</g:link></li>
						<li><g:link controller="graphs" action="correlation">Correlation</g:link></li>
						<li><g:link controller="graphs" action="covariance">Covariance</g:link></li>
						<li><g:link controller="graphs" action="calendar">Calendar</g:link></li>
					</ul>
				</li>
				<li>
					<g:link controller="infinite">Data Aggregation</g:link>
					<ul>
						<li><g:link controller="graphs" action="sentiment">News/Events</g:link></li>
						<li><g:link controller="graphs" action="matrix">Matrix</g:link></li>
						<!-- <li><g:link controller="graphs" action="nodes">Node Graph</g:link></li> -->
					</ul>
				</li>
				<li>
				    <g:link controller="analysis">Analysis</g:link>
				    <ul>
				        <li><g:link controller="analysis" action="decisiontree">Decision Tree</g:link></li>
				        <li><g:link controller="analysis">Linear Reg.</g:link></li>
				    </ul>
				</li>
				<li>
				    <g:link controller="home" action="feed">Community</g:link>
				</li>
				<li>
					<a>More</a>
					<ul>
						<li><g:link controller="home" action="features">Features</g:link></li>
						<li><a href="/tutorial/index#intro">Docs</a></li>
						<li><a href="http://augurworks.com/?cat=3">Blog</a></li>
						<li><g:link controller="home" action="about">About Us</g:link></li>
						<li><g:link controller="tutorial" action="tutorial">Tutorials</g:link></li>
						<li><g:link controller="home" action="team">The Team</g:link></li>
					</ul>
				</li>
				<g:if test="${ service?.loggedIn }">
					<g:if test="${ service.currentUser?.authorities?.any { it.authority == "ROLE_ADMIN" } }">
						<li><g:link controller="home" action="controllers">Controllers</g:link></li>
					</g:if>
					<li style="float: right;">
						<g:link class="rightMenu">${ service.authentication.name }</g:link>
						<ul>
							<!-- <li><g:link controller="user" action="settings">Settings</g:link></li> -->
							<li><g:link controller="logout">[ Logout ]</g:link></li>
						</ul>
					</li>
				</g:if>
				<g:else>
					<li style="float: right;"><g:link controller="login">[ Login ]</g:link></li>
				</g:else>
				<li style="float: right;"><g:link controller="home" action="feedback">Feedback</g:link></li>
			</ul>
		</div>
		<g:layoutBody/>
		<div class="footer" role="contentinfo">
			<div style="display: inline-block; margin: 0 auto;">
				<table style="border: none;">
					<tr>
						<td>AugurWorks, INC</td>
						<td><a href='/home/terms' style=''>Terms & Conditions</a></td>
					</tr>
				</table>
			</div>
		</div>
		<g:javascript library="application"/>
		<r:layoutResources />
	</body>
</html>
