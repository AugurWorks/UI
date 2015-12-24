<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="main">
<title>Decision Tree</title>
<style>
    .jqplot-table-legend {
        width: auto;
    }
    .node circle {
      cursor: pointer;
      fill: #fff;
      stroke: steelblue;
      stroke-width: 1.5px;
    }
    .node text {
      font-size: 15px;
    }
    path.link {
      fill: none;
      stroke: #ccc;
      stroke-width: 1.5px;
    }
</style>
</head>
<body>
    <asset:javascript src="d3.min.js" />
    <asset:javascript src="plots/decisionTree.js" />
    <div id='content' style='padding: 10px;'>
        <div>
            <h3>If-Then-Else Algorithm</h3>
            <!--  Issue  184 - Drew 
            <p>
            This page allows you to generate a <a href="http://en.wikipedia.org/wiki/Decision_tree" target="_blank">decision tree</a> using
            an algorithm similar to the <a href='http://en.wikipedia.org/wiki/ID3_algorithm' target="_blank">ID3</a> algorithm. A decision
            tree is a common tool for making choices. You read it as "if A is true, do B; else, do C". In the case below, the inputs are stock
            prices and the output is a buy / sell / hold estimate.
            </p>
            <br />
            <p>
            In the example below the "true" branch is always the upper branch (and is denoted by T). The stock to buy / sell / hold is the uppermost
            stock. When you hit "submit", our servers calculate a decision tree and try to predict the outputs. The correctness of the tree is
            determined by counting the percent of days which are predicted correctly by the generated model. The correctness should be much
            higher than 50%, because otherwise you're better off with a coin flip!
            </p>
            -->
        </div>
        <br />
        <g:render template="../layouts/menu" />
        <br />
        <div><h4 id="correct">Correctness: N/A</h4></div>
        <div style="text-align: center; padding: 5px;">
            <div id="chart1"></div>
        </div>
        <button class="buttons" class="button-reset">Reset Zoom</button>
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
            }

            // Function runs after AJAX call is completed. Creates additional data sets (daily change, change since start) and replots the graph.
            function ajaxComplete(ajaxData) {
                console.log(ajaxData)
                $('#correct').html("Correctness: " + ajaxData['correctness'] + '%')
                drawTree(ajaxData, $('#chart1').width(), Math.min($(window).height() * .8, $('#chart1').width()))
            }

            function qtip() {
                refreshQtip()
                var html = []
                html[0] = '<h1>How do I use it?</h1>';
                html[0] += '<p>';
                html[0] += 'Add a new input by selecting an input type, typing an input value, and clicking the "Add" button.';
                html[0] += ' For stocks, the input value must be the stock ticker symbol.'; 
                html[0] += ' The top input will always be the stock to predict. We predict the stock by shifting it a day and ';
                html[0] += ' using the shifted value to correlate with other inputs.';
                html[0] += ' Added inputs are shown in the "Currently Added Inputs" table and can be removed with the "Remove" button.';
                html[0] += ' You can also clear all inputs by clicking the "Clear" button.';
                html[0] += ' After adding all inputs press the "Submit" button.';
                html[0] +='</p>';

                html[1] = '<h1>What does it show?</h1>';
                html[1] += '<p>';
                html[1] += 'The decision tree shows a way to decide whether to buy, sell, or hold a stock, where the "true" branch'; 
                html[1] += ' is always the upper branch (and is denoted by T). The tree should be read as "if A then B, else C". On this'; 
                html[1] += ' page, that generally reads as "if stock A is > some value, buy; else sell".';
                html[1] +='</p>';

                html[2] = '<h1>What does it mean?</h1>';
                html[2] += '<p>';
                html[2] += 'The If-Then-Else algorithm provides a deterministic visual representation for choosing how to trade a stock.';
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
