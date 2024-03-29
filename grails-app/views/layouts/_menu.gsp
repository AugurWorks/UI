<div class='errors' id="invalidMessage" style="display: none;"></div>
<div class="inputs" style="overflow: visible;">
	<table style="margin: none; display: inline;">
		<g:if test="${ inputNum != 2 }">
			<tr>
				<td>Select Input Type:</td>
				<td class="hasQtip"><g:select name="input1" from="${ dataTypes }" optionKey="name" style="min-width: 120px;" /></td>
				<td class="hidden"><p>Select a type of data to plot.</p><a target="_blank" href="/docs#inputType">More Info</a></td>
				<td>Input Value:</td>
				<td id="inputDiv2" class="hasQtip"><g:textField type="text" name="input2" value="${ !numbers ? 'Oil' : 'USO' }" /></td>
				<td class="hidden"><p>Input a value such as USO or Tesla for a stock or Oil for sentiment.</p><a target="_blank" href="/docs#inputValue">More Info</a></td>
			</tr>
			<g:if test="${ sameSize }">
				<tr id="start" style="display: none;">
			</g:if>
			<g:else>
				<tr id="start">
			</g:else>
				<td>Start Date:</td>
				<td class="hasQtip"><g:textField style="width: 90px;" type="text" name="startDate" value="${startDate}" /></td>
				<td class="hidden"><p>Enter the start date for the data set.</p><a target="_blank" href="/docs#startDate">More Info</a></td>
				<td>End Date:</td>
				<td class="hasQtip"><g:textField style="width: 90px;" type="text" name="endDate" value="${endDate}" /></td>
				<td class="hidden"><p>Enter the start date for the data set.</p><a target="_blank" href="/docs#endDate">More Info</a></td>
			</tr>
			<g:if test="${ sameSize }">
				<tr id="off">
					<td>Predicted Days:</td>
					<td class="hasQtip"><input style="width: 60px;" type="number" id="predictedDays" min="0" value="0" /></td>
					<td class="hidden"><p>The number of days to be predicted upon after the Neural Net finishes training.</p><a target="_blank" href="/docs#predictedDays">More Info</a></td>
				</tr>
			</g:if>
			<tr class="advanced" style="display: none;">
				<td>Day Value:</td>
				<td id="inputDiv3" class="hasQtip"><g:select name="agg" from="${ agg }" optionKey="name" value="Day Percent Change" /></td>
				<td class="hidden"><p>Select how to aggregate the data.</p><a target="_blank" href="/docs#dayValue">More Info</a></td>
				<td>Custom:</td>
				<td class="hasQtip"><g:textArea name="custom" value="${ custom }" rows="2" cols="40" /></td>
				<td class="hidden"><p>Add a custom transformation in JavaScript where 'it' is the value of each datapoint, e.g. 'it * 2' would create a dataset where each value is doubled.</p><a target="_blank" href="/docs#custom">More Info</a></td>
			</tr>
			<g:if test="${ page == 'decisionTree' }">
				<tr class="advanced" style="display: none;">
					<td>Tree Depth:</td>
					<td class="hasQtip"><input style="width: 40px;" type="number" name="depth" id="depth" value="3" min="1" /></td>
					<td class="hidden"><p>The decision tree will have this depth meaning there will be potentially 2^(depth + 1) leaf nodes..</p><a target="_blank" href="/docs#treeDepth">More Info</a></td>
				</tr>
			</g:if>
			<g:if test="${ page == 'neuralNet' }">
				<tr class="advanced" style="display: none;">
					<td>Neural Net Depth:</td>
					<td class="hasQtip"><input style="width: 40px;" type="number" id="depth" value="4" min="1" /></td>
					<td class="hidden"><p>The number of layers between inputs and outputs in the neural net.</p><a target="_blank" href="/docs#netDepth">More Info</a></td>
					<td>Learning Constant:</td>
					<td class="hasQtip"><input style="width: 40px;" type="number" id="learning" value=".1" min="0" max="1" /></td>
					<td class="hidden"><p>The constant training updates are multiplied by when updating the net. Higher values mean faster, but less accurate training.</p><a target="_blank" href="/docs#netConstant">More Info</a></td>
				</tr>
				<tr class="advanced" style="display: none;">
					<td>Training Rounds:</td>
					<td class="hasQtip"><input style="width: 60px;" type="number" id="rounds" value="2500" min="0" /></td>
					<td class="hidden"><p>The maximum number of times the neural net will train using the training set.</p><a target="_blank" href="/docs#netRounds">More Info</a></td>
					<td>Accuracy Cutoff:</td>
					<td class="hasQtip"><input style="width: 40px;" type="number" id="cutoff" value=".01" min="0" /></td>
					<td class="hidden"><p>The accuracy level to cutoff training at.</p><a target="_blank" href="/docs#netCutoff">More Info</a></td>
				</tr>
				<!-- <tr class="advanced" style="display: none;">
					<td>Offset:</td>
					<td class="hasQtip"><input style="width: 60px;" type="number" id="offset" name="offset" value="0" /></td>
					<td class="hidden"><p>Input a number of business days for this set to be offset from the initial dataset date range.</p><a target="_blank" href="/docs#offset">More Info</a></td>
				</tr> -->
			</g:if>
		</g:if>
		<g:else>
			<tr>
				<td>Input 1:</td>
				<td class="hasQtip"><g:select name="input1" from="${ dataTypes }" optionKey="name" /></td>
				<td class="hidden"><p>Input type for the x-axis.</p><a target="_blank" href="/docs#inputValue">More Info</a></td>
				<td>Input 2:</td>
				<td class="hasQtip"><g:select name="input3" from="${ dataTypes }" optionKey="name" /></td>
				<td class="hidden"><p>Input type for the y-axis.</p><a target="_blank" href="/docs#inputValue">More Info</a></td>
			</tr>
			<tr>
				<td>Value:</td>
				<td id="inputDiv2"><g:textField style="width: 90px;" type="text" name="input2" value="USO" /></td>
				<td>Value:</td>
				<td id="inputDiv4"><g:textField style="width: 90px;" type="text" name="input4" value="DJIA" /></td>
			</tr>
			<tr class="advanced" style="display: none;">
				<td>Day Value:</td>
				<td id="inputDiv5" class="hasQtip"><g:select name="agg1" from="${ agg }" optionKey="name" value="Normalized Value" /></td>
				<td class="hidden"><p>Select how to aggregate the data.</p><a target="_blank" href="/docs#dayValue">More Info</a></td>
				<td>Day Value:</td>
				<td id="inputDiv6" class="hasQtip"><g:select name="agg2" from="${ agg }" optionKey="name" value="Normalized Value" /></td>
				<td class="hidden"><p>Select how to aggregate the data.</p><a target="_blank" href="/docs#dayValue">More Info</a></td>
			</tr>
			<tr class="advanced" style="display: none;">
				<td>Custom 1:</td>
				<td class="hasQtip"><g:textArea name="custom1" value="${custom}" rows="2" cols="40" /></td>
				<td class="hidden"><p>Add a custom transformation in JavaScript where 'it' is the value of each datapoint, e.g. 'it * 2' would create a dataset where each value is doubled.</p><a target="_blank" href="/docs#custom">More Info</a></td>
				<td>Custom 2:</td>
				<td class="hasQtip"><g:textArea name="custom2" value="${custom}" rows="2" cols="40" /></td>
				<td class="hidden"><p>Add a custom transformation in JavaScript where 'it' is the value of each datapoint, e.g. 'it * 2' would create a dataset where each value is doubled.</p><a target="_blank" href="/docs#custom">More Info</a></td>
			</tr>
			<tr>
				<td>Start date:</td>
				<td class="hasQtip"><g:textField style="width: 90px;" type="text" id="startDate" name="startDate" value="${startDate}" /></td>
				<td class="hidden"><p>Enter the start date for the data set.</p><a target="_blank" href="/docs#startDate">More Info</a></td>
				<td>End date:</td>
				<td class="hasQtip"><g:textField style="width: 90px;" type="text" id="endDate" name="endDate" value="${endDate}" /></td>
				<td class="hidden"><p>Enter the end date for the data set.</p><a target="_blank" href="/docs#endDate">More Info</a></td>
			</tr>
			<tr>
				<td>Offset:</td>
				<td class="hasQtip"><input style="width: 60px;" type="number" id="offset" name="offset" value="0" /></td>
				<td class="hidden"><p>Input a number of business days for this set to be offset from the initial dataset date range.</p><a target="_blank" href="/docs#offset">More Info</a></td>
			</tr>
		</g:else>
	</table>
	<g:if test="${ !inputNum }">
		<button class="buttons" onclick="add($('#input2').val(), $('#input1').val(), $('#agg').val(), $('#startDate').val(), $('#endDate').val(), $('#predictedDays').val(), getTickerUrl, $('#predictedDays').val() * -1, $('#custom').val(), page)">Add Input</button>
		<button class="buttons" style="background-color: orange;" onclick="clearTable()">Clear Inputs</button>
	</g:if>
	<sec:ifAnyGranted roles="ROLE_ADMIN">
		<g:if test="${ numbers }">
			<button class="buttons" onclick="toggleAdvanced()">Toggle Advanced</button>
		</g:if>
	</sec:ifAnyGranted>
