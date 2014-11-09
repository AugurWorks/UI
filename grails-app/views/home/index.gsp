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
		<!-- <table>
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
				<h1 style="text-align: center;"><g:link controller="graphs" action="sentiment">Sentiment</g:link></h1>
				<g:link controller="graphs" action="sentiment"><img src="${resource(dir: 'images', file: 'Infinite.png')}"  class="icons"></g:link>
			</td>
			<td style="width:25%;">
				<h1 style="text-align: center;"><g:link controller="analysis" action="decisiontree">Decision Tree</g:link></h1>
				<g:link controller="analysis" action="decisiontree"><img src="${resource(dir: 'images', file: 'DecisionTree.png')}"  class="icons"></g:link>
			</td>
		</tr>
		<tr>
			<td>
				<p style="text-align: center; font-size:.8em">
					The graph provides a visual representation of a data set and creates an easy to use and intuitive interface for data. It also provides a visual comparison between different sets which can then be compared more thoroughly on the correlation or covariance pages.
				</p>
			</td>
			<td>
				<p style="text-align: center; font-size:.8em">
					The correlation plot provides a scatter plot of two data sets. Also plotted is a linear regression line which fits the data sets. Hovering over a data point reveals additional information such as the specific dates and values of that point. Hovering over the fit line reveals the x-axis set value and the predicted y-axis value based on the linear regression.
				</p>
			</td>
			<td>
				<p style="text-align: center; font-size:.8em">
					The Sentiment page shows news story and Tweets which match the input keyword. The stories are ranked in descending order of relevance and contain information about publish date, title, description, and a link to the story. The entitles display provides information about the top entities within the story and information about them.
				</p>
			</td>
			<td>
				<p style="text-align: center; font-size:.8em">
					The Decision Tree algorithm analyzes inputs to determine a tree of true-false decisions which best describes the relationship between the inputs and a given output. Use this to perform further analysis on data sets after visualizing inputs on other pages.
				</p>
			</td>
		</tr>
		</table> -->
		<ul class="table-list thirds">
			<li>
				<h1 class="large">Step 1</h1>
				<h2>Explore the data</h2>
				<p>
					Use the <g:link controller="graphs">Line Chart</g:link> to plot data sources on a line graph or the <g:link controller="graphs" action="calendar">Calendar</g:link>
					to see a heatmap of the data across time.
				</p>
			</li>
			<li>
				<h1 class="large">Step 2</h1>
				<h2>Find correlations</h2>
				<p>
					Use the <g:link controller="graphs" action="matrix">Adjacency Matrix</g:link> to see how many news entities appear in the same article,
					the <g:link controller="graphs" action="correlation">Correlation Plot</g:link> to discover the correlation between two data sources,
					or the <g:link controller="graphs" action="covariance">Covariance Matrix</g:link> to find the correlation between multiple data sources over a period of time.
				</p>
			</li>
			<li>
				<h1 class="large">Step 3</h1>
				<h2>Make predictions</h2>
				<p>
					Once you have discovered interesting relationships between data sources use the <g:link controller="analysis" action="neuralnet">Neural Net</g:link> to
					make predictions. Train the Neural Net against past data and see what it predicts tomorrows values to be.
				</p>
			</li>
		</ul>
		<h1 class="large">Tutorials</h1>
		<p>
			If you're lost about what to do or where to start check out our <a href="/tutorial/index#intro">Docs</a> or some of our tutorials below.
		</p>
		<ul class="table-list thirds">
			<li>
				<h1 class="large">Tutorial</h1>
				<p>
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vel dui non sem ultricies accumsan eget quis dui. Interdum et malesuada fames ac ante
					ipsum primis in faucibus. Ut at lectus nisl. Quisque hendrerit a nisi eu scelerisque. Vestibulum quis accumsan leo. Aenean id nibh vitae nisi porta
					malesuada. Curabitur in enim eget tortor sollicitudin volutpat id in ex. Sed tincidunt mi vel tincidunt ornare. Etiam ut est erat. Duis quis accumsan
					enim, at ullamcorper tortor.
				</p>
			</li>
			<li>
				<h1 class="large">Tutorial</h1>
				<p>
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vel dui non sem ultricies accumsan eget quis dui. Interdum et malesuada fames ac ante
					ipsum primis in faucibus. Ut at lectus nisl. Quisque hendrerit a nisi eu scelerisque. Vestibulum quis accumsan leo. Aenean id nibh vitae nisi porta
					malesuada. Curabitur in enim eget tortor sollicitudin volutpat id in ex. Sed tincidunt mi vel tincidunt ornare. Etiam ut est erat. Duis quis accumsan
					enim, at ullamcorper tortor.
				</p>
			</li>
			<li>
				<h1 class="large">Tutorial</h1>
				<p>
					Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vel dui non sem ultricies accumsan eget quis dui. Interdum et malesuada fames ac ante
					ipsum primis in faucibus. Ut at lectus nisl. Quisque hendrerit a nisi eu scelerisque. Vestibulum quis accumsan leo. Aenean id nibh vitae nisi porta
					malesuada. Curabitur in enim eget tortor sollicitudin volutpat id in ex. Sed tincidunt mi vel tincidunt ornare. Etiam ut est erat. Duis quis accumsan
					enim, at ullamcorper tortor.
				</p>
			</li>
		</ul>
	</div>
</body>
</html>
