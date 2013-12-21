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
			In late 2012, Brian Conn and Stephen Freiberg, two MIT '13 graduates, joined forces with Drew Showers, an experienced IT professional to solve a difficult problem many have tried to solve before: how to predict the stock market. Thus, AugurWorks was born.
			<br></br>
			Since then we've made progress integrating and analyzing various sources of data to help in predicting the market: historical stock price data and <g:link controller="infinite">sentiment</g:link> generated from news stories and Twitter streams. We've also begun to analyze the data using tailored algorithms such as decision trees, linear regressions, and neural nets.
			<br></br>
			We found that manual data integration is extremely time consuming, especially when our normal working sessions are frequent, short sessions after work. To flatten these issues, we started this website as a side project to facilitate data visualization, integration, and analysis. Here users can get up to date on the market and generate stock predictions. We are developing in Grails, a modern Java based Model-View-Controller language, which allows us to create plugins for new algorithms and data services so we can maintain a flexible, evolving website allowing us to respond quickly to user requests.
		</p>
	</div>
</body>
</html>