function setDatePickers() {
	$(function() {
	    	$("#startDate").datepicker({maxDate: -2, onSelect: setDateLimits});
		});
	
	$(function() {
	    	$("#endDate").datepicker({maxDate: 0, onSelect: setDateLimits});
	  	});
}
function setDateLimits(date, inst) {
	var d = new Date(date)
	if (inst.id == "endDate") {
		var n = new Date(d.getFullYear(), d.getMonth(), d.getDate() - 2);
		$("#startDate").datepicker("option", "maxDate", n.getMonth() + 1 + "/" + n.getDate() + "/" + n.getFullYear())
	} else {
		var n = new Date(d.getFullYear(), d.getMonth(), d.getDate() + 2);
		$("#endDate").datepicker("option", "minDate", n.getMonth() + 1 + "/" + n.getDate() + "/" + n.getFullYear())
	}
}