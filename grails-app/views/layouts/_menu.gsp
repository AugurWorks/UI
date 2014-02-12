<div class='errors' id="invalidMessage" style="display: none;"></div>
<div class="inputs">
	<table style="margin: none; display: inline;">
		<g:if test="${ inputNum != 2 }">
			<tr>
				<td>Select Input Type:</td>
				<td class="hasQtip"><g:select name="input1" from="${ dataTypes }" optionKey="name" /></td>
				<td class="hidden"><p>Select a type of data to plot.</p><a href="/tutorial/index#inputType">More Info</a></td>
				<td>Input Value:</td>
				<td id="inputDiv2" class="hasQtip"><g:textField type="text" name="input2" value="USO" /></td>
				<td class="hidden"><p>Input a value such as USO or Tesla for a stock or Oil for sentiment.</p><a href="/tutorial/index#inputValue">More Info</a></td>
			</tr>
			<tr id="start">
				<td>Start Date:</td>
				<td><g:textField style="width: 90px;" type="text" id="startDate" name="startDate" value="${startDate}" /></td>
				<td>End Date:</td>
				<td><g:textField style="width: 90px;" type="text" id="endDate" name="endDate" value="${endDate}" /></td>
			</tr>
			<g:if test="${ sameSize }">
				<tr id="off">
					<td>Offset:</td>
					<td class="hasQtip"><input style="width: 60px;" type="number" id="offset" name="offset" value="0" /></td>
					<td class="hidden"><p>Input a number of business days for this set to be offset from the initial dataset date range.</p><a href="/tutorial/index#offset">More Info</a></td>
				</tr>
			</g:if>
			<tr id="advanced" style="display: none;">
				<td>Day Value:</td>
				<td id="inputDiv3" class="hasQtip"><g:select name="agg" from="${ agg }" optionKey="name" /></td>
				<td class="hidden"><p>Select how to aggregate the data.</p><a href="/tutorial/index#dayValue">More Info</a></td>
				<td>Custom:</td>
				<td class="hasQtip"><g:textArea name="custom" value="${ custom }" rows="2" cols="40" /></td>
				<td class="hidden"><p>Add a custom transformation in JavaScript where 'it' is the value of each datapoint, e.g. 'it * 2' would create a dataset where each value is doubled.</p><a href="/tutorial/index#custom">More Info</a></td>
			</tr>
		</g:if>
		<g:else>
			<tr>
				<td>Input 1:</td>
				<td class="hasQtip"><g:select name="input1" from="${ dataTypes }" optionKey="name" /></td>
				<td class="hidden"><p>Input type for the x-axis.</p><a href="/tutorial/index#inputValue">More Info</a></td>
				<td>Input 2:</td>
				<td class="qtip"><g:select name="input3" from="${ dataTypes }" optionKey="name" /></td>
				<td class="hidden"><p>Input type for the y-axis.</p><a href="/tutorial/index#inputValue">More Info</a></td>
			</tr>
			<tr>
				<td>Value:</td>
				<td><g:textField style="width: 90px;" type="text" name="input2" value="USO" /></td>
				<td>Value:</td>
				<td><g:textField style="width: 90px;" type="text" name="input4" value="DJIA" /></td>
			</tr>
			<tr id="advanced" style="display: none;">
				<td>Day Value:</td>
				<td id="inputDiv5" class="hasQtip"><g:select name="agg1" from="${ agg }" optionKey="name" /></td>
				<td class="hidden"><p>Select how to aggregate the data.</p><a href="/tutorial/index#dayValue">More Info</a></td>
				<td>Day Value:</td>
				<td id="inputDiv6" class="hasQtip"><g:select name="agg2" from="${ agg }" optionKey="name" /></td>
				<td class="hidden"><p>Select how to aggregate the data.</p><a href="/tutorial/index#dayValue">More Info</a></td>
			</tr>
			<tr id="advanced1" style="display: none;">
				<td>Custom 1:</td>
				<td class="hasQtip"><g:textArea name="custom1" value="${custom}" rows="2" cols="40" /></td>
				<td class="hidden"><p>Add a custom transformation in JavaScript where 'it' is the value of each datapoint, e.g. 'it * 2' would create a dataset where each value is doubled.</p><a href="/tutorial/index#custom">More Info</a></td>
				<td>Custom 2:</td>
				<td class="hasQtip"><g:textArea name="custom2" value="${custom}" rows="2" cols="40" /></td>
				<td class="hidden"><p>Add a custom transformation in JavaScript where 'it' is the value of each datapoint, e.g. 'it * 2' would create a dataset where each value is doubled.</p><a href="/tutorial/index#custom">More Info</a></td>
			</tr>
			<tr>
				<td>Start date:</td>
				<td><g:textField style="width: 90px;" type="text" id="startDate" name="startDate" value="${startDate}" /></td>
				<td>End date:</td>
				<td><g:textField style="width: 90px;" type="text" id="endDate" name="endDate" value="${endDate}" /></td>
			</tr>
			<tr>
				<td>Offset:</td>
				<td class="hasQtip"><input style="width: 60px;" type="number" id="offset" name="offset" value="0" /></td>
				<td class="hidden"><p>Input a number of business days for this set to be offset from the initial dataset date range.</p><a href="/tutorial/index#offset">More Info</a></td>
			</tr>
		</g:else>
	</table>
	<g:if test="${ !inputNum }">
			<button class="buttons" onclick="add($('#input2').val(), $('#input1').val(), $('#agg').val(), $('#startDate').val(), $('#endDate').val(), getTickerUrl, $('#offset').val(), $('#custom').val())">Add Input</button>
			<button class="buttons" style="background-color: orange;" onclick="clearTable()">Clear Inputs</button>
	</g:if>
	<button class="buttons" onclick="toggleAdvanced()">Toggle Advanced</button>
</div>
<g:if test="${ !inputNum }">
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

	function toggleAdvanced() {
		$('#advanced').css('display') == 'none' ? $('#advanced').show() : $('#advanced').hide();
		<g:if test="${ inputNum == 2 }">
			$('#advanced1').css('display') == 'none' ? $('#advanced1').show() : $('#advanced1').hide();
		</g:if>
	}
	
	// Draws a table showing current requests
	function drawTable() {
		var text = "<table><tr><th>Name</th><th>Data Type</th><th>Aggregation</th><th>Start Date</th><th>End Date</th><th>Custom</th><g:if test="${ sameSize }"><th>Offset</th></g:if><th></th></tr>"
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
			text += '</td><td><div' + (req[i].custom.length != 0 ? ' class="hasQtip">Roll Over' : '>None') + '</div><div class="hidden">';
			text += req[i].custom + '</div>';
			<g:if test="${ sameSize }">
			text += '</td><td>'
			text += req[i].offset
			</g:if>
			text += '</td><td><image src="${resource(dir: 'images', file: 'delete.png')}" class="remove" onclick="removeReq(' + i + ')" /></td></tr>'
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