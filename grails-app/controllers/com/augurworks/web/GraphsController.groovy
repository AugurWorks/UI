package com.augurworks.web

import java.text.SimpleDateFormat

import org.apache.log4j.Logger


class GraphsController {

	def springSecurityService
	def infiniteService
	def getStockService
	private static final Logger log = Logger.getLogger(GraphsController.class);
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

	def index() {
		def keyword, startDate, endDate, reply;
		if (!validateKeyword(params.keyword)) {
			flash.message = "Empty keyword. Defaulting to oil.";
			keyword = "oil";
		} else {
			keyword = params.keyword;
		}
		if (!validateDates(params.startDate, params.endDate)) {
			flash.message = "Dates were invalid. Defaulting to today.";
			startDate = yesterday();
			endDate = today();
		} else {
			startDate = formatDate(params.startDate);
			endDate = formatDate(params.endDate);
		}
		reply = infiniteService.queryInfinite(keyword, startDate, endDate);

		[service : springSecurityService, infinite : infiniteService, reply: reply, keyword: keyword,
			startDate: startDate, endDate: endDate]
	}

	private boolean validateKeyword(String keyword) {
		return keyword != null;
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

	private String yesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return DATE_FORMAT.format(cal.getTime());
	}

	def index2() {
		String stockVal = 'GOOG'
		if (params.stock) {
			/*StockTicker stockSearch = StockTicker.findBySymbol(params.stock.toUpperCase())
			if (stockSearch == null) {
				flash.message = "Displaying " + params.stock + "."
			} else {
				flash.message = "Displaying " + stockSearch.symbol + ", " + stockSearch.name + "."
			}*/
			stockVal = params.stock
		} else {
			flash.message = "Please search for a stock. Currently displaying GOOG."
		}
		[service : springSecurityService, infinite : infiniteService, stocks : getStockService, stock : stockVal]
	}
}
