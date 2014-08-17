<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Linear Regression</title>
<style>
    .jqplot-table-legend {
        width: auto;
    }
</style>
</head>
<body>
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
            <p>
                A <a href='http://en.wikipedia.org/wiki/Linear_regression'>linear regression</a> is a common way of analyzing data.
                The concept of a linear regression stems from the equation for a line, <i>y = m * x + b</i>. This really means
                <i>output = a * input + b</i> where <i>a</i> and <i>b</i> are both constants. In this case, we've extended the formula
                to be <i>output = a * input_1 + b * input_2 + ... z</i>. We allow as many inputs as you want, and the output will
                always be the top stock.
            </p>
            <p>
                The algorithm used here is based off of a paper published in 1988 by Alan Miller. Feel free to read the
                <a href='http://www.jstor.org/stable/2347583'>paper</a> if you're interested in the details!
            </p>
        </div>
        <br />
        <g:render template="../layouts/menu" />
        <div style="text-align: center; padding: 5px;">
            <div id="metadata"></div>
            <div id="chart1"></div>
        </div>
        <button class="buttons" class="button-reset">Reset</button>
        <g:render template="../layouts/qtip" />
        <script type="text/javascript">
            counter = 5
            $(document).ready(function() {
                setDatePickers();
                drawTable();
                validate();
                qtip();
            });

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
            function ajaxComplete(ajaxData, metadata) {
                console.log(ajaxData)
                $('#metadata').html('R-Squared: ' + parseFloat(metadata.r_squared).toFixed(4) + ', Std Errors: ' +
                        $.map(metadata.parameter_std_error.split(','), function(d) { return parseFloat(d).toFixed(4).toString() }).join(', '))
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
                html[0] += 'Add a new plot line by selecting an input type, typing an input value, and clicking the "Add" button.';
                html[0] += ' Added inputs are shown in the "Currently Added Inputs" table and can be removed with the "Remove" button.';
                html[0] += ' You can also clear all inputs by clicking the "Clear" button.';
                html[0] += ' After adding all inputs press the "Submit" button.';
                html[0] += '<br />';
                html[0] += 'The top-most value will always be the stock predicted.'
                html[0] +='</p>';

                html[1] = '<h1>What does it show?</h1>';
                html[1] += '<p>';
                html[1] += 'The graph shows all of the given inputs and the predicted output series. It also gives you the R squared';
                html[1] += ' value for the regression, which is a simple measure of "goodness". You want this to me as close to 1 as';
                html[1] += ' possible.';
                html[1] +='</p>';

                html[2] = '<h1>What does it mean?</h1>';
                html[2] += '<p>';
                html[2] += 'If the regression closely follows the output stock and the R squared value is close to 1, then you\'ve found';
                html[2] += ' a good formula for predicting your output stock!'
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