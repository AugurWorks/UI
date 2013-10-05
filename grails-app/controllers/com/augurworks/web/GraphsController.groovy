package com.augurworks.web

class GraphsController {

	def springSecurityService
	def infiniteService
	def getStockService

	def index() {
		if (params.stock) {
			if (!getStockService.getStock(params.stock, 0, 1, 2010, 0, 1, 2012, "d")) {
				flash.message = "Your stock doesn't exist2"
			} else {
				flash.message = "Your stock is " + params.stock
				[service : springSecurityService, infinite : infiniteService, stocks : getStockService, stock : params.stock]
			}
		} else {
			flash.message = "Your stock doesn't exist"
			[service : springSecurityService, infinite : infiniteService, stocks : getStockService]
		}
	}
}
