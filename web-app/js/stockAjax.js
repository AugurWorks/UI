/*
 * Makes AJAX call to get stock data.
 * REQUIREMENTS:
 * 	Must have a function called stockAjaxComplete(stockAjaxObject) on the page.
 */

function stockAjax(stocks, startDate, endDate, stockFieldId, messageId, url) {
	var stockAjaxObject;
	var resp = $.ajax({
		url : url,
		dataType : 'json',
		data : {
			stock : stocks,
			startDate : startDate,
			endDate : endDate
		},
		success : function(data) {
			stockAjaxObject = setData(data, stockFieldId, messageId)
		},
		error : function(request, status, error) {
			console.log(request)
			alert(error)
		},
		complete : function() {
			stockAjaxComplete(stockAjaxObject)
		}
	});
}

/*
 * Used to set the stock data which is returned through the stockAjaxComplete call.
 * REQUIREMENTS:
 * 	
 */

function setData(data, stockFieldId, messageId) {
	var set = data[0].root
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