/*
 * Makes AJAX call to get data from the server.
 * REQUIREMENTS:
 *     Must have a function called ajaxComplete(ajaxObject) on the page.
 */

function ajaxCall(req, url) {
    $.blockUI({
        message: '<div style="padding: 20px;"><img src="/images/Logo.png" style="height: 75px;" /><h1>Loading data...</h1></div>'
    });
    var ajaxObject;
    var resp = $.ajax({
        url : url,
        dataType : 'json',
        data : {
            req : JSON.stringify(req)
        },
        success : function(data) {
            //console.log(req)
            //console.log(data)
            if (!data.root.errorBoolean) {
                for (i in data.root) {
                    if (data.root[i].metadata && data.root[i].metadata.req.custom.length > 0) {
                        try {
                            var temp = new Object()
                            $.each(data.root[i].dates, function(key) { var it = data.root[i].dates[key]; temp[key] = eval(data.root[i].metadata.req.custom) })
                            data.root[i].dates = temp
                        } catch (e) {
                            data.root[i].metadata.errors['Custom'] = e
                        }
                    }
                }
                ajaxObject = data.root
                ajaxComplete(ajaxObject)
            } else {
              console.log('Error:')
              console.log(data.root.error)
            }
        },
        error : function(request, status, error) {
            alert(error)
            $.unblockUI()
        },
        complete : function() {
            $.unblockUI()
        }
    });
}

/*
 * Makes AJAX call to get data from the server.
 * REQUIREMENTS:
 *     Must have a function called ajaxComplete(ajaxObject) on the page.
 */

