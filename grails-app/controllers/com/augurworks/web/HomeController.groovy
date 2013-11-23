package com.augurworks.web
import grails.plugins.springsecurity.Secured

class HomeController {
	
	def springSecurityService
	
    def index() {
		[service : springSecurityService]
	}
	
    def about() {
		def members = TeamMember.findAll()
		[service : springSecurityService, members : members]
	}
	
	@Secured(['ROLE_ADMIN'])
	def controllers() {
		[service : springSecurityService]
	}
}
