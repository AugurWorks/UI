<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Adjacency Matrix</title>
<style>
	.background {
	  fill: #eee;
	}
	
	line {
	  stroke: #fff;
	}
	
	text.active {
	  fill: red;
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
					<td><input style="width: 60px;" type="number" id="nodeNum" name="nodeNum" value="50" /></td>
				</tr>
			</table>
		</div>
		<div id="matrix" class="matrix" style="width: 100%; text-align: center;"></div>
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
			setMatrix($('#nodeNum').val() == -1 ? null : $('#nodeNum').val(), null);
		}

		// Iterates through data and sets the accordian.
		function setMatrix(nodeNum, linkNum) {
			var matrixData = formatAllData(dataSet, nodeNum, linkNum)
			var margin = {top: 150, right: 0, bottom: 10, left: 150},
			    width = $('#matrix').width() - margin.left - margin.right,
			    height = width;
	
			var x = d3.scale.ordinal().rangeBands([0, width]),
			    z = d3.scale.linear().domain([0, 4]).clamp(true),
			    c = d3.scale.category10().domain(d3.range(10));

			d3.select("#matrix").selectAll("svg").remove()
			var svg = d3.select("#matrix").append("svg")
			    .attr("width", width + margin.left + margin.right)
			    .attr("height", height + margin.top + margin.bottom)
			  	.append("g")
			    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");
	
			  var matrix = [],
			      nodes = matrixData.nodes,
			      n = nodes.length;
	
			  // Compute index per node.
			  nodes.forEach(function(node, i) {
			    node.index = i;
			    node.count = 0;
			    matrix[i] = d3.range(n).map(function(j) { return {x: j, y: i, z: 0}; });
			  });
	
			  // Convert links to matrix; count character occurrences.
			  matrixData.links.forEach(function(link) {
			    matrix[link.source][link.target].z += link.value;
			    matrix[link.target][link.source].z += link.value;
			    matrix[link.source][link.source].z += link.value;
			    matrix[link.target][link.target].z += link.value;
			    nodes[link.source].count += link.value;
			    nodes[link.target].count += link.value;
			  });
	
			  // Precompute the orders.
			  var orders = {
			    name: d3.range(n).sort(function(a, b) { return d3.ascending(nodes[a].name.toLowerCase(), nodes[b].name.toLowerCase()); }),
			    count: d3.range(n).sort(function(a, b) { return nodes[b].count - nodes[a].count; }),
			    group: d3.range(n).sort(function(a, b) { return nodes[b].group > nodes[a].group; })
			  };
	
			  // The default sort order.
			  x.domain(orders.name);
	
			  svg.append("rect")
			      .attr("class", "background")
			      .attr("width", width)
			      .attr("height", height);
	
			  var row = svg.selectAll(".row")
			      .data(matrix)
			    .enter().append("g")
			      .attr("class", "row")
			      .attr("transform", function(d, i) { return "translate(0," + x(i) + ")"; })
			      .each(row);
	
			  row.append("line")
			      .attr("x2", width);
	
			  row.append("text")
			      .attr("x", -6)
			      .attr("y", x.rangeBand() / 2)
			      .attr("dy", ".32em")
			      .attr("text-anchor", "end")
			      .text(function(d, i) { return nodes[i].name; });
	
			  var column = svg.selectAll(".column")
			      .data(matrix)
			    .enter().append("g")
			      .attr("class", "column")
			      .attr("transform", function(d, i) { return "translate(" + x(i) + ")rotate(-90)"; });
	
			  column.append("line")
			      .attr("x1", -width);
	
			  column.append("text")
			      .attr("x", 6)
			      .attr("y", x.rangeBand() / 2)
			      .attr("dy", ".32em")
			      .attr("text-anchor", "start")
			      .text(function(d, i) { return nodes[i].name; });
	
			  function row(row) {
			    var cell = d3.select(this).selectAll(".cell")
			        .data(row.filter(function(d) { return d.z; }))
			      	.enter().append("rect")
			        .attr("class", "cell")
			        .attr("x", function(d) { return x(d.x); })
			        .attr("width", x.rangeBand())
			        .attr("height", x.rangeBand())
			        .style("fill-opacity", function(d) { return z(d.z); })
			        .style("fill", function(d) { return nodes[d.x].group == nodes[d.y].group ? c(nodes[d.x].group) : null; })
			        .on("mouseover", mouseover)
			        .on("mouseout", mouseout);
			  }
	
			  function mouseover(p) {
			    d3.selectAll(".row text").classed("active", function(d, i) { return i == p.y; });
			    d3.selectAll(".column text").classed("active", function(d, i) { return i == p.x; });
			  }
	
			  function mouseout() {
			    d3.selectAll("text").classed("active", false);
			  }

			  d3.selectAll('.cell').each(function(d) {
					$(this).qtip({
					    style: {
					    	widget: true,
					    	def: false,
					    },
					    position: {
				            my: 'bottom right',
				            at: 'top left'
				        },
						content: {
							text: '<div><div>' + nodes.filter(function(e, i) { return i == d.x; })[0].name + '<div></div>' +
								nodes.filter(function(e, i) { return i == d.y; })[0].name + '<div></div>' +
								'Count: ' + d.z + '</div></div>'
						}
					})
		        })
	
			  d3.select("#order").on("change", function() {
			    clearTimeout(timeout);
			    order(this.value);
			  });
	
			  function order(value) {
			    x.domain(orders[value]);
	
			    var t = svg.transition().duration(2500);
	
			    t.selectAll(".row")
			        .delay(function(d, i) { return x(i) * 4; })
			        .attr("transform", function(d, i) { return "translate(0," + x(i) + ")"; })
			      	.selectAll(".cell")
			        .delay(function(d) { return x(d.x) * 4; })
			        .attr("x", function(d) { return x(d.x); });
	
			    t.selectAll(".column")
			        .delay(function(d, i) { return x(i) * 4; })
			        .attr("transform", function(d, i) { return "translate(" + x(i) + ")rotate(-90)"; });
			  }
	
			  var timeout = setTimeout(function() {
			    order("group");
			    //d3.select("#order").property("selectedIndex", 2).node().focus();
			  }, 1000);
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
