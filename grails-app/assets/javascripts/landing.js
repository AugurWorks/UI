function generateImage(id, time, ticker) {
	var margin = {
		top : 10, right : 0, bottom : 20, left : 0
	}, width = window.innerWidth - margin.left - margin.right,
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
		$('#ticker').val(temp.metadata.req.name)
		Object.keys(temp.dates).forEach(function(me) {
			data.push({ date: new Date(me), close: temp.dates[me] })
		});

		x.domain(d3.extent(data, function(d) {
			return d.date;
		}));
		y.domain(d3.extent(data, function(d) {
			return d.close;
		}));

		svg.append("g").attr("class", "x axis").attr("transform", "translate(0," + height + ")").call(xAxis);

		svg.append("g").attr("class", "y axis").call(yAxis);

		var path = svg.append("path").datum(data).attr("class", "line").attr("d", line);

		var totalLength = path.node().getTotalLength();

		path.attr("stroke-dasharray", totalLength + " " + totalLength).attr(
				"stroke-dashoffset", totalLength).transition().duration(time)
				.ease("linear").attr("stroke-dashoffset", 0);
	});
}

function reset(time, ticker) {
	$('#coverImage').html('')
	generateImage('coverImage', time, $('#ticker').val());
	if ($(window).width() > 1600 && collapsed) {
		collapsed = false

		for (i in tables) {
			var me = tables[i];
			$(me).children().children('tr:eq(1)').each(function(){
				var html = '<td style="width: 25%">';
				html += $('td:eq(0)', this).html() + '</td><td style="width: 25%">';
				html += $('td:eq(1)', this).html() + '</td>';
				$(me + ' > tbody > tr:eq(0)').append(html)
				$('td:eq(1)', this).remove()
				$('td:eq(0)', this).remove()
				$(this).remove()
			})
			$(me).width('100%')
		}
		$('#team > tbody').prepend('<tr></tr>')
		$('#team > tbody').children('tr').each(function(i, me) {
			$(me).children('td').each(function() {
				$('#team > tbody > tr:eq(0)').append('<td width="33%;">' + $(this).html() + '<td>')
			})
			if (i != 0) {
				$(me).remove()
			}
			$('#team td').each(function() {
				if ($(this).html().length == 0) {
					$(this).remove()
				}
			})
		})
		
		$('#team').width('100%')
		$('.box').width(1600)
		$('.subheader').width(900)
	} else if ($(window).width() <= 1600 && !collapsed) {
		collapsed = true
		for (i in tables) {
			var me = tables[i];
			$(me).children().children('tr').each(function(){
				var html = '<td>';
				html += $('td:eq(2)', this).html() + '</td><td>';
				html += $('td:eq(3)', this).html() + '</td>';
				$('<tr>').insertAfter(this).append(html)
				$('td:eq(3)', this).remove()
				$('td:eq(2)', this).remove()
			})
			$(me).width(800)
		}
		$('#team > tbody').append('<tr></tr><tr></tr>')
		$('#team > tbody > tr:eq(0)').children('td').each(function(i, me) {
			$('#team > tbody > tr:eq(' + i + ')').append('<td>' + $(me).html() + '</td>')
			$(me).remove()
		})
		$('#team').width(500)
		$('.box').width(800)
		$('.subheader').width(600)
	}
}