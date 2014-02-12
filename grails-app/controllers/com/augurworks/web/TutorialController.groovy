package com.augurworks.web

class TutorialController {

	def springSecurityService
	
    def index() {
		[service : springSecurityService]
	}
	
    def tutorial() {
		[service : springSecurityService]
	}
}
