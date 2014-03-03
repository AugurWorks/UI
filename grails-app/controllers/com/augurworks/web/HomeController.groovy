package com.augurworks.web

import grails.plugins.springsecurity.Secured
import grails.converters.JSON
import groovy.time.TimeCategory

class HomeController {
	
	def springSecurityService
	def twitterService
	def QuandlService
	
    def index() {
		[service : springSecurityService]
	}
	
    def landing() {
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
	
	def getFeed() {
		try {
			def date = Request.findById(params.latest.toInteger()).requestDate
			def newFeed = Request.findAllByRequestDateGreaterThan(date)
			String deep = ''
			JSON.use('deep') {
				deep = [data: newFeed, success: true] as JSON
			}
			
			render(deep)
		} catch (e) {
			render([data: null, success: false, error: e] as JSON)
		}
	}
}
