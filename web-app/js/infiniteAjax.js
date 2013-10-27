function infiniteAjax(keyword, startDate, endDate, keywordId, url) {
	var infiniteAjaxObject;
	var resp = $.ajax({
		url: url,
		dataType: 'json',
	    data: {
		    keyword : keyword,
	        startDate : startDate,
	        endDate : endDate
	    },
	    success: function(data) {
	    	infiniteAjaxObject = setData(data, keywordId)
	    },
	    error: function(request, status, error) {
		    console.log(request)
	        alert(error)
	    },
	    complete: function() {
		    infiniteAjaxComplete(infiniteAjaxObject)
	    }
	});
}

function setData(data, keywordId) {
	var infiniteAjaxObject = new Object()
	infiniteAjaxObject.dataSet = data.data
	return infiniteAjaxObject
}