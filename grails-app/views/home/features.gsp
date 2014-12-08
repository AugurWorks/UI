<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Features</title>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'about.css')}" type="text/css">
<style>
	h1 {
		font-size: 40px;
	}
	h2, h3, h4 {
		padding-bottom: 5px;
	}
	p {
		padding-bottom: 25px;
	}
	h3 {
		text-decoration: underline;
	}
</style>
</head>
<body>
	<div id='about'>
		<h1>Beta Features</h1>
		<h2>Implemented</h2>
		<h3>Graphs</h3>
		<h4>Line Graph</h4>
		<p>
			The line graph is the most intuitive plot of time series data and seamlessly integrates with all number data sets. Data sets can be plotted against others on 
			independent time scales and have informational highlighters when rolling over the plot.
		</p>
		<h4>Correlation Plot</h4>
		<p>
			The correlation plot graphs two data sets against each other and fits a trend line to the data. This visualization helps users interpret the correlation between 
			two data sets as well as look for anomalies for further investigation. Data sets can also be offset be any number of days, meaning correlated, offset time-series 
			data can be used for predicting one variable off another.
		</p>
		<h4>Covariance Matrix</h4>
		<p>
			The covariance matrix is a way to represent more correlations then just the one in the correlation plot. The matrix provides a color coded table to show the 
			correlations between all combinations of multiple inputs.
		</p>
		<h4>Calendar</h4>
		<p>
			The calendar view graphs data on a compact calendar view where data points are plotted on a color spectrum. This view allows for visual analysis of daily, 
			weekly, or monthly trends.
		</p>
		<h3>Data Aggregation</h3>
		<h4>News & Events</h4>
		<p>
			This is a simple accordion view where stacked ranked news stories or tweets can be browsed and expanded for research.
		</p>
		<h4>Matrix</h4>
		<p>
			The matrix view gives a more in-depth view of text based data by analyzing similarities between data points and plotting those as color coded cells within 
			a matrix. Rolling over each cell gives additional information about the specific relationship.
		</p>
		<h3>Analysis</h3>
		<p>
			AugurWorks also provides an analysis interface to do more in-depth analysis. These algorithms are intended to be used to validate relationships seen though 
			visualizations and make predictions based on those relationships.
		</p>
        <h4>Decision Tree (If-Then_Else)</h4>
		<p>
			Decision trees are tree-like graphs which maps a data point to a predicted value based on its common characteristics to other data points and their outcomes.
		</p>
		<h4>Linear Regression</h4>
		<p>
			A linear regression creates a best-fit linear equation with as many variables as input columns. The coefficients to the variables are calculated through a 
			regression of the data sets, resulting in an equation which can be used to predict an output value.
		</p>
		<h3>Community</h3>
		<h4>Feeds</h4>
		<p>
			Captures a user’s recent queries for all users to view. To repeat the query, just clock on the individual feed. This will be enhanced in upcoming beta deployments.
		</p>
		<h2>Upcoming</h2>
		<h3>Data Aggregation</h3>
		<h4>Node Graph</h4>
		<p>
			The node graph plots the same relationships as the matrix view but as a graph network. The visualization can be used to see what the most connected entities are 
			within the network.
		</p>
		<h3>Analysis</h3>
        <h4>Neural Net (Machine Learning)</h4>
		<p>
			Neural nets are based off of neurons and are created by repeated training where connections between nodes are strengthened and weakened based on output value.
		</p>
		<h3>Tutorials</h3>
		<p>
			A brief set of tutorials is included to jumpstart users by walking them through a few use cases for the site. The tutorials will outline the main features, 
			how they integrate, and recommended use.
		</p>
		<h3>Community</h3>
		<p>
			TBD
		</p>
	</div>
</body>
</html>