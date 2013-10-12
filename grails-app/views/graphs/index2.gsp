<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>Graph</title>
		<style>
			.jqplot-yaxis-label {
				margin-right: 40px;
			}
		</style>
	</head>
	<body>
		<g:javascript src="jquery-2.0.3.js"/>
		<g:javascript src="jquery.jqplot.js"/>
		<g:javascript src="jqplot.canvasTextRenderer.js"/>
		<g:javascript src="jqplot.canvasAxisLabelRenderer.js"/>
		<g:javascript src="jqplot.CanvasAxisTickRenderer.js"/>
		<g:javascript src="jqplot.DateAxisRenderer.js"/>
		<g:javascript src="jqplot.highlighter.js"/>
		<g:javascript src="jqplot.cursor.js"/>
		<g:javascript src="jqplot.dateAxisRenderer.js"/>
		<div id='content' style='padding: 10px;'>
			<div style="text-align: right">Username: ${ service.authentication.name }</div>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div style="text-align: center; padding: 20px;">
				<div id="chart1"></div>
			</div>
			Stock: <g:textField type="text" id="stock" name="stock" value="${stock}"/>
			<button onClick="submit();">Go!</button>
			<script type="text/javascript">
				function submit() {
					var stock = $('#stock').val()
					window.location.href = "./index2?stock=" + stock
				}
				$(document).ready(function(){
					var raw = '${ stocks.getStock(stock, 0, 1, 2010, 0, 1, 2012, "d") }'
					if (raw == 'Stock Not Found') {
						$('#chart1').html('The stock is invalid.')
					} else {
						var line = $.parseJSON(raw.replace(/&quot;/g, '"'))
						var list = []
						$.each(line.data[0], function(index, value) {
						    list.push([value.date, parseFloat(value.val)])
						});
						$('#chart1').width('100%')
						$('#chart1').height('600px')
						var plot1 = $.jqplot('chart1', [list.reverse()], {
						    title:'Default Date Axis',
						    axesDefaults: {
						        tickRenderer: $.jqplot.CanvasAxisTickRenderer ,
						        tickOptions: {
						          angle: -30,
						          fontSize: '10pt'
						        }
						    },
						    axes: {
							    xaxis: {
							    	labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
								    renderer: $.jqplot.DateAxisRenderer,
								    label: 'Date',
								    tickOptions:{
								        formatString:'%b %#d %y'
								    }
								},
								yaxis: {
							        tickOptions:{
							            formatString:'$%.2f'
							        },
									labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
									label: 'Price ($)'
								}
					    	},
						    highlighter: {
						        show: false
						    },
						    cursor: {
						        show: true,
						        tooltipLocation:'sw'
						    },
							series: [{showMarker: true}]
					  	});
					}
			});
		</script>
		</div>
	</body>
</html>
