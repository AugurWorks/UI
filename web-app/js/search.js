$(function() {
	$('#sets').chosen();
	$('#stocks').chosen();
});

function validate() {
	var params = {sets: $('#sets').val(), stocks: $('#stocks').val(), start: $('#start').val(), end: $('#end').val(), offset: $('#offset').val(), agg: $('#agg').val() };
	var errors = [];
	if (!params.sets && !params.stocks) {
		errors.push('No datasets chosen.')
	}
	if (!params.start || new Date(params.start).getTime() > new Date().getTime()) {
		errors.push('Invalid start date.')
	}
	if (!params.end || new Date(params.end).getTime() > new Date().getTime()) {
		errors.push('Invalid end date.')
	}
	if (params.start && params.end && new Date(params.start).getTime() > new Date(params.end).getTime()) {
		errors.push('Start date after end date.')
	}
	if (!params.offset) {
		errors.push('Invalid offset.')
	}
	if (errors.length > 0) {
		$('#errors').show();
		$('#errors').html('<ul><li>' + errors.join('</li><li>') + '</li></ul>');
	} else {
		$('#errors').hide();
		submit(params)
	}
}

function submit(params) {
    var resp = $.ajax({
        url : '/search/submit',
        dataType : 'json',
        data : {
            data : JSON.stringify(params)
        },
        success : function(data) {
           console.log(data)
        },
        error : function(request, status, error) {
            alert(error)
        }
    });
}