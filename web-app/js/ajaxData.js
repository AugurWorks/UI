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
			//console.log(ajaxObject)
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
		if (set[input].metadata.valid) {
			var len = Object.keys(set[input].dates).length
			try {
				$.each(
						set[input].dates,
						function(index, value) {
							list.push([index, parseFloat(value) ])
						});
				inputArray.push(input)
				nameArray.push(set[input].metadata.req.name + ' - ' + set[input].metadata.req.dataType)
				listArray.push(list)
			} catch (e) {
				console.log('An error occured.')
			}
		}
		else {
			invalid.push(set[input].metadata.req.name);
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
 * Calculates the correlation and covariance between two sets.
 */

function calcCorrelation(data, val1, val2) {
	var first = [];
	var second = [];
	for (k in data[val1]['dates']) {
		first.push(parseFloat(data[val1]['dates'][k]));
	}
	for (k in data[val2]['dates']) {
		second.push(parseFloat(data[val2]['dates'][k]));
	}
	if (first.length < second.length) {
		second = second.slice(second.length - first.length);
	} else if (first.length > second.length) {
		first = first.slice(first.length - second.length);
	}
	return [$.corr_coeff(first, second), $.covariance(first, second)]
}

/*
 * Calculates the offset from a day.
 */

function calcNewDate(date, offset) {
	var s = new Date(date);
	var day = s.getDay()
	var add = 0;
	if ((day + offset % 5 + 7) % 7 == 0) {
		if (offset % 5 < 0) {
			add -= 2;
		} else if (offset % 5 > 0) {
			add += 1;
		}
	} else if ((day + offset % 5 + 7) % 7 == 6) {
		if (offset % 5 < 0) {
			add -= 1;
		} else if (offset % 5 > 0) {
			add += 2;
		}
	}
	offset += 2 * ((offset - offset % 5) / 5);
	offset += add;
	var n = new Date(s.getFullYear(), s.getMonth(), s.getDate() + offset);
	return n.getMonth() + 1 + "/" + n.getDate() + "/" + n.getFullYear()
}