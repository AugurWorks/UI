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
            <h3>Machine Learning Algorithm</h3>
        </div>
        <br />
        <g:render template="../layouts/menu" />
        <br />
        <div style="text-align: center; padding: 5px;">
            <div id="chart1"></div>
        </div>
        <g:if test="${ data }">
        	<button class="buttons" class="button-reset">Reset Zoom</button>
        </g:if>
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
                html[0] += ' Add a new input by selecting an Input Type, typing an Input Value, and clicking the "Add" button.';
                html[0] += ' The easiest way to begin is select "Stock Price" as the "Input Type", then enter stock tickers in the "Input Value". ';
                html[0] += ' NOTE: The top (first) input will ALWAYS be the stock you want to predict. We predict the stock by shifting it by the number ';
                html[0] += ' of days selected in the "Predicted Days" value. Typically this is set to "1" for all inputs, except for the first of course. ';
                html[0] += ' However, it can be set to any positive value, but keep in mind it is the offset for predictions. The further out, the less accurate ';
                html[0] += ' The last thing to set is the date range. The End Date is the day to predict and must be used in conjunction with the Predicted Days value ';
                html[0] += ' Since this is a learning algorithm, the Start Date indicateds the "training duration" the algorithm uses to predict the stock. ';
                html[0] += ' You will want to test what duration works the best for the stock type and market volatility. Typically, use a shorter duration ';
                html[0] += ' for more volatile markets. ';
                html[0] += ' Added inputs are shown in the "Currently Added Inputs" table and can be removed with the "Remove" button.';
                html[0] += ' You can also clear all inputs by clicking the "Clear" button.';
                html[0] += ' After adding all inputs press the "Submit" button.';
                html[0] +='</p>';

                html[1] = '<h1>What does it show?</h1>';
                html[1] += '<p>';
                html[1] += ' This algorithm uses artificial intelligence techniques to preidct the "stock price direction". ';
                html[1] += ' The graph will show the input values (predictive indicators) and the predicted result. ';
                html[1] += ' The value of the predicted graph shows a way to decide whether to buy, sell, or hold a stock.';
                html[1] += ' The easiest way to read the graph is to "turn off" the input graphs by clicking on the graph legend for each input stock.';
                html[1] +='</p>';

                html[2] = '<h1>What does it mean?</h1>';
                html[2] += '<p>';
                html[2] += ' Look at the predicted graph. If the value of the day you want to predict is positive, the algorithm is predicting ';
                html[2] += ' the stock price will increase. If the graph at the end point (predicted day) is negative, the algorithm predicts ';
                html[2] += ' the stock price will decrease. You will need to determine the threshold predicted value to "hold". For exmaple, ';
                html[2] += ' a predicted "Day % Change" value of +/- 0.5% might indicate a "hold", greater than 0.5% is a "buy" and less than -0.5% is a "sell". ';
                html[2] += ' The actual threshold values must be determined by the type of stock, number of predictive inputs used, and training ';
                html[2] += ' duration (date range). We are working on the silver bullet ';
                    html[2] += '</p>';
                $('.info').qtip({
                    style: {
                        widget: true,
                        def: false,
                        width: '700px'
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
