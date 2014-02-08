<div class='errors' id="invalidMessage" style="display: none;"></div>
<div class="buttons">
	<g:if test="${ inputNum != 2 }">
		<div class="button-line">
			<div style="display: inline;" title="Select a type of data to plot.">
				Select:
				<g:select name="input1" from="${ dataTypes }" optionKey="name" />
			</div>
			<div style="display: inline;" title="Input a value such as USO or Tesla for a stock or Oil for sentiment." id="inputDiv2">
				Input Value:
				<g:textField type="text" name="input2" value="USO" />
			</div>
		</div>
		<div class="button-line">
			<div style="display: inline;" title="Select how to aggregate the data." id="inputDiv3">
				Aggregation:
				<g:select name="agg" from="${ agg }" optionKey="name" />
			</div>
			<div style="display: inline;" title="Add a custom transformation in JavaScript where 'it' is the value of each datapoint, e.g. 'it * 2' would create a dataset where each value is doubled.">
				Custom:
				<g:textArea name="custom" value="${ custom }" rows="2" cols="40" />
			</div>
		</div>
		<div id="start">
			Start date:
			<g:textField style="width: 90px;" type="text" id="startDate" name="startDate" value="${startDate}" />
			End date:
			<g:textField style="width: 90px;" type="text" id="endDate" name="endDate" value="${endDate}" />
		</div>
		<g:if test="${ sameSize }">
			<div id="off" style="display: inline;" title="Input a number of business days for this set to be offset from the initial dataset date range.">
				Offset: <input style="width: 60px;" type="number" id="offset" name="offset" value="0" />
			</div>
		</g:if>
	</g:if>
	<g:else>
		<div class="button-line">
			<div style="display: inline;" title="Input type for the x-axis.">Input 1: <g:select name="input1" from="${ dataTypes }" optionKey="name" /></div>
			<div id="inputDiv2" style="display: inline;">Value: <g:textField style="width: 90px;" type="text" name="input2" value="USO" /></div>
			<div style="display: inline;" title="Select how to aggregate the data." id="inputDiv5">Aggregation: <g:select name="agg1" from="${ agg }" optionKey="name" /></div>
		</div>
		<div class="button-line">
			<div style="display: inline;" title="Input type for the y-axis.">Input 2: <g:select name="input3" from="${ dataTypes }" optionKey="name" /></div>
			<div id="inputDiv4" style="display: inline;">Value: <g:textField style="width: 90px;" type="text" name="input4" value="DJIA" /></div>
			<div style="display: inline;" title="Select how to aggregate the data." id="inputDiv6">Aggregation: <g:select name="agg2" from="${ agg }" optionKey="name" /></div>
		</div>
		<div class="button-line">
			<div style="display: inline;" title="Add a custom transformation in JavaScript where 'it' is the value of each datapoint, e.g. 'it * 2' would create a dataset where each value is doubled.">Custom 1: <g:textArea name="custom1" value="${custom}" rows="2" cols="40" /></div>
			<div style="display: inline;" title="Add a custom transformation in JavaScript where 'it' is the value of each datapoint, e.g. 'it * 2' would create a dataset where each value is doubled.">Custom 2: <g:textArea name="custom2" value="${custom}" rows="2" cols="40" /></div>
		</div>
		<div class="button-line">
			Start date: <g:textField style="width: 90px;" type="text" id="startDate" name="startDate" value="${startDate}" />
			End date: <g:textField style="width: 90px;" type="text" id="endDate" name="endDate" value="${endDate}" />
			<div style="display: inline;" title="Input a number of business days for this set to be offset from the initial dataset date range.">Offset: <input style="width: 60px;" type="number" id="offset" name="offset" value="0" /></div>
		</div>
	</g:else>
</div>
</div>
<g:if test="${ !inputNum }">
	<div class="button-line">
		<button class="buttons" onclick="add($('#input2').val(), $('#input1').val(), $('#agg').val(), $('#startDate').val(), $('#endDate').val(), getTickerUrl, $('#offset').val(), $('#custom').val())">Add</button>
		<button class="buttons" onclick="clearTable()">Clear</button>
	</div>
	<div id="results"></div>
</g:if>
<h1 style="text-align: center;" id="message"></h1>
<g:if test="${ !inputNum }">
	<h4>Currently Added Inputs</h4>
	<div id="table"></div>
</g:if>
<div class="button-line">
	<button id="submit" class="buttons" style="font-size: large;" onclick="validate()">Submit</button>
</div>

<script>
	var initilized = false;
	var single = ${ inputNum == 1 };
	var dataSet;
	var fullAjaxData;
	var plot1;
	var inputArray = [];
	var nameArray = [];
	var seriesArray = [];
	var req = new Object();
	var tempReq = new Object()
	var getTickerUrl = "${g.createLink(controller:'data', action:'getTicker')}";
	var dataTypes = $.parseJSON("${dataTypeJson}".replace( /\&quot;/g, '"' ))
	//Clears the request object and redraws the table
	function clearTable() {
		req = new Object();
		drawTable();
		<g:if test="${ sameSize }">
		$('#off').hide();
		$('#start').show();
		</g:if>
	}
	
	// Draws a table showing current requests
	function drawTable() {
		var text = "<table><tr><th>Name</th><th>Data Type</th><th>Aggregation</th><th>Start Date</th><th>End Date</th><g:if test="${ sameSize }"><th>Custom</th></g:if><th>Offset</th><th>Remove</th></tr>"
		for (i in req) {
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
			text += '</td><td><div title="';
			text += req[i].custom;
			text += '">' + (req[i].custom.length != 0 ? 'Roll Over' : 'None') + '</div>';
			<g:if test="${ sameSize }">
			text += '</td><td>'
			text += req[i].offset
			</g:if>
			text += '</td><td><button class="buttons" onclick="removeReq(' + i + ')">Remove</button></td></tr>'
		}
		text += "</table>"
		$('#table').html(text)
	}
	
	function removeReq(i) {
		delete req[i];
		<g:if test="${ sameSize }">
		if (Object.keys(req).length == 0) {
			$('#off').hide();
			$('#start').show();
		}
		</g:if>
		drawTable();
	}

	$('#input1').change(function() {
		changeInput('#input1', '#inputDiv2', 'input2', 'USO', dataTypes)
	})

	<g:if test="${ inputNum == 2 }">
	$('#input3').change(function() {
		changeInput('#input3', '#inputDiv4', 'input4', 'USO', dataTypes)
	})
	</g:if>
	
	// Runs each time the 'Go!' button is clicked. Retrieves data from the server.
	function validate() {
		<g:if test="${ inputNum == 2 }">
		var startDate = $('#startDate').val();
		var endDate = $('#endDate').val();
		var offset = parseInt($('#offset').val());
		var req = new Object();
		var name1 = $('#input2').val();
		var name2 = $('#input4').val();
		var custom1 = $('#custom1').val();
		var custom2 = $('#custom2').val();
		req[0] = {dataType: $('#input1').val(), startDate: startDate, endDate: endDate, name: name1, agg: $('#agg1').val(), custom: custom1}
		req[1] = {dataType: $('#input3').val(), startDate: calcNewDate(startDate, offset), endDate: calcNewDate(endDate, offset), name: name2, agg: $('#agg2').val(), custom: custom2}
		</g:if>
		ajaxCall(req, "${g.createLink(controller:'data',action:'getData')}")
	}
</script>