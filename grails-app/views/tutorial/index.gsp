<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Documentation</title>
<g:javascript src="AWcollapse.js" />
</head>
<body>
	<div id='content' style='padding: 20px;'>
		<h1>Documentation</h1>
		<p>Welcome to the AugurWorks documentation! You must be really bored.</p>
		<ul id="AWcollapse">
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
								<p>Stuff</p>
								<ul>
									<li>
										<h5>Use Cases</h5>
										<p>Stuff</p>
									</li>
								</ul>
							</li>
							<li>
								<h4 id="correlation">Correlation</h4>
								<p>Stuff</p>
							</li>
							<li>
								<h4 id="covariance">Covariance</h4>
								<p>Stuff</p>
							</li>
							<li>
								<h4 id="calendar">Calendar</h4>
								<p>Stuff</p>
							</li>
						</ul>
					</li>
					<li>
						<h3>Features</h3>
						<p>Stuff</p>
						<ul>
							<li>
								<h4 id="inputType">Input Type</h4>
								<p>Stuff</p>
							</li>
							<li>
								<h4 id="inputValue">Input Value</h4>
								<p>Stuff</p>
							</li>
							<li>
								<h4 id="dayValue">Day Value</h4>
								<p>Stuff</p>
							</li>
							<li>
								<h4 id="customFunction">Custom Function</h4>
								<p>Stuff</p>
							</li>
							<li>
								<h4 id="offset">Offset</h4>
								<p>Stuff</p>
							</li>
						</ul>
					</li>
				</ul>
			</li>
			<li>
				<h2>Data</h2>
				<p>Stuff</p>
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
		</ul>
	</div>
	<script>
		$(document).ready(function() {
			$.AWcollapse('AWcollapse')
		})
	</script>
</body>
</html>