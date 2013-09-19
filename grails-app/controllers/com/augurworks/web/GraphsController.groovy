package com.augurworks.web

class GraphsController {

	def springSecurityService

	def index() {
		[service : springSecurityService]
	}
}
	