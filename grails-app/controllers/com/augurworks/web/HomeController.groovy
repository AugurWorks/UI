package com.augurworks.web

import grails.plugins.springsecurity.Secured
import grails.converters.JSON

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
	
	def feed() {
		[requests: Request.list(max: 10, sort: 'requestDate', order: 'desc'), service : springSecurityService]
	}
}
