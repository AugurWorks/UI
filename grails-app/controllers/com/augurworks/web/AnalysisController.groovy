package com.augurworks.web

class AnalysisController {

	def springSecurityService
	
    def index() {
		[service : springSecurityService]
	}
}
