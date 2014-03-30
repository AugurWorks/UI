<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Graph</title>
<style>
	.node circle {
	  cursor: pointer;
	  fill: #fff;
	  stroke: steelblue;
	  stroke-width: 1.5px;
	}
	
	.node text {
	  font-size: 15px;
	}
	
	path.link {
	  fill: none;
	  stroke: #ccc;
	  stroke-width: 1.5px;
	}
</style>

</head>
<body>
	<g:javascript src="d3.min.js" />
	<g:javascript src="plots/decisionTree.js" />
	<div id='content' style='padding: 10px;'>
		<div style="text-align: center; padding: 5px;">
			<div id="chart1"></div>
		</div>
		<script type="text/javascript">
			var json = {
				    "cutoffValue": -24.850000000000364,
				    "cutoffName": "LT",
				    "cutoffType": "DJIA",
				    "results": {
				        "isTrue": {
				            "cutoffValue": 0.14000000000000057,
				            "cutoffName": "EQ",
				            "cutoffType": "T",
				            "results": {
				                "isTrue": {
				                    "results": "BUY"
				                },
				                "isFalse": {
				                    "results": "BUY"
				                }
				            }
				        },
				        "isFalse": {
				            "cutoffValue": 0.14000000000000057,
				            "cutoffName": "EQ",
				            "cutoffType": "T",
				            "results": {
				                "isTrue": {
				                    "results": "BUY"
				                },
				                "isFalse": {
				                    "results": "BUY"
				                }
				            }
				        }
				    }
				}
			$(function() {
				console.log(json)
				drawTree(json, $('#chart1').width(), Math.min($(window).height() * .8, $('#chart1').width()))
			})
		</script>
	</div>
</body>
</html>
