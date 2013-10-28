/*
 * Makes AJAX call to get data from the server.
 * REQUIREMENTS:
 * 	Must have a function called ajaxComplete(ajaxObject) on the page.
 */

function ajaxCall(req, url) {
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
		}
	});
}

/*
 * Used to create daily change and change per time-frame start based on a stock set.
 */

function setStockPercentageData(set, stockFieldId, messageId) {
	var listArray1 = []
	var listArray2 = []
	var listArray3 = []
	var invalid = []
	var seriesArray = []
	var stockArray = []
	for (stock in set) {
		seriesArray.push({
			showMarker : false
		})
		var list1 = []
		var list2 = []
		var list3 = []
		var temp = []
		var len = Object.keys(set[stock]).length
		if (set[stock].val != undefined) {
			invalid.push(stock)
		} else {
			$.each(
					set[stock],
					function(index, value) {
						temp.push([index, parseFloat(value) ])
					});
			var counter = 0
			$.each(
					set[stock],
					function(index, value) {
						list1.push([index, parseFloat(value)])
						if (counter > 0) {
							list2.push([index, (temp[counter][1] - temp[counter - 1][1]) / temp[counter - 1][1] * 100])
							list3.push([index, (temp[counter][1] - temp[len - 1][1]) / temp[len - 1][1] * 100])
						}
						counter += 1
					});
			stockArray.push(stock)
			listArray1.push(list1)
			listArray2.push(list2)
			listArray3.push(list3)
		}
	}
	var stockAjaxObject = new Object()
	if (invalid.length == 1) {
		$("#" + messageId).text(invalid[0] + " is invalid.")
		$("#" + messageId).show()
	} else if (invalid.length > 1) {
		$("#" + messageId).text(invalid.join(", ") + " are invalid.")
		$("#" + messageId).show()
	} else {
		$("#" + messageId).hide()
	}
	$("#" + stockFieldId).val(stockArray.join(', '))
	stockAjaxObject.dataSet = [listArray1, listArray2, listArray3]
	stockAjaxObject.stockArray = stockArray
	stockAjaxObject.seriesArray = seriesArray
	return stockAjaxObject
}