/*
 * Makes AJAX call to get data from the server.
 * REQUIREMENTS:
 * 	Must have a function called ajaxComplete(ajaxObject) on the page.
 */

function ajaxCall(req, url) {
	$.blockUI({ 
        message: '<div style="padding: 20px;"><img src="../images/Logo.png" style="height: 75px;" /><h1>Loading data...</h1></div>'
    });
	var ajaxObject;
	var resp = $.ajax({
		url : url,
		dataType : 'json',
		data : {
			req : JSON.stringify(req)
		},
		success : function(data) {
			ajaxObject = data[0].root
		},
		error : function(request, status, error) {
			console.log(request)
			alert(error)
		},
		complete : function() {
			ajaxComplete(ajaxObject)
			$.unblockUI()
		}
	});
}

/*
 * Used to create daily change and change per time-frame start based on a stock set.
 */

function setPlotData(set, inputFieldId, messageId) {
	var listArray = []
	var invalid = []
	var seriesArray = []
	var inputArray = []
	var nameArray = []
	for (input in set) {
		seriesArray.push({
			showMarker : false
		})
		var list = []
		var len = Object.keys(set[input].dates).length
		if (set[input].val != undefined) {
			invalid.push(input)
		} else {
			$.each(
					set[input].dates,
					function(index, value) {
						list.push([index, parseFloat(value) ])
					});
			inputArray.push(input)
			nameArray.push(set[input].metadata.name + ' - ' + set[input].metadata.dataType)
			listArray.push(list)
		}
	}
	var inputAjaxObject = new Object()
	if (invalid.length == 1) {
		$("#" + messageId).text(invalid[0] + " is invalid.")
		$("#" + messageId).show()
	} else if (invalid.length > 1) {
		$("#" + messageId).text(invalid.join(", ") + " are invalid.")
		$("#" + messageId).show()
	} else {
		$("#" + messageId).hide()
	}
	$("#" + inputFieldId).val(inputArray.join(', '))
	inputAjaxObject.dataSet = listArray
	inputAjaxObject.inputArray = inputArray
	inputAjaxObject.nameArray = nameArray
	inputAjaxObject.seriesArray = seriesArray
	return inputAjaxObject
}

/*
 * Calculates the correlation between two sets.
 */

function calcCorrelation(data, val1, val2) {
	var first = []
	var second = []
	for (k in data[val1]['dates']) {
		first.push(parseFloat(data[val1]['dates'][k]))
	}
	for (k in data[val2]['dates']) {
		second.push(parseFloat(data[val2]['dates'][k]))
	}
	return $.corr_coeff(first, second)
}