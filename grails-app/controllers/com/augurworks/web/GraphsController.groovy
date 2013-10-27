package com.augurworks.web

import java.text.SimpleDateFormat
import grails.converters.JSON
import org.apache.log4j.Logger


class GraphsController {

	def springSecurityService
	def getStockService
	private static final Logger log = Logger.getLogger(GraphsController.class);
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

	def index() {
		[service : springSecurityService, startDate: halfYearAgo(), endDate: today()]
	}
	
	def stockData() {
		def stockList, startDate, endDate;
		startDate
		if (!validateDates(params.startDate, params.endDate)) {
			flash.message = "Dates were invalid. Defaulting the last year.";
			startDate = halfYearAgo();
			endDate = today();
		} else {
			startDate = formatDate(params.startDate);
			endDate = formatDate(params.endDate);
		}
		if (params.stock) {
			stockList = params.stock.replace(' ', '').split(',')
		} else {
			stockList = ['USO', 'DJIA']
			flash.message = "Please search for a stock. Separate multiple stocks with a comma. Currently displaying USO and DJIA."
		}
		int startMonth = Integer.parseInt(startDate.substring(0, 2)) - 1
		int startDay = Integer.parseInt(startDate.substring(3, 5))
		int startYear = Integer.parseInt(startDate.substring(6, 10))
		int endMonth = Integer.parseInt(endDate.substring(0, 2)) - 1
		int endDay = Integer.parseInt(endDate.substring(3, 5))
		int endYear = Integer.parseInt(endDate.substring(6, 10))
		def rawData = [:]
		for (stk in stockList) {
			rawData << [(stk) : getStockService.getStock(stk, startMonth, startDay, startYear, endMonth, endDay, endYear, "d")]
		}
		def temp = []
		temp << ["root" : rawData]
		render((temp as JSON).toString())
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
	
	private String halfYearAgo() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -182);
		return DATE_FORMAT.format(cal.getTime());
	}
}
