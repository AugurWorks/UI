package com.augurworks.web
import grails.plugins.springsecurity.Secured

class HomeController {
	
	def springSecurityService
	def twitterService
	
	def QuandlService
	
    def index() {
		[service : springSecurityService]
	}
	
	def about() {
		[service : springSecurityService]
	}
	
    def team() {
		def members = TeamMember.findAll()
		[service : springSecurityService, members : members]
	}
	
	@Secured(['ROLE_ADMIN'])
	def controllers() {
		[service : springSecurityService]
	}
	
	def feedback() {
		[service : springSecurityService]
	}
}
