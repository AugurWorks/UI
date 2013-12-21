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
		<g:javascript src="jquery-2.0.3.js" />
		<g:javascript src="jquery.qtip.min.js" />
		<g:javascript src="datepickers.js" />
		<g:javascript src="ajaxData.js" />
		<g:layoutHead/>
		<r:layoutResources />
		<g:if test="${ Environment.current.getName().equalsIgnoreCase("production") }">
			<script type="text/javascript">
				var _gaq = _gaq || [];
				_gaq.push(['_setAccount', 'UA-38986739-1']);
				_gaq.push(['_trackPageview']);
				(function() {
					var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
					ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
					var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
				})();
			</script>
		</g:if>
	</head>
	<body>
		<div id="augurworksLogo" role="banner"><a href="http://alpha.augurworks.net"><img style="height: 70px; padding: 10px;" src="${resource(dir: 'images', file: 'augurworks_logo.png')}" alt="AugurWorks"/></a></div>
		<div id="menuHeader">
			<ul id="menu">
				<li><g:link controller="home">Home</g:link></li>
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
					<g:link controller="infinite">Data</g:link>
					<ul>
						<li><g:link controller="infinite">Sentiment</g:link></li>
					</ul>
				</li>
				<li><g:link controller="analysis">Analysis</g:link></li>
				<li>
					<g:link controller="home" action="about">About Us</g:link>
					<ul>
						<li><g:link controller="home" action="team">The Team</g:link></li>
					</ul>
				</li>
				<li><a href="http://augurworks.com/?cat=3">Blog</a></li>
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
		<div class="footer" role="contentinfo"></div>
		<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
		<g:javascript library="application"/>
		<r:layoutResources />
	</body>
</html>
