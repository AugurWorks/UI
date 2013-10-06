package com.augurworks.web

class GraphsController {

	def springSecurityService
	def infiniteService
	def getStockService

	def index() {
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
