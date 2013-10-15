package com.augurworks.web

import java.text.SimpleDateFormat

import org.apache.log4j.Logger


class GraphsController {

	def springSecurityService
	def getStockService
	private static final Logger log = Logger.getLogger(GraphsController.class);
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

	def index() {
		def stock, startDate, endDate;
		if (!validateDates(params.startDate, params.endDate)) {
			flash.message = "Dates were invalid. Defaulting the last year.";
			startDate = lastYear();
			endDate = today();
		} else {
			startDate = formatDate(params.startDate);
			endDate = formatDate(params.endDate);
		}
		if (params.stock) {
			stock = params.stock
		} else {
			stock = 'USO'
			flash.message = "Please search for a stock. Currently displaying USO."
		}
		[service : springSecurityService, stocks : getStockService, stock : stock, startDate: startDate, endDate: endDate]
	}

	private String formatDate(String date) {
		Date d = new Date(date);
		return DATE_FORMAT.format(d);
	}

	private boolean validateDates(String startDate, String endDate) {
		try {
			Date start = new Date(startDate);
			Date end = new Date(endDate);
			if (start.after(end)) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private String today() {
		Calendar cal = Calendar.getInstance();
		return DATE_FORMAT.format(cal.getTime());
	}
	
	private String lastYear() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -365);
		return DATE_FORMAT.format(cal.getTime());
	}
}
