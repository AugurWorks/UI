<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Community Feed</title>
<link rel="stylesheet" href="${resource(dir: 'css', file: 'about.css')}" type="text/css">
<style>
	th:hover, tr:hover {
		background: #E1F2B6;
	}
	tr:hover {
		cursor: pointer;
	}
</style>
</head>
<body>
	<div style="padding: 15px;">
		<h1>Watch the Community Feed</h1>
		<p>Click on a row to see the query.</p>
		<div style="max-height: 500px; overflow-y: auto;">
			<table id='main'>
				<tr>
					<th>User</th>
					<th>Date</th>
					<th>Page</th>
					<th>Inputs</th>
					<th>Views</th>
				</tr>
				<g:each in="${ requests }">
					<tr id="${ it.page }_${ it.id }" class="clickable">
						<td>${ it.user }</td>
						<td>${ it.requestDate.toString().substring(0, 19) }</td>
						<td>${ mapping[it.page] }</td>
						<td>${ it.dataSets.size() }</td>
						<td>${ it.views }</td>
					</tr>
				</g:each>
			</table>
		</div>
		<script>
			setInterval(function() { getFeed() }, 1000)
			refreshClickable()
		
			var latest = "${ requests[0].id }"

			function refreshClickable() {
				$('.clickable').click(function() {
					window.open($(this).attr('id').split('_').join('/'), '_blank');
				})
			}
			
			function getFeed() {
				$.ajax({
			        url : "/home/getFeed",
			        dataType : 'json',
			        data : {
			            latest: latest
			        },
			        success : function(data) {
				        if (data.success) {
				            if (data.data.length != 0) {
								refreshTable(data.data)
								latest = data.data[0].id
					        }
				        } else {
							console.log(data.error)
					    }
			        },
			        error : function(request, status, error) {
			            console.log(error)
			        }
			    });
			}

			function refreshTable(data) {
				for (i in data) {
					var str = '<tr id=' + data[i].page + '_' + data[i].id + ' class="clickable"><td>' + data[i].user.username + '</td><td>' + data[i].requestDate.substring(0, 19).split('T').join(' ') + '</td><td>' + data[i].page.charAt(0).toUpperCase() + data[i].page.slice(1) + '</td><td>' + data[i].dataSets.length + '</td><td>' + data[i].views + '</td></tr>';
					$('#main > tbody > tr').eq(0).after(str)
				}
				refreshClickable()
			}
		</script>
	</div>
</body>
</html>