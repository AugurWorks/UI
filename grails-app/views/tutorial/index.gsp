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
									the covariance matrix calculates the correlation between all combinations of multiple data sets is shown. This provides a quick view
									of many correlation combinations. Interesting pairing can then be investigated through the correlation page.
								</p>
								<h4>Use Cases</h4>
								<p>A few use cases for the correlation plot are as follows.</p>
								<ul>
									<li>
										<b>Portfolio Diversification</b> - By inputting each stock in your current portfolio you can calculate a cross-section of your market exposure.
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
									value: red for the lowest, yellow for the middle, green for the highest, and combinations of each for in-between. Hovering over each day 
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
									are free text. These free text input fields have a limited amount of validation and will skip the data set and say that the input
									value was invalid.
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
								<br>
								<p>
									On the other hand, some functions will do more that scale or shift the data. Taking the log of the input (e.g. "Math.log(it + 1)") will
									scale down inputs which grow exponentially. In general, linear transformations like shifts or stretching will not affect analysis results
									while non-linear transformations such as exponentials and logs will.
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
				<h2>Data Aggregation</h2>
				<p>
					Some data sources are empirical in nature and can't be converted to numbers as easily. These sources capture more of the 'emotion’ of market indicators harvested 
					from news feeds and social media. This “unstructured” data is enriched and organized into entities, events and relationships. A key aspect of this enrichment is 
					the Sentiment, which indicates either a positive or negative attitude toward the topic. This information can be used on how the public feels about a current event 
					and media analysis.
				</p>
				<ul>
					<li>
						<h3>News & Events</h3>
						<p>
							This page displays the latest articles ranked by importance by the query term and date range. The default page will display the most important events and 
							new articles for last two days relating to ‘oil’, along with the overall Sentiment. The actual data source, along with more detailed analytics can be 
							viewed by clicking into the article. Even more granular detail can be viewed by clicking into the “Entities”.
						</p>
					</li>
					<li>
						<h3>Matrix</h3>
						<p>
							The Matrix Graph shows “frequency” of relationships between entities based on the query term and date range. That is, the number of news articles that 
							contain these two terms, indicating the most covered topics. The default page will display the most important events and new articles for last two days 
							relating to ‘oil’.
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
				<p>AugurWorks currently features two analysis algorithms: Decision Trees and Linear Regressions.</p>
				<ul>
					<li>
						<h3>Decision Tree</h3>
						<p>
				            This page allows you to generate a <a href="http://en.wikipedia.org/wiki/Decision_tree" target="_blank">decision tree</a> using
				            an algorithm similar to the <a href='http://en.wikipedia.org/wiki/ID3_algorithm' target="_blank">ID3</a> algorithm. A decision
				            tree is a common tool for making choices. You read it as "if A is true, do B; else, do C". In the case below, the inputs are stock
				            prices and the output is a buy / sell / hold estimate.
			            </p>
			            <br>
			            <p>
				            In the example below the "true" branch is always the upper branch (and is denoted by T). The stock to buy / sell / hold is the uppermost
				            stock. When you hit "submit", our servers calculate a decision tree and try to predict the outputs. The correctness of the tree is
				            determined by counting the percent of days which are predicted correctly by the generated model. The correctness should be much
				            higher than 50%, because otherwise you're better off with a coin flip!
			            </p>
					</li>
					<li>
						<h3>Linear Regression</h3>
						<p>
			                A <a href='http://en.wikipedia.org/wiki/Linear_regression'>linear regression</a> is a common way of analyzing data.
			                The concept of a linear regression stems from the equation for a line, <i>y = m * x + b</i>. This really means
			                <i>output = a * input + b</i> where <i>a</i> and <i>b</i> are both constants. In this case, we've extended the formula
			                to be <i>output = a * input_1 + b * input_2 + ... z</i>. We allow as many inputs as you want, and the output will
			                always be the top stock.
			            </p>
			            <br>
			            <p>
			                The algorithm used here is based off of a paper published in 1988 by Alan Miller. Feel free to read the
			                <a href='http://www.jstor.org/stable/2347583'>paper</a> if you're interested in the details!
			            </p>
					</li>
					<li>
						<h3>Neural Net</h3>
						<p>
			                A <a href="http://en.wikipedia.org/wiki/Artificial_neural_network">Neural Net</a> is a machine learning technique based off
			                of biological neurons. On one side there is an input layer of nodes
			                and on the other there is an output layer (only one in our case) of nodes. In between are hidden layers of nodes. The number
			                of hidden layers is determined by the <a href="#netDepth">net depth</a> input. Each node in a layer has a link connecting it
			                to each node in the adjacent layers. These links are initialized using random values.
			            </p>
			            <br>
			            <p>
			                To train the net a data set of training data is used. For each line in the input set there are a series of input values and
			                expected output values. First, the input layer of the neural net is set to the corresponding input set values. To calculate
			                the value of a node in the next layer the values of the previous layer are multiplied by the weights of the links to the given
			                node. These are summed and run through a static function, resulting in the node's value. This process is repeated until the output
			                node's value is calculated.
			            </p>
			            <br>
			            <p>
			                Once the output node's value is calculated it is compared to the expected output value. The difference is then propagated back
			                through the network, updating link weights based on another function as it goes. How much these weights are updated is determined by
			                the <a href="#netConstant">learning constant</a>. After the net reaches a certain level of accuracy
			                (the <a href="#netCutoff">accuracy cutoff</a>) or the net has trained a fixed number of rounds (<a href="#netRounds">training rounds</a>)
			                it has been trained and can start predicting outputs by taking input values and returning the output value.
			            </p>
			            <ul>
			            	<li>
			            		<h4>Additional Inputs</h4>
			            		<p>
			            			The Neural Net requires a few additional inputs which are under the <b>Advanced</b> section.
			            		</p>
			            		<ul>
			            			<li>
			            				<h5 id="netDepth">Depth</h5>
			            				<p>
			            					The neural net depth determines how many layers the neural net has.
			            				</p>
			            			</li>
			            			<li>
			            				<h5 id="netConstant">Learning Constant</h5>
			            				<p>
			            					The learning constant determines how much each link weight is updated during back propagation. A higher learning
			            					constant will train the net faster, but runs the risk of overshooting the correct answer. A lower learning constant
			            					will train at a slower pace but will usually be more accurate.
			            				</p>
			            			</li>
			            			<li>
			            				<h5 id="netRounds">Training Rounds</h5>
			            				<p>
			            					The training rounds input determines the maximum number of times a net trains using a certain set. This is essentially
			            					a maximum training time and will stop training if the <a href="#netCutoff">accuracy cutoff</a> has not been reached.
			            				</p>
			            			</li>
			            			<li>
			            				<h5 id="netCutoff">Accuracy Cutoff</h5>
			            				<p>
			            					The accuracy cutoff is the average accuracy over an entire training set at which the net will stop training. A higher
			            					accuracy cutoff may not be as accurate, but a lower accuracy cutoff will take longer to train and runs the risk of
			            					over training.
			            				</p>
			            			</li>
			            		</ul>
			            	</li>
			            </ul>
					</li>
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
							Tutorials will be coming soon.
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