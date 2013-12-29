<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Node Graph</title>
<style>
	.node {
	  stroke: #fff;
	  stroke-width: 1.5px;
	}
	
	.link {
	  stroke: #999;
	  stroke-opacity: .6;
	}
	
	text {
		font: 12px sans-serif;
		stroke-width: .5px;
		stroke: black;
	}
</style>
</head>
<body>
	<g:javascript src="d3.min.js" />
	<g:javascript src="jquery-ui.js" />
	<g:javascript src="jquery.blockUI.js" />
	<g:javascript src="datepickers.js" />
	<g:javascript src="sorting.js" />
	<g:javascript src="nodes.js" />
	<div id='content' style="padding: 10px;">
		<g:if test="${flash.message}">
			<div class='errors'>
				${flash.message}
			</div>
		</g:if>
		<div class="buttons">
			<div class="button-line">
				<div class="qtipText" title="Select a type of data.">Select 1: <g:select name="input1" from="${ dataTypes }" optionKey="name" /></div>
				<div class="qtipText" title="Input a keyword to query.">Keyword: <g:textField type="text" name="input2" value="Oil" /></div>
			</div>
			<div class="button-line">
				Start date: <g:textField type="text" id="startDate" name="startDate" value="${startDate}" />
				End date: <g:textField type="text" id="endDate" name="endDate" value="${endDate}" />
			</div>
			<!-- <div class="button-line">
				<div class="qtipText" title="Select an attribute to sort entities by.">Sort By: <g:select id="sort" name='sort' from='[[id:"name", name:"Name"], [id:"frequency", name:"Frequency"], [id:"type", name:"Type"], [id:"sentiment", name:"Sentiment"], [id:"significance", name:"Significance"]]' optionKey="id" optionValue="name"></g:select></div>
				<div class="qtipText" title="Select an order to sort entities by.">Order: <g:select id="order" name='order' from='[[id:"asc", name:"Ascending"], [id:"desc", name:"Descending"]]' optionKey="id" optionValue="name"></g:select></div>
			</div> -->
		</div>
		<div class="button-line">
			<button id="submit" class="buttons" style="font-size: large;" onclick="validate()">Submit</button>
			Order:
			<select id="order">
				<option value="name">Name</option>
				<option value="count">Count</option>
				<option value="group" selected>Type</option>
			</select>
			<table style="display: inline-block;">
				<tr>
					<td>Number of Nodes:</td>
					<td><input style="width: 60px;" type="number" id="nodeNum" name="nodeNum" min="-1" value="50" /></td>
				</tr>
				<tr>
					<td>Number of Links:</td>
					<td><input style="width: 60px;" type="number" id="linkNum" name="linkNum" min="-1" value="200" /></td>
				</tr>
			</table>
		</div>
		<div id="nodes" class="nodes" style="width: 100%; text-align: center;"></div>
		<div style="text-align: center;">
			<div id="0" class="info"><table><tr><td><img style="width: 20px; padding: 3px; display: inline-block;" src="${resource(dir: 'images', file: 'info.png')}"></td><td>How do I use it?</td></tr></table></div>
			<div id="1" class="info"><table><tr><td><img style="width: 20px; padding: 3px; display: inline-block;" src="${resource(dir: 'images', file: 'info.png')}"></td><td>What does it show?</td></tr></table></div>
			<div id="2" class="info"><table><tr><td><img style="width: 20px; padding: 3px; display: inline-block;" src="${resource(dir: 'images', file: 'info.png')}"></td><td>What does it mean?</td></tr></table></div>
		</div>
	</div>
	<script type="text/javascript">
		var dataSet = [];
		var fullData;

		// Sets initial values and sets the root accordian for the first time.
		$(document).ready(function() {
			setDatePickers()
			validate();
			qtip();
		});

		// Runs each time the 'Go!' button is clicked. Retrieves data from the server.
		function validate() {
			var req = new Object()
			var name = encodeURIComponent($('#input2').val())
			req[0] = {name: name, dataType: $('#input1').val(), startDate: $('#startDate').val(), endDate: $('#endDate').val()}
			ajaxCall(req, "${g.createLink(controller:'data', action:'getData')}")
		}

		// Function runs after AJAX call is completed. Resets accordian.
		function ajaxComplete(ajaxData) {
			fullData = ajaxData;
			if (fullData.success == false) {
				alert(fullData.message);
				return;
			}
			dataSet = ajaxData[0].data;
			setGraph($('#nodeNum').val() == -1 ? null : $('#nodeNum').val(), $('#linkNum').val() == -1 ? null : $('#linkNum').val());
		}

		// Iterates through data and sets the accordian.
		function setGraph(nodeNum, linkNum) {
			var graph = formatAllData(dataSet, nodeNum, linkNum)
			var width = Math.min($('#nodes').width(), window.innerHeight - 20),
			    height = width;
		
			var color = d3.scale.category20();
		
			var force = d3.layout.force()
				.charge(-220)
				.linkDistance(120)
				.size([width, height]);
		
			d3.select("#nodes").selectAll("svg").remove()
			var svg = d3.select("#nodes").append("svg")
				.attr("width", "100%")
				.attr("height", height).call(d3.behavior.zoom().scaleExtent([.1, 6]).on("zoom", zoom));

			function zoom() {
				force.stop();
				force.linkDistance(120 * d3.event.scale);
				force.start();
			}
		
			force.nodes(graph.nodes)
				.links(graph.links)
				.start();
			
			var link = svg.selectAll(".link")
			    .data(graph.links)
			   	.enter().append("line")
				.attr("class", "link")
				.style("stroke-width", function(d) { return Math.sqrt(d.value); });
			
			var node = svg.selectAll(".node")
				.data(graph.nodes)
				.enter().append("g")
				.attr("class", "node")
				.call(force.drag)
				.on("mouseover", mouseover)
				.on("mouseout", mouseout);
				
			node.append("circle")
				.attr("r", 5)
				.style("fill", function(d) { return color(d.group); });

			node.filter(function(d) { return link.filter(function(e) { return e.source == d || e.target == d; })[0].length == 0; }).remove()

			function mouseover(p) {
				var tempLinks = link.filter(function (d) { return d.source == p || d.target == p; })
					
				tempLinks.style('stroke', 'black')
					.style('stroke-opacity', 1)
				var related = []
				tempLinks.each(function(d) {
					if (related.indexOf(d.source) == -1) {
						related.push(d.source)
					}
					if (related.indexOf(d.target) == -1) {
						related.push(d.target)
					}
				})
				d3.selectAll('g').filter(function(d) { return related.indexOf(d) != -1; })
					.append('text')
					.text(function(d) { return d.name; })
					.attr("dx", 10)
					.attr("dy", -10);
			}

			function mouseout(p) {
				link.filter(function (d) { return d.source == p || d.target == p; })
					.style('stroke', '#999')
					.style('stroke-opacity', .6);
				d3.selectAll('g').selectAll('text').remove();
			}
			
			force.on("tick", function() {
				link.attr("x1", function(d) { return d.source.x; })
				    .attr("y1", function(d) { return d.source.y; })
				    .attr("x2", function(d) { return d.target.x; })
				    .attr("y2", function(d) { return d.target.y; });
				
				node.attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });
			});
		}

		function qtip() {
			$('div .qtipText').qtip({
			    style: {
			    	widget: true,
			    	def: false
			    },
			    position: {
		            my: 'bottom left',
		            at: 'top right'
		        }
			});
			var html = []
			html[0] = '<h1>How do I use it?</h1>';
			html[0] += '<p>';
			html[0] += 'Start by selecting an input type, an input values, and a date range.';
			html[0] += ' Entities can also be sorted by the "Sort By" and "Order" criteria.';
			html[0] += ' After adding all inputs press the "Submit" button.';
			html[0] +='</p>';
			html[0] +='<br></br>';
			html[0] += '<p>';
			html[0] += 'Once the inputs have been submitted a data accordian will be created which can be clicked on and expanded.';
			html[0] += ' Inside each data set there is an "Entities" tab which can also be expanded.';
			html[0] +='</p>';
			
			html[1] = '<h1>What does it show?</h1>';
			html[1] += '<p>';
			html[1] += 'The Sentiment page shows news story and Tweets which match the input keyword.';
			html[1] += ' The stories are ranked in descending order of relevance and contain information about publish date, title, description, and a link to the story.';
			html[1] += ' The entitles accordian provides information about the top entities within the story and information about them.';
			html[1] +='</p>';
			
			html[2] = '<h1>What does it mean?</h1>';
			html[2] += '<p>';
			html[2] += 'Each story has information about it, but the real information comes from the entities within them.';
			html[2] += ' The frequency reflects the number of time each entity shows up in the article and the type is what type of entity it is.';
			html[2] += ' The sentiment and significance are values generated from the sentiment analysis engine we use.';
			html[2] += ' The significance is a generated value of how significant the entity is within the article and the sentiment is a measure of how the article "feels" about the entity.';
			html[2] += ' This means that an entity which has a large, positive sentiment is written about positivly and vice versa.';
			html[2] += '</p>';
			$('.info').qtip({
			    style: {
			    	widget: true,
			    	def: false,
			    	width: '70%'
			    },
			    position: {
		            my: 'bottom center',
		            at: 'top center',
			    	target: $('#1')
		        },
		        content: {
			        text: function() {
				        return html[parseInt($(this).attr('id'))];
			        }
		        },
		        hide: {
		        	fixed: true
		        }
			});
		}
	</script>
</body>
</html>
