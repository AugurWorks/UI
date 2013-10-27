/*
 * Makes AJAX call to get infinite data.
 * REQUIREMENTS:
 * 	Must have a function called infiniteAjaxComplete(infiniteAjaxObject) on the page.
 */

function infiniteAjax(req, keywordId, url) {
	var infiniteAjaxObject;
	console.log(JSON.stringify(req))
	var resp = $.ajax({
		url: url,
		dataType: 'json',
	    data: {
	    	req : JSON.stringify(req)
	    },
	    success: function(data) {
	    	infiniteAjaxObject = setData(data, keywordId)
	    },
	    error: function(request, status, error) {
		    console.log(request)
	        alert(error)
	    },
	    complete: function() {
	    	console.log(infiniteAjaxObject)
		    infiniteAjaxComplete(infiniteAjaxObject)
	    }
	});
}

/*
 * Used to set the infinite data which is returned through the infiniteAjaxComplete call.
 */

function setData(data, keywordId) {
	var infiniteAjaxObject = new Object()
	console.log(data)
	infiniteAjaxObject.dataSet = data.data
	return infiniteAjaxObject
}