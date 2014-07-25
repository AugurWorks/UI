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
</style>
</head>
<body>
    <g:javascript src="d3.min.js" />
    <g:javascript src="plots/neuralNet.js" />
    <div id='content' style='padding: 10px;'>
        <div>
            <h3>What am I looking at?</h3>
        </div>
        <br />
        <g:render template="../layouts/menu" />
        <br />
        <div><h4 id="correct">Correctness: N/A</h4></div>
        <div style="text-align: center; padding: 5px;">
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
                drawNet(null, $('#chart1').width(), Math.min($(window).height() * .8, $('#chart1').width()))
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
            }

            // Function runs after AJAX call is completed. Creates additional data sets (daily change, change since start) and replots the graph.
            function ajaxComplete(ajaxData) {
                console.log(ajaxData)
                $('#correct').html("Correctness: " + ajaxData['correctness'] + '%')
                //drawTree(ajaxData, $('#chart1').width(), Math.min($(window).height() * .8, $('#chart1').width()))
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
