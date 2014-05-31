<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Check</title>
</head>
<body>
	<div id='content' style='padding: 10px;'>
		<h1>Check Searches</h1>
		<p>
			Use this to check previous searches.
		</p>
		<div class="errors" id="errors" style="display: none;"></div>
		<g:select name="sets" from="${ searches }" value="${ current }" optionKey="id" optionValue="name" />
		<h1>Result</h1>
		<div id="result"></div>
	</div>
	<script>
		$(function() {
			$('#sets').chosen();
			refresh()
			$('#sets').change(function() {
				refresh()
			})
		});

		function refresh() {
			var resp = $.ajax({
		        url : '/search/refresh',
		        dataType : 'json',
		        data : {
		            id : $('#sets').val()
		        },
		        success : function(data) {
					//console.log(data)
					if (!data.done) {
						setTimeout(refresh, 5000)
					}
					draw(data)
		        },
		        error : function(request, status, error) {
		            console.log(error)
		        }
		    });
		}

		function draw(data) {
			var html;
			if (!data.done) {
				html = 'Not done.'
			} else {
				html = '<h2>Correlations</h2>';
				html += '<table><tr><th>' + ['First', 'Second', 'Offset', 'Correlation'].join('</th><th>') + '</th></tr><tr>';
				html += $.map(data.correlations.sort(function(a, b) { return b.correlation - a.correlation; }), function(d) {
					return '<td>' + [getSet(d.first.id, data.sets), getSet(d.second.id, data.sets), d.offset, '<a href="/correlation/show/' + d.id + '">' + d.correlation + '</a>'].join('</td><td>') + '</td>';
				}).join('</tr><tr>');
				html += '</tr></table>';
			}
			$('#result').html(html)
		}

		function getSet(id, sets) {
			var set = $.grep(sets, function(d) { return d.id == id; })[0];
			return set.input ? set.input : set.dataTypeChoice;
		}
	</script>
</body>
</html>
