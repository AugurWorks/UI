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
	<body class="landing">
		<g:layoutBody/>
		<div class="footer" role="contentinfo"></div>
		<g:javascript library="application"/>
	</body>
</html>