</div>
<g:if test="${ !inputNum }">
	<div id="results"></div>
</g:if>
<div style="padding-top: 5px;" id="estimate"></div>
<span id="loading" style="float: right; display: none; font-size: 26px;">
	<img src="/assets/loading.gif" />
	Loading graph...
</span>
<h1 style="text-align: center;" id="message"></h1>
<g:if test="${ !inputNum }">
	<h4>Currently Added Inputs</h4>
	<div id="table"></div>
</g:if>
<div class="button-line">
	<button id="submit" class="buttons" style="font-size: large;" onclick="validate()">Submit</button>
	<div style="float: right;">
		Tooltips On:
		<g:field type="checkbox" name="toggleQtip" checked="true" />
	</div>
</div>

<script>
	var initilized = false;
	var req = $.parseJSON("${req}".replace( /\&quot;/g, '"' ))
	var page = req[Object.keys(req)[0]].page;
	var single = ${ inputNum == 1 };
	var dataSet;
	var fullAjaxData;
	var plot1;
	var inputArray = [];
	var nameArray = [];
	var seriesArray = [];
	var tempReq = new Object()
	var getTickerUrl = "${g.createLink(controller:'data', action:'getTicker')}";
	var dataTypes = $.parseJSON("${dataTypeJson}".replace( /\&quot;/g, '"' ));
	var qtips = [];
	var qtipHtml = [];
	
	$(function() {
		$('#input1, #input3').chosen({
			inherit_select_classes: true,
			placeholder_text: 'Select'
		});
		$('.info').qtip({
		    style: {
		    	widget: true,
		    	def: false,
		    	width: '600px'
		    },
		    position: {
	            my: 'bottom center',
	            at: 'top center',
		    	target: $('#1')
	        },
	        content: {
		        text: function() {
			        return qtipHtml[parseInt($(this).attr('id'))];
		        }
	        },
	        hide: {
	        	fixed: true
	        }
		});
	})
	
	//Checks if qtip toggle then turns qtips on/off
	$('#toggleQtip').change(function() {
		if ($(this).is(':checked')) {
			$('.qtipToggle').qtip('enable')
		} else {
			$('.qtipToggle').qtip('disable')
		}
	})
	
	//Clears the request object and redraws the table
	function clearTable() {
		req = new Object();
		drawTable();
		// If data sets are required to be the same size the offset input is hidden and the date inputs are shown.
		<g:if test="${ sameSize }">
			$('#off').hide();
			$('#start').show();
		</g:if>
	}

	// Toggles showing/hiding the advanced features.
	function toggleAdvanced() {
		$('.advanced').css('display') == 'none' ? $('.advanced').show() : $('.advanced').hide();
		$('#agg').chosen({
			inherit_select_classes: true,
			placeholder_text: 'Select'
		})
	}
	
	// Draws a table showing current inputs.
	function drawTable() {
		var text = "<table><tr><th>Name</th><th>Data Type</th><th>Aggregation</th><th>Start Date</th><th>End Date</th><th>Custom</th><g:if test="${ sameSize }"><th>Offset</th></g:if><th></th></tr>"
		for (i in req) {
			if (!isNaN(i)) {
				text += '<tr><td>'
				text += req[i].name
				text += '</td><td>'
				text += req[i].dataType
				text += '</td><td>';
				text += req[i].agg;
				text += '</td><td>'
				text += req[i].startDate
				text += '</td><td>'
				text += req[i].endDate
				text += '</td><td><div' + (req[i].custom.length != 0 ? ' class="hasQtip">Roll Over' : '>None') + '</div><div class="hidden rollOver">';
				text += req[i].custom + '</div>';
				// If sets are required to be the same size the offset value is also shown in the request table.
				<g:if test="${ sameSize }">
					text += '</td><td>'
					text += req[i].offset
				</g:if>
				text += '</td><td><image src="/assets/delete.png" class="remove" onclick="removeReq(' + i + ')" /></td></tr>'
			}
		}
		text += "</table>"
		$('#table').html(text)
	}

	// An input is removed from the request object.
	function removeReq(i) {
		delete req[i];
		// If sets are required to be the same size the date inputs are hidden and offset input is shown after adding the first input.
		<g:if test="${ sameSize }">
			if (Object.keys(req).length == 0) {
				$('#off').hide();
				$('#start').show();
			}
		</g:if>
		<g:if test="${ page == 'neuralNet' }">
			estimate();
		</g:if>
		drawTable();
	}

	// When the input type is changed the input value type is checked to see if it needs to be changed to a text field.
	$('#input1').change(function() {
		changeInput('#input1', '#inputDiv2', 'input2', 'USO', dataTypes)
	})

	// If two inputs are required then a second input check is added.
	<g:if test="${ inputNum == 2 }">
		$('#input3').change(function() {
			changeInput('#input3', '#inputDiv4', 'input4', 'USO', dataTypes)
		})
	</g:if>
	
	// Runs each time the 'Submit' button is clicked. Retrieves data from the server.
	function validate() {
		// If the page requires two inputs the validate function creates a request object composed of both inputs.
		<g:if test="${ inputNum == 2 }">
			var startDate = $('#startDate').val();
			var endDate = $('#endDate').val();
			var offset = parseInt($('#offset').val());
			var req = new Object();
			var name1 = $('#input2').val();
			var name2 = $('#input4').val();
			var custom1 = $('#custom1').val();
			var custom2 = $('#custom2').val();
			req[0] = {dataType: $('#input1').val(), startDate: startDate, endDate: endDate, name: name1, agg: $('#agg1').val(), custom: custom1, page: page, reqId: -1}
			req[1] = {dataType: $('#input3').val(), startDate: calcNewDate(startDate, offset), endDate: calcNewDate(endDate, offset), name: name2, agg: $('#agg2').val(), custom: custom2, page: page, reqId: -1}
		</g:if>
		// If the page requires one input the validate function creates a request object from the inputs.
		<g:if test="${ inputNum == 1 }">
			var startDate = $('#startDate').val();
			var endDate = $('#endDate').val();
			var req = new Object();
			var name = $('#input2').val();
			var custom = $('#custom').val();
			req[0] = {dataType: $('#input1').val(), startDate: startDate, endDate: endDate, name: name, agg: $('#agg').val(), custom: custom, page: page, reqId: -1}
			console.log(req)
		</g:if>
		// The request object is sent to the data controller.
		if (Object.keys(req).length == 0) {
			$('#invalidMessage').html('Please select an input.')
			$('#invalidMessage').show()
		} else {
			$('#invalidMessage').hide()
			<g:if test="${ !analysis }">
				ajaxCall(req, "${g.createLink(controller:'data', action:'ajaxData')}")
			</g:if>
			<g:if test="${ analysis }">
				<g:if test="${ page == 'decisionTree' }">
					delete req['analysis']
					req['analysis'] = {"treeDepth": $('#depth').val(),
				            "cutoff": 0.1,
				            "nameToPredict": req[Object.keys(req)[0]].name,
					        "type": "${ page }"}
				</g:if>
				<g:if test="${ page == 'linearRegression' }">
					delete req['analysis']
					req['analysis'] = {
				            "dependent": (req[Object.keys(req)[0]].name + '-' + Object.keys(req)[0]),
				            "independent": $.map(Object.keys(req).slice(1), function(d) { return (req[d].name + '-' + d) }).join(', '),
					        "type": "${ page }"}
				</g:if>
					<g:if test="${ page == 'neuralNet' }">
					delete req['analysis']
					req['analysis'] = {
				            "dependent": (req[Object.keys(req)[0]].name + '-' + Object.keys(req)[0]),
				            "depth": $('#depth').val(),
				            "learningConstant": $('#learning').val(),
				            "rounds": $('#rounds').val(),
				            "cutoff": $('#cutoff').val(),
					        "type": "${ page }"}
				</g:if>
				analysisCall(req, "${g.createLink(controller:'analysis', action:'analyze')}")
			</g:if>
		}
	}
</script>