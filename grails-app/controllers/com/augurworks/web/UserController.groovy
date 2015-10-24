package com.augurworks.web

class UserController {
	
	def springSecurityService

    def scaffold = true
	
	def settings() {
		[user: User.findByUsername(springSecurityService.authentication.name)]
	}
}
