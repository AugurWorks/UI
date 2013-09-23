package com.augurworks.web

class GraphsController {

	def springSecurityService
	def infiniteService
	def getStockService

	def index() {
		[service : springSecurityService, infinite : infiniteService, stocks : getStockService]
	}
}
	