<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<title>Documentation</title>
	<g:javascript src="jquery.doccollapse.js" />
	<style>
		h2 {
			color: black;
		}
	</style>
</head>
<body>
	<div id='content' style='padding: 20px;'>
		<h1>Documentation</h1>
		
		<ul id="AWcollapse">
			<li>
				<h2 id="intro">Introduction</h2>
				<p>
					Welcome to the AugurWorks documentation! You must be really bored. This documentation has a little more information on the pages and features of the AugurWorks 
					site. Please send us <g:link action="feedback" controller="home">feedback</g:link> on anything you think is missing.
				</p>
			</li>
			<li>
				<h2>Graphs</h2>
				<p>This section describes visualization pages and their features.</p>
				<ul>
					<li>
						<h3>Pages</h3>
						<p>This section provides information about what each graph page displays as well as a few case studies where the graph can be used.</p>
						<ul>
							<li>
								<h4 id="lineGraph">Line Graph</h4>
								<p>
									The line graph is the plot we all know and love. Dates on the x-axis, values on the y-axis. This is a good plot to use when exploring 
									new data sets and want to get a feel for what order of magnitude the data is, how volatile it is, and what its general trends are.
								</p>
								<br>
								<p>
									Benefits of the line graph include plotting multiple data sets side by side, plotting multiple data sets with independent date ranges, 
									zooming in on specific regions, hovering over data values to get more information, and easily visualizing the effects of 
									<a href="#customFunction">custom functions</a> you are experimenting with.
								</p>
								<br>
								<p>
									The rest is fairly self explanatory. For additional information on the specific features used on the line graph page (such as 
									<a href="#inputType">Input Types</a> and <a href="#inputValues">Input Values</a>) please consult the <a href="#features">Features</a> 
									section.
								</p>
							</li>
							<li>
								<h4 id="correlation">Correlation</h4>
								<p>
									The correlation graph plots each data point of two data sets against each other and computes the best fit (least squared error) line 
									between the two data sets. It also calculates the correlation between both sets.
								</p>
								<p>
									Hovering over each plotted point provides information about the point including which date each data set's value belongs too, the point's 
									value, and the percentage difference between the point and the fit line.
								</p>
								<h4>Use Cases</h4>
								<p>A few use cases for the correlation plot are as follows.</p>
								<ul>
									<li>
										<b>Calculating the Correlation</b> - The obvious use case is when the correlation between two sets needs to be calculated. The 
										added benefit of the correlation plot (because the <a href="#covariance">Covariance</a> plot also calculates the correlation) 
										is that a visual representation of the correlation is provided. The points of two highly correlated data sets will be clustered 
										near the fit line whereas the points of two highly uncorrelated data sets will be scattered away from the fit line.
									</li>
								</ul>
							</li>
							<li>
								<h4 id="covariance">Covariance</h4>
								<p>
									The covariance matrix is an aggregated version of the correlation plot. Instead of calculating the correlation between two data sets 
									the correlation between all combinations of multiple data sets is shown. This provides a quick view of many correlation combinations.
									Interesting pairing can then be investigated through the correlation page.
								</p>
								<h4>Use Cases</h4>
								<p>A few use cases for the correlation plot are as follows.</p>
								<ul>
									<li>
										<b>Portfolio Diversification</b> - By inputting each stock in your current portfolio you can a cross-section of your market exposure.
										A portfolio of highly correlated stocks means there is little diversification and market movements in one direction will most likely 
										affect all of your stocks in the same way. Adding additional stocks to the covariance page help you find stocks which are uncorrelated 
										with your current stocks to help diversify your portfolio.
									</li>
									<li>
										<b>Time Offset Correlation</b> - Creating a set of inputs where stock price changes are time offset can help identify indicator stocks.
										An indicator stock is one who's change is highly correlated with another stock's change in the future. For example, if a stocks change 
										today was highly correlated with another stock's change tomorrow, today's change in the first stock could be used to predict the other's,
										creating an effective trading strategy.
									</li>
								</ul>
							</li>
							<li>
								<h4 id="calendar">Calendar</h4>
								<p>
									The calendar The calendar is an easy way to get an intuitive view of data over a long period of time. Each day's value is converted to a 
									value: red for the lowest, yellow for the middle, green for the highest, and combinations of each for in between. Hovering over each day 
									shows additional information such as the exact day and value.
								</p>
							</li>
						</ul>
					</li>
					<li>
						<h3 id="features">Features</h3>
						<p>This section covers the common features between the graph pages. These features help in creating the data sets in each plot.</p>
						<ul>
							<li>
								<h4 id="inputType">Input Type</h4>
								<p>
									The input type describes the family for each data set. Data set families group similar sets such as stocks, crop prices, 
									or LIBOR swap rates. This selection helps narrow down which data sources can be selected from so there is not an overwhelming 
									number of choices.
								</p>
							</li>
							<li>
								<h4 id="inputValue">Input Value</h4>
								<p>
									The input value is the specific data set to be used. Most data sets are fixed and are picked from a list, but some (such as stocks) 
									are free text. These free text input fields have a limited amount of validation, sometimes returning a set of "did you mean" results,
									but others will only skip the data set and say that the input value was invalid.
								</p>
							</li>
							<li>
								<h4 id="startDate">Start Date</h4>
								<p>
									The start date is the first date to appear in the data set. If the start date is before the first date available in the data set then 
									the first date to appear is the first date available.
								</p>
							</li>
							<li>
								<h4 id="endDate">End Date</h4>
								<p>
									The end date is the last date to appear in the data set. If the end date is after the last date available in the data set then 
									the last date to appear is the last date available.
								</p>
							</li>
							<li>
								<h4 id="dayValue">Day Value</h4>
								<p>
									The day value selects which calculated value will be used for the data set. The default is the plain data set value (such as the stock 
									price for that day) while others can be calculated off that value (such as percentage stock price change from the day before). These 
									additional day value choices provide more flexibility when comparing data sets with different units (normalized value), daily changes, 
									and period changes. All values are calculated off of the original day value.
								</p>
							</li>
							<li>
								<h4 id="customFunction">Custom Function</h4>
								<p>
									The custom function is the most advanced feature in data set generation. It allows the user to generate custom data sets by executing a 
									user-generated function on each data point of a set. The functions are input as free text and should be written in JavaScript. The 
									convention used is that the data point value is contained in the variable "it". For example, the input text to double the value at each 
									data point would be "it * 2".
								</p>
								<br>
								<p>
									Many functions won't do much other than shift or stretch data sets. Multiplying or dividing data sets (e.g. "it * 2" or "it / 3") will 
									stretch or compress the data sets whereas adding and subtracting (e.g. "it + 10" or "it - 3") will shift the set up or down. Manipulating 
									a data set in one of these ways alone won't add much to a user's analysis as many of the benefits can be achieved in easier ways. For 
									example, if a user wants to compare two data sets which have different units or have values which are on different orders of magnitude 
									they could select the "Normalized Value" option from the <a href="#dayValue">Day Value</a> input so each data set is normalized and on 
									the same scale.
								</p>
							</li>
							<li>
								<h4 id="offset">Offset</h4>
								<p>
									The offset is an integer number of days the current data set is offset from a reference data set (often the first set). When there are only 
									two data sets (e.g. the <a href="#correlation">Correlation</a> plot) the offset is the number of days the second set is offset from the 
									first. When there is no limit on the number of data sets, but all data sets are required to be the same size (e.g. the 
									<a href="#covariance">Covariance</a> plot) the offset is the number of days the current set is offset from the first set's date range.
								</p>
								<br>
								<p>
									The offset is useful in investigation relationships between time-offset data sets. For example, if a high correlation was found between the 
									daily change of USO and yesterday's daily change of DJIA (an offset of -1) then the daily change of DJIA can be used as to predict the change 
									in USO for the next day.
								</p>
							</li>
						</ul>
					</li>
				</ul>
			</li>
			<li>
				<h2>Data</h2>
				<p>
					Some data sources can't be converted to numbers as easily. These sources capture more of the 'feel' of market indicators and are displayed in a few different type
					of graphs.
				</p>
				<ul>
					<li>
						<h3>Sentiment</h3>
						<p>
							The sentiment page is a way to explore the latest stories. Search results are displayed in an accordian style which can be expanded to reveal additional 
							information.
						</p>
					</li>
					<li>
						<h3>Matrix</h3>
						<p>
							The matrix page provided a view into how entities are related. Entities within news stories are aggregated and the top 30 are displayed in a matrix. The 
							matrix cells show the number of stories both column and row entities were present. Highly correlated entities will show up with a dark cell and can help 
							users discover hidden connections between entities.
						</p>
					</li>
					<!-- <li>
						<h3>Node Graph</h3>
						<p>
							TO DO
						</p>
					</li> -->
				</ul>
			</li>
			<li>
				<h2>Analysis</h2>
				<p>Stuff</p>
				<ul>
					<li><h3>Decision Tree</h3></li>
					<li><h3>Linear Regression</h3></li>
					<li><h3>Neural Net</h3></li>
				</ul>
			</li>
			<li>
				<h2>Extra</h2>
				<p>Listed below are a few extra features that AugurWorks provides.</p>
				<ul>
					<li>
						<h3>Activity Feed</h3>
						<p>
							The activity feed provides a look at what other people are searching for on AugurWorks. A real-time feed of the searches updates as searches are made 
							and each search is clickable, taking you right to that page with those inputs. A counter is also provided to show you how many other people have clicked 
							on that search as well.
						</p>
						<h4>Future</h4>
						<p>
							We hope to spruce this up a little bit by adding filtering, a top query list, etc. Suggestions are welcome.
						</p>
					</li>
					<li>
						<h3>Tutorials</h3>
						<p>
							TO DO
						</p>
					</li>
				</ul>
			</li>
		</ul>
	</div>
	<script>
		$(document).ready(function() {
			$('#AWcollapse').docCollapse({
				//color: '#0098D9'
			})
		})
	</script>
</body>
</html>