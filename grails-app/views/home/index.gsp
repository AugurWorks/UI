<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Home</title>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'home.css')}" type="text/css">

</head>
<body>
	<div id='content' style='padding: 10px;'>
		<!--  Drew 14Dec14 - Adding Beta, here since this is the prodcut page
		<h1 style="text-align: center; font-size: 2em; padding: 20px"><b>Welcome to AugurWorks!</b></h1>
		
		-->
		<h1 style="text-align: center; font-size: 2em; margin-top: 0; padding: 20px"><b>AugurWorks</b><sup style="font-size: .4em;">BETA</sup></h1>
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
		<!--  Drew
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
		
		End Drew -->
		<ul class="table-list halves ">
		
			<li>
				<h2>Welcome!</h2>
				<p>
				You are an early adopter of a very unique investment community! Before getting started, we’d like to give you an idea of what to expect and how to use the services. 
				If you accessed this site using “Try It”, you are automatically logged in as a “Trial user”. There is only one (1) Trial user account, so you will be sharing this 
				account with others. You will see their activities and others will see your activities. We highly recommend creating your own account. 
				This will also help us with feedback.
				</p>
				<h2>What it is</h2>
				<p>
				First, this is a Beta version. In this version the core prediction engine functionality is complete, but the user experience is not yet fully automated. 
				The prediction results to date have been quite favorable. Using the only <g:link controller="analysis" action="linearregression">Modeling</g:link> 
				algorithm, we have experienced approximately 67% success rate in predictions. Using only the <g:link controller="analysis" action="neuralnet">Learning</g:link> 
				algorithm, we have experienced a prediction success rate over 80%, and as high as 95% on short 30 day runs. We encourge you to use all three (3) algorithms 
				including the <g:link controller="analysis" action="decisiontree">If-Then-Else</g:link> to get familiar with the predictions processes and results of each. 
				</p>
				<h2>What it isn’t</h2>
				<p>
				A Silver Bullet! With the Beta version, there is still some work to do on your end that hasn't been automated yet. 
				Therefore, the user must determine what stock predictor inputs to use, as well as determining what algorithm strategies to use. For example, market volatility has an 
				impact on the algorithm date range used. In general, when the market is more volatility, use a shorter date range. Any of the many Volitilty Indexes can be used for guidance. 
				Although we are planning to automate these manual Beta requirements, we’re just not there yet. 
				</p>	
				<p>
				To the right are the key process steps to provide an overview of how to use the services. Tutorials and other <a href="/tutorial/index#intro">documentation</a> 
				are provided for details. <b>Now, get started!</b> 
				</p>
			</li>
			<li>
				<h2>Step 1 - Explore the data</h2>
				<p>
					Use the <g:link controller="graphs">Line Graph</g:link> to plot data source trends or the <g:link controller="graphs" action="calendar">Calendar</g:link>
					to see a heatmap of the data across time. The Line Graph is also quite valuable to explore and validate data sources. There are many data sources provided 
					and it is always a good idea to check them from time to time using the Line Graph. It is also worth noting the data services get updated at approximately 10pm 
					Eastern Time daily. Therefore, the predictions must be made between 10pm and when the markets open. 
				</p>
			
				<h2>Step 2 - Find correlations</h2>
				<p>
					This is one of the key steps in the process in order to get solid results, and arguably the hardest to do. In a future release, we will provide 
					this information as part of the AugurWorks service. But for now, we have provided additional tools to help. 
					Use the <g:link controller="graphs" action="matrix">Adjacency Matrix</g:link> to see how many news entities appear in the same article,
					the <g:link controller="graphs" action="correlation">Correlation Plot</g:link> to discover the correlation between two data sources,
					or the <g:link controller="graphs" action="covariance">Covariance Matrix</g:link> to find the correlation between multiple data sources over a period of time.
					Additionally, the <g:link controller="analysis" action="linearregression">Modeling</g:link> algorithm provides a very good indicator of input quality using 
					the R-Squared value above the graph result. The default stocks provided generally have an R-Squared over 90%, which indicates very highly correlated inputs. 
					However, anything over 70% is good, but you should always strive in increase this value by using different or additional inputs. 
				</p>
			
				<h2>Step 3 - Make predictions</h2>
				<p>
					Once you have discovered interesting relationships between data sources, use the <g:link controller="analysis" action="neuralnet">Go Predict</g:link> menu item to
					make predictions. Highly correlated energy stocks are pre-entered as default values to help you get started. Predictions can also be performed using other methods available 
					under the Tools menu by selecting the <g:link controller="analysis" action="linearregression">Modeling</g:link> and 
					<g:link controller="analysis" action="decisiontree">If-Then-Else</g:link> algorithms.
				</p>
			</li>
		</ul>
		
	</div>
</body>
</html>
