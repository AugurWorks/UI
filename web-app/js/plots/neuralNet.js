function generateData(cols, rows) {
	var data = {nodes: [], links: []};
	var id = 1;
	d3.range(cols).forEach(function(c) {
		d3.range(rows).forEach(function(r) {
			if (c != cols - 1) {
				d3.range(rows).forEach(function(r2) {
					data.links.push({s: id, t: id + rows + r2 - r, val: Math.random() * 2 - 1});
				})
			} else {
				data.links.push({s: id, t: cols * rows + 1, val: Math.random() * 2 - 1});
			}
			data.nodes.push({id: id++, col: c, row: r});
		});
	});
	data.nodes.push({id: id++, col: cols, row: 0});
	return data;
}

function drawNet(input, w, h) {
	var data = generateData(5, 5);
	
	var margin = {top: 40, right: 40, bottom: 40, left: 40},
		height = h - margin.top - margin.bottom,
		width = w - margin.right - margin.left;
	
	var svg = d3.select("#chart1").append("svg").attr("width", w)
		.attr("height", h).append("g")
		.attr("transform", "translate(" + margin.left + "," + margin.top + ")");
	
	var cols = d3.max(data.nodes, function(d) { return d.col; }),
		rows = d3.max(data.nodes, function(d) { return d.row; });
	
	var n = data.nodes.map(function(d) {
		return {x: width / cols * d.col, y: height / rows * d.row, id: d.id, r: 7.5}
	});
	
	var l = data.links.map(function(d) {
		var s = n.filter(function(c) { return c.id == d.s; })[0],
			e = n.filter(function(c) { return c.id == d.t; })[0];
		return {val: d.val, sx: s.x, sy: s.y, ex: e.x, ey: e.y, w: 1, s: d.s, t: d.t};
	});
	
	var extent = d3.extent(l, function(d) { return d.val; });
	var scale = d3.scale.linear().domain([extent[0], 0, extent[1]]).range(['red', 'white', 'green']);
	
	var links = svg.selectAll('.link').data(l).enter().append('line').attr('class', 'link')
		.attr('x1', function(d) { return d.sx; })
		.attr('x2', function(d) { return d.ex; })
		.attr('y1', function(d) { return d.sy; })
		.attr('y2', function(d) { return d.ey; })
		.style('stroke', function(d) { return scale(d.val); })
		.style('stroke-width', function(d) { return d.w; });
	
	var textStart = svg.selectAll('.textStart').data(l).enter().append('text').attr('class', 'textStart')
				.attr('transform', function(l) {
					var cur = n.filter(function(d) { return l.s == d.id; })[0];
					return 'translate(' + cur.x + ',' + (cur.y - 15) + ')';
				})
				.style('opacity', 0).text(function(d) { return Math.round(d.val * 100) / 100; });
	
	var textEnd = svg.selectAll('.textEnd').data(l).enter().append('text').attr('class', 'textEnd')
				.attr('transform', function(l) {
					var cur = n.filter(function(d) { return l.t == d.id; })[0];
					return 'translate(' + cur.x + ',' + (cur.y - 15) + ')';
				})
				.style('opacity', 0).text(function(d) { return Math.round(d.val * 100) / 100; });

	var nodes = svg.selectAll('.node').data(n).enter().append('g')
				.attr('class', 'node').attr('transform', function(d) { return 'translate(' + d.x + ',' + d.y + ')'; });
	nodes.append('circle').attr('r', function(d) { return d.r; });
	
	nodes.on('mouseover', function(d) {
		textStart.filter(function(e) { return e.t == d.id; }).style('opacity', 1);
		textEnd.filter(function(e) { return e.s == d.id; }).style('opacity', 1);
		var others = links.filter(function(l) { return l.s != d.id && l.t != d.id; });
		others.style('opacity', .25);
	});
	
	nodes.on('mouseout', function(d) {
		svg.selectAll('text').style('opacity', 0);
		var others = links.filter(function(l) { return l.s != d.id && l.t != d.id; });
		others.style('opacity', 1);
	});
}
