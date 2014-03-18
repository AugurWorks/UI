function generateImage(id, time, ticker) {
	var margin = {
		top : 10, right : 0, bottom : 20, left : 0
	}, width = $('#' + id).width() - margin.left - margin.right,
		height = $('#' + id).height()- margin.top - margin.bottom;

	var parseDate = d3.time.format("%y-%m-%d").parse;

	var x = d3.time.scale().range([ 0, width ]);

	var y = d3.scale.linear().range([ height, 0 ]);

	var xAxis = d3.svg.axis().scale(x).orient("bottom").ticks(0);

	var yAxis = d3.svg.axis().scale(y).orient("left").ticks(0);

	var line = d3.svg.line().x(function(d) {
		return x(d.date);
	}).y(function(d) {
		return y(d.close);
	});

	var svg = d3.select("#" + id).attr("width",
			width + margin.left + margin.right).attr("height",
			height + margin.top + margin.bottom).append("g").attr("transform",
			"translate(" + margin.left + "," + margin.top + ")");

	d3.json("/home/landingData?ticker=" + ticker, function(error, temp) {
		var data = []
		Object.keys(temp).forEach(function(me) {
			data.push({ date: new Date(me), close: temp[me] })
		});

		x.domain(d3.extent(data, function(d) {
			return d.date;
		}));
		y.domain(d3.extent(data, function(d) {
			return d.close;
		}));

		svg.append("g").attr("class", "x axis").attr("transform", "translate(0," + height + ")").call(xAxis);

		svg.append("g").attr("class", "y axis").call(yAxis);

		var path = svg.append("path").datum(data.reverse()).attr("class", "line").attr("d", line);

		var totalLength = path.node().getTotalLength();

		path.attr("stroke-dasharray", totalLength + " " + totalLength).attr(
				"stroke-dashoffset", totalLength).transition().duration(time)
				.ease("linear").attr("stroke-dashoffset", 0);
	});
}