package com.augurworks.web

import grails.plugins.springsecurity.Secured
import grails.converters.JSON
import groovy.time.TimeCategory

class HomeController {
	
	def springSecurityService
	def dataService
	
    def index() {
		[service : springSecurityService]
	}
	
    def landing() {
		def members = TeamMember.findAll().reverse()
		[service : springSecurityService, members: members]
	}
	
	def landingData(String ticker) {
		def req = ['0':[reqId:'1', startDate: dataService.daysAgo(-700), dataType:'Stock Price', page:'index', agg:'Day Value', name:ticker, endDate: dataService.daysAgo(0), offset:'0', custom:'']]
		def result = dataService.getData(req).root['0']
		if (!result.dates) {
			req['0'].name = 'DJIA'
			result = dataService.getData(req).root['0']
		}
		render(result as JSON)
	}
	
	def about() {
		[service : springSecurityService]
	}
	
	def features() {
		[service : springSecurityService]
	}
	
    def team() {
		def members = TeamMember.findAll().reverse()
		[service : springSecurityService, members : members]
	}
	
	def terms() {
		[service : springSecurityService]
	}
	
	@Secured(['ROLE_ADMIN'])
	def controllers() {
		[service : springSecurityService]
	}
	
	def feedback() {
		[service : springSecurityService]
	}
	
	@Secured(['ROLE_ADMIN', 'ROLE_USER'])
	def feed() {
		[requests: Request.list(max: 10, sort: 'requestDate', order: 'desc'), service : springSecurityService]
	}
	
	@Secured(['ROLE_ADMIN', 'ROLE_USER'])
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