function analysisCall(req, url) {
    $.blockUI({
        message: '<div style="padding: 20px;"><img src="/images/Logo.png" style="height: 75px;" /><h1>Loading data...</h1></div>'
    });
    var ajaxObject;
    var resp = $.ajax({
        url : url,
        dataType : 'json',
        data : {
            req : JSON.stringify(req)
        },
        success : function(data) {
            //console.log(req)
            //console.log(data)
            if (data.root) {
	            if (!data.root.errorBoolean) {
	                for (i in data.root) {
	                    if (data.root[i].metadata && data.root[i].metadata.req.custom.length > 0) {
	                        try {
	                            var temp = new Object()
	                            $.each(data.root[i].dates, function(key) { var it = data.root[i].dates[key]; temp[key] = eval(data.root[i].metadata.req.custom) })
	                            data.root[i].dates = temp
	                        } catch (e) {
	                            data.root[i].metadata.errors['Custom'] = e
	                        }
	                    }
	                }
	                ajaxObject = data.root
	                ajaxComplete(ajaxObject, data.metadata)
	                if (data.invalidInputs.length > 0) {
	                	$('#invalidMessage').html('Invalid Inputs: ' + data.invalidInputs.join(', '))
	                	$('#invalidMessage').show()
	                } else {
	                	$('#invalidMessage').hide()
	                }
	            }
            } else {
	            if (!data.errorBoolean) {
	                ajaxComplete(data)
	                $('#invalidMessage').hide()
	            } else {
	              $('#invalidMessage').html(data.error)
	              $('#invalidMessage').show()
	            }
            }
        },
        error : function(request, status, error) {
            alert(error)
            $.unblockUI()
        },
        complete : function() {
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
                var keys = Object.keys(set[input].dates).sort()
                for (i in keys) {
                    list.push([keys[i], parseFloat(set[input].dates[keys[i]])])
                }
                inputArray.push(input)
                nameArray.push(set[input].metadata.req.name + ' - ' + set[input].metadata.req.dataType)
                listArray.push(list)
            } catch (e) {
                console.log('An error occured.')
                console.log(e)
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
    /*var day = s.getDay()
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
    offset += add;*/
    var n = new Date(s.getFullYear(), s.getMonth(), s.getDate() + offset);
    return n.getMonth() + 1 + "/" + n.getDate() + "/" + n.getFullYear()
}

function tickerRequest(query, url) {
    $.blockUI({
        message: '<div style="padding: 20px;"><img src="/images/Logo.png" style="height: 75px;" /><h1>Loading tickers...</h1></div>'
    });
    var tickerResults;
    var resp = $.ajax({
        url : url,
        dataType : 'json',
        data : {
            query : query
        },
        success : function(result) {
            tickerResults = result.root
            //console.log(result)
        },
        error : function(request, status, error) {
            console.log(request)
            alert(error)
        },
        complete : function() {
            resultsDone(tickerResults, query)
            $.unblockUI()
        }
    });
}

//Adds a query to the request object and redraws the table
function add(name, dataType, agg, start, end, predictedDays, url, off, custom, page) {
    counter += 1;
    if (predictedDays && Object.keys(req).length != 0) {
    	var temp = new Date(end);
    	temp.setTime(temp.getTime() + predictedDays * 3600 * 24 * 1000)
    	end = (temp.getMonth() + 1) + '/' + temp.getDate() + '/' + temp.getFullYear()
    }
    var cur = {name: name.toUpperCase(), dataType: dataType, agg: agg, startDate: start, endDate: end, custom: custom, page: page, reqId: -1};
    if (!isDuplicate(req, cur)) {
	    tempReq[counter] = cur;
	    /*if (dataType == 'Stock Price' || dataType == 'Stock Day Change' || dataType == 'Stock Period Change') {
	        tickerRequest(name, url);
	    }*/
	    if (!isNaN(off)) {
	        if (Object.keys(req).length == 0) {
	            tempReq[counter].offset = 0;
	            $('#start').hide();
	            $('#off').show();
	        } else {
	            tempReq[counter] = {name: name, dataType: dataType, agg: agg, startDate: calcNewDate(start, parseInt(off)), endDate: calcNewDate(end, parseInt(off)), offset: off, custom: custom, page: page, reqId: -1};
	        }
	    }
	    //if (dataType != 'Stock Price' && dataType != 'Stock Day Change' && dataType != 'Stock Period Change') {
	        if (single) {
	            req = new Object()
	            req[counter] = tempReq[counter]
	            validate()
	        } else {
	            req[counter] = tempReq[counter]
	            drawTable();
	        }
	        refreshQtip()
	    //}
    }
}

function isDuplicate(req, cur) {
	var dup = false;
	Object.keys(req).forEach(function(k) {
		var me = req[k];
		dup = dup || (me.name == cur.name && me.dataType == cur.dataType && me.agg == cur.agg && me.startDate == cur.startDate && me.endDate == cur.endDate && me.custom == cur.custom && me.page == cur.page);
	})
	return dup;
}

function resultsDone(results, query) {
    var html = '';
    var isTicker = false;
    var longName;
    if (results.length == 0) {

    } else {
        html += '<br></br><h3>Results</h3><table><tr><th>Symbol</th><th>Name</th><th>Exchange</th><th>Select</th></tr>';
        for (i in results) {
            html += '<tr><td>' + results[i].symbol + '</td><td>' + results[i].name + '</td><td>' + results[i].exch + '</td><td><button class="buttons" onclick="selectResult(\'' + results[i].symbol + '\', \'' + results[i].name + '\')">Select</button></td></tr>';
            if (results[i].symbol.toUpperCase() == query.toUpperCase()) {
                isTicker = true
                longName = results[i].name
            }
        }
        html += '</table>';
    }
    if (!isTicker) {
        $('#results').html(html);
    } else {
        if (single) {
            req = new Object()
            req[counter] = tempReq[counter]
            req[counter].longName = longName
            validate()
        } else {
            req[counter] = tempReq[counter]
            req[counter].longName = longName
            drawTable()
        }
    }
    refreshQtip()
}

function selectResult(symbol, longName) {
    req[counter] = {name: symbol, dataType: tempReq[counter].dataType, startDate: tempReq[counter].startDate, endDate: tempReq[counter].endDate, longName: longName};
    if (tempReq[counter].offset) {
        req[counter].offset = tempReq[counter].offset
    }
    $('#results').html('');
    if (single) {
        validate()
        for (i in Object.keys(req)) {
            if (counter != i) {
                delete req[i]
            }
        }
    } else {
        drawTable();
    }
}

function removeReq(i) {
    delete req[i]
    drawTable()
}

$(document).keypress(function(e){
    if (e.which == 13){
        $("#submit").click();
    }
});

function changeInput(name, inputDiv, inputId, defaultVal, dataTypes) {
    var val = $(name).val()
    if (val == 'Sentiment') {
    	$('#agg').val('Day Change');
    } else {
    	$('#agg').val('Day Percent Change');
    }
    $('#agg').trigger("chosen:updated");
    var obj = $.grep(dataTypes, function(d) { return d.name == val })[0]
    if (obj.dataChoices.length > 0) {
        var html = '<select name="' + inputId + '" id="' + inputId + '">';
        for (i in obj.dataChoices.sort(function(a, b) { return a.name > b.name})) {
            html += '<option value="' + obj.dataChoices[i].name + '">' + obj.dataChoices[i].name + '</option>';
        }
        html += '</select>';
        $(inputDiv).html(html)
        $('#' + inputId).chosen({
    		inherit_select_classes: true,
    		placeholder_text: 'Select'
    	})
    } else {
        $(inputDiv).html('<input type="text" style="width: 90px;" name="' + inputId + '" value="' + defaultVal + '" id="' + inputId + '">')
    }
}

/**
 * Initializes all new qtips.
 */
function refreshQtip() {
    $('.hasQtip').each(function() {
        var me = $(this).qtip({
        	prerender: true,
            style: {
                classes: 'qtip-blue qtip-rounded',
                width: '300px'
            },
            content: {
                text: $(this).next('.hidden')
            },
            hide: {
                fixed: true
            },
            position: {
                my: 'bottom center',
                at: 'top center',
                adjust: {
                    mouse: true
                }
            }
        })
        if (!$(this).next('.hidden').hasClass('rollOver')) {
			me.addClass('qtipToggle')
		}
        $(this).removeClass('hasQtip')
    });
}