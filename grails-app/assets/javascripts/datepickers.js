function setDatePickers() {
	$(function() {
		//workaround to display current day data for news and events. 
		//$("#startDate").datepicker({maxDate: -2, onSelect: setDateLimits});
			$("#startDate").datepicker({maxDate: 0, onSelect: setDateLimits});
		});
	
	$(function() {
	    	$("#endDate").datepicker({maxDate: +1, onSelect: setDateLimits});
	  	});
}
function setDateLimits(date, inst) {
	var d = new Date(date)
	if (inst.id == "endDate") {
		//Allow user to select the same start and end date to return data for one day.
		//var n = new Date(d.getFullYear(), d.getMonth(), d.getDate() - 2);
		var n = new Date(d.getFullYear(), d.getMonth(), d.getDate() - 0);
		$("#startDate").datepicker("option", "maxDate", n.getMonth() + 1 + "/" + n.getDate() + "/" + n.getFullYear())
	} else {
		//var n = new Date(d.getFullYear(), d.getMonth(), d.getDate() + 2);
		var n = new Date(d.getFullYear(), d.getMonth(), d.getDate() + 0);
		$("#endDate").datepicker("option", "minDate", n.getMonth() + 1 + "/" + n.getDate() + "/" + n.getFullYear())
	}
}