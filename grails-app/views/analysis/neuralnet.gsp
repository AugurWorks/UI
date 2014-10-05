<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Neural Net</title>
<style>
    .node circle {
      cursor: pointer;
      fill: #fff;
      stroke: steelblue;
      stroke-width: 1.5px;
    }
    text {
      text-anchor: middle;;
    }
    path.link {
      fill: none;
      stroke: #ccc;
      stroke-width: 1.5px;
    }
    .jqplot-table-legend {
        width: auto;
    }
</style>
</head>
<body>
    <g:javascript src="d3.min.js" />
    <g:javascript src="plots/neuralNet.js" />
    <g:javascript src="jqplot/jquery.jqplot.js" />
    <g:javascript src="jqplot/jqplot.canvasTextRenderer.js" />
    <g:javascript src="jqplot/jqplot.canvasAxisLabelRenderer.js" />
    <g:javascript src="jqplot/jqplot.canvasAxisTickRenderer.js" />
    <g:javascript src="jqplot/jqplot.highlighter.js" />
    <g:javascript src="jqplot/jqplot.cursor.js" />
    <g:javascript src="jqplot/jqplot.dateAxisRenderer.js" />
    <g:javascript src="jqplot/jqplot.enhancedLegendRenderer.js" />
    <div id='content' style='padding: 10px;'>
        <div>
            <h3>What am I looking at?</h3>
        </div>
        <br />
        <g:render template="../layouts/menu" />
        <br />
        <div style="text-align: center; padding: 5px;">
            <div id="chart1"></div>
        </div>
        <button class="buttons" class="button-reset">Reset</button>
        <h1>Previous Nets</h1>
        <ul style="margin-left: 25px;">
        	<g:each in="${ nets }" var="cur">
        		<g:if test="${ cur.neuralNet.dataLocation }">
        			<li><a href="/analysis/neuralnet/${ cur.id }">${ cur.dataSets.collect { it.name + ' (' + it.startDate + '-' + it.endDate + ')' }.join(', ') }</a></li>
        		</g:if>
        		<g:else>
        			<li>${ cur.dataSets.collect { it.name + ' (' + it.startDate + '-' + it.endDate + ')' }.join(', ') }</li>
        		</g:else>
        	</g:each>
        </ul>
        <g:render template="../layouts/qtip" />
        <script type="text/javascript">
			var data;
			if (${ data != null }) {
				data = JSON.parse("${ data }".replace( /\&quot;/g, '"' ));
			}
        
            counter = 5
            $(document).ready(function() {
                setDatePickers();
                drawTable();
                //validate();
                //drawNet(null, $('#chart1').width(), Math.min($(window).height() * .8, $('#chart1').width()))
                if (data) {
                	drawPlot(data);
                }
                qtip();
                $('#rounds, #depth').on('change', function() {
					estimate();
                });
                $('.buttons').on('click', function() {
					estimate();
                });
                estimate();
            });

            function estimate() {
                var i = Object.keys(req).length - 1;
                var r = $('#rounds').val();
                var s = Math.floor((new Date($('#endDate').val()) - new Date($('#startDate').val())) / (1000 * 60 * 60 * 24));
                var d = $('#depth').val() - 1;
                var c = .0003;
				var time = c * r * s * (i * i * d + i);
				if (i >= 0 && d >= 0) {
					$('#estimate').html('Time Estimate: <b>~' + formatTime(time) + '</b>');
				} else {
					$('#estimate').html('');
				}
            }

            function formatTime(sec) {
				if (sec < 60) {
					return Math.round(sec * 10) / 10 + ' sec';
				} else if (sec >= 60 && sec < 3600) {
					return Math.round(sec / 6) / 10 + ' min';
				} else {
					return Math.round(sec / 360) / 10 + ' hr';
				}
            }

            // Resize the plot on window resize
            window.onresize = resize
            function resize() {
                var size = Math.min(window.innerWidth - 150, window.innerHeight - 100)
                $('#chart1').width('100%');
                $('#chart1').height('600px');
                if(plot1) {
                    $('#chart1').empty();
                }
                plot()
            }

            // Function runs after AJAX call is completed. Creates additional data sets (daily change, change since start) and replots the graph.
            function ajaxComplete(ajaxData) {
                console.log(ajaxData)
            }

            // Function runs after AJAX call is completed. Creates additional data sets (daily change, change since start) and replots the graph.
            function drawPlot(ajaxData) {
                fullAjaxData = ajaxData
                ajaxObject = setPlotData(ajaxData, 'input', 'invalidMessage')
                dataSet = ajaxObject.dataSet
                inputArray = ajaxObject.inputArray
                nameArray = ajaxObject.nameArray
                seriesArray = ajaxObject.seriesArray
                if (!initilized) {
                    resize()
                    initilized = true
                } else {
                    replot()
                }
                console.log(ajaxData['-1'].metadata.stats)
            }

            // Refreshes the plot.
            function replot() {
                $('#chart1').empty();
                resize()
            }

            // Plots the graph for the first time.
            function plot() {
                if (dataSet.length == 0) {
                    $('#chart1').html('The input is invalid.')
                } else {
                    var axes = new Object()
                    axes.xaxis = {
                            labelRenderer : $.jqplot.CanvasAxisLabelRenderer,
                            renderer : $.jqplot.DateAxisRenderer,
                            label : 'Date',
                            tickOptions : {
                                formatString : '%b %#d %y'
                            }
                        }
                    var units = []
                    seriesArray = []
                    for (i in inputArray) {
                        var unit = fullAjaxData[inputArray[i]].metadata.unit
                        if (units.indexOf(unit) == -1) {
                            var formatStr = '%.2f';
                            var labelVal;
                            if (unit == '$') {
                                formatStr = unit + formatStr
                                labelVal = 'Price'
                            } else if (unit == '%') {
                                formatStr = formatStr + unit
                                labelVal = 'Percentage'
                            } else {
                                formatStr += ' ' + unit
                                labelVal = unit
                            }
                            if (units.length == 0) {
                                axes.yaxis = {
                                        tickOptions : {
                                            formatString : formatStr
                                        },
                                        labelRenderer : $.jqplot.CanvasAxisLabelRenderer,
                                        label : labelVal
                                    }
                            } else if (units.length == 1) {
                                axes.yaxis2 = {
                                        tickOptions : {
                                            formatString : formatStr
                                        },
                                        labelRenderer : $.jqplot.CanvasAxisLabelRenderer,
                                        label : labelVal
                                    }
                            }
                            units.push(unit)
                        }
                        var y = 'yaxis';
                        if (units.indexOf(unit) == 1) {
                            y = 'y2axis'
                        }
                        seriesArray.push({
                            showMarker : false,
                            yaxis: y
                        })
                    }
                    plot1 = $.jqplot(
                        'chart1',
                        dataSet,
                        {
                            title : 'Graph',
                            axesDefaults : {
                                tickRenderer : $.jqplot.CanvasAxisTickRenderer,
                                tickOptions : {
                                    angle : -30,
                                    fontSize : '10pt'
                                }
                            },
                            axes : axes,
                            highlighter : {
                                sizeAdjust: 7.5,
                                tooltipLocation: 'nw',
                                tooltipOffset: 10,
                                show : true,
                                tooltipContentEditor: function (str, seriesIndex, pointIndex, plot) {

                                    var date = plot.series[seriesIndex].data[pointIndex][0];
                                    var val = plot.series[seriesIndex].data[pointIndex][1];
                                    var name = nameArray[seriesIndex];

                                    var html = "<div>";
                                    html += name;
                                    html += "<br>";
                                    html += str;
                                    html += "</div>";
                                    return html;
                                }
                            },
                            grid : {
                                background: '#EEEEEE',
                                borderWidth: 0
                            },
                            cursor : {
                                show: true,
                                zoom: true
                            },
                            series : seriesArray,
                            legend: {
                                renderer: $.jqplot.EnhancedLegendRenderer,
                                show: true,
                                labels: nameArray,
                                location: 'nw'
                            }
                        });
                    $('.button-reset').click(function() { plot1.resetZoom() });
                }
            }

            function qtip() {
                refreshQtip()
                var html = []
                html[0] = '<h1>How do I use it?</h1>';
                html[0] += '<p>';
                html[0] += 'Add a new input by selecting an input type, typing an input value, and clicking the "Add" button.';
                html[0] += ' The top input will always be the stock to predict. We predict the stock by shifting it a day and ';
                html[0] += ' using the shifted value to correlate with other inputs.';
                html[0] += ' Added inputs are shown in the "Currently Added Inputs" table and can be removed with the "Remove" button.';
                html[0] += ' You can also clear all inputs by clicking the "Clear" button.';
                html[0] += ' After adding all inputs press the "Submit" button.';
                html[0] +='</p>';

                html[1] = '<h1>What does it show?</h1>';
                html[1] += '<p>';
                html[1] += 'The decision tree shows a way to decide whether to buy, sell, or hold a stock.';
                html[1] += ' The tree should be read as "if A then B, else C". On this page, that generally reads as ';
                html[1] += '"if stock A is > some value, buy; else sell".'
                html[1] +='</p>';

                html[2] = '<h1>What does it mean?</h1>';
                html[2] += '<p>';
                html[2] += 'The decision tree provides a visual representation of an algorithm for choosing how to trade a stock.';
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
    </div>
</body>
</html>
