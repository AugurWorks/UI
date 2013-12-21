<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Home</title>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'home.css')}" type="text/css">

</head>
<body>
	<div id='content' style='padding: 10px;'>
		<h1 style="text-align: center; font-size: 2em; padding: 20px"><b>Welcome to AugurWorks!</b></h1>
		<table>
		<tr>
			<td style="width:25%;">
				<h1 style="text-align: center;"><g:link controller="graphs">Line Graph</g:link></h1>
				<g:link controller="graphs"><img src="${resource(dir: 'images', file: 'Graph2.png')}"  class="icons"></g:link>
			</td>
			<td style="width:25%;">
				<h1 style="text-align: center;"><g:link controller="graphs" action="correlation">Correlation Plot</g:link></h1>
				<g:link controller="graphs" action="correlation"><img src="${resource(dir: 'images', file: 'CorrelationPlot.png')}"  class="icons"></g:link>
			</td>
			<td style="width:25%;">
				<h1 style="text-align: center;"><g:link controller="graphs" action="covariance">Covariance Matrix</g:link></h1>
				<g:link controller="graphs" action="covariance"><img src="${resource(dir: 'images', file: 'Matrix.png')}"  class="icons"></g:link>
			</td>
			<td style="width:25%;">
				<h1 style="text-align: center;"><g:link controller="infinite">Sentiment</g:link></h1>
				<g:link controller="infinite"><img src="${resource(dir: 'images', file: 'Infinite.png')}"  class="icons"></g:link>
			</td>
		</tr>
		<tr>
			<td>
			<p style="text-align: center; font-size:.8em">
				The graph provides a visual representation of a data set and creates an easy to use and intuitive interface for data. It also provides a visual comparison between different sets which can then be compared more thoroughly on the correlation or covariance pages.</p>
			</td>
			<td>
			<p style="text-align: center; font-size:.8em">
				The correlation plot provides a scatter plot of two data sets. Also plotted is a linear regression line which fits the data sets. Hovering over a data point reveals additional information such as the specific dates and values of that point. Hovering over the fit line reveals the x-axis set value and the predicted y-axis value based on the linear regression.</p>
			</td>
			<td>
			<p style="text-align: center; font-size:.8em">
				The covariance table shows either the covariance or correlation (there is a toggle button) between each combination of data sets. Darker blue means more positive correlation and darker red means more negative correlation. Lighter text means less correlation.</p>
			</td>
			<td>
			<p style="text-align: center; font-size:.8em">
				The Sentiment page shows news story and Tweets which match the input keyword. The stories are ranked in descending order of relevance and contain information about publish date, title, description, and a link to the story. The entitles accordian provides information about the top entities within the story and information about them.
			</td>
		</tr>
		</table>
	</div>
</body>
</html>
