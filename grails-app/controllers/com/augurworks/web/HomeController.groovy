package com.augurworks.web

class HomeController {
	
	def springSecurityService

    def index() {
		
		[service : springSecurityService]
	}
}
