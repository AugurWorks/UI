package com.augurworks.web

import org.apache.log4j.Logger


class GraphsController {

	def springSecurityService
	def infiniteService
	def getStockService
	private static final Logger log = Logger.getLogger(GraphsController.class);

	def index() {
		def keyword, startDate, endDate, reply;
		if (params.keyword == null) {
			keyword = "oil";
		} else {
			keyword = params.keyword;
		}
		if (params.startDate == null || params.endDate == null) {
			startDate = "09/09/2013";
			endDate = "09/10/2013";
		} else {
			startDate = params.startDate;
			endDate = params.endDate;
		}
		reply = infiniteService.queryInfinite(params.keyword, params.startDate, params.endDate);

		[service : springSecurityService, infinite : infiniteService, reply: reply, keyword: keyword,
			startDate: startDate, endDate: endDate]
	}

	def index2() {
		String stockVal = 'GOOG'
		if (params.stock) {
			StockTicker stockSearch = StockTicker.findBySymbol(params.stock.toUpperCase())
			if (stockSearch == null) {
				flash.message = "Your stock is not a valid stock, displaying GOOG."
			} else {
				flash.message = "Your stock is " + params.stock + "."
				stockVal = params.stock
			}
		} else {
			flash.message = "Please search for a stock. Currently displaying GOOG."
		}
		[service : springSecurityService, infinite : infiniteService, stocks : getStockService, stock : stockVal]
	}
}
