package com.augurworks.web

import grails.plugins.springsecurity.Secured
import grails.converters.JSON
import groovy.time.TimeCategory

class HomeController {
	
	def springSecurityService
	
    def index() {
		[service : springSecurityService]
	}
	
    def landing() {
		def members = TeamMember.findAll().reverse()
		[service : springSecurityService, members: members]
	}
	
	def landingData(String ticker) {
		def data = new DataController()
		def req = [0:[reqId:'1', startDate: data.daysAgo(-700), dataType:'Stock Price', page:'index', agg:'Day Value', name:ticker, endDate: data.daysAgo(0), offset:'0', custom:'']]
		def result = data.getData(req).root[0]
		if (!result.dates) {
			req[0].name = 'DJIA'
			result = data.getData(req).root[0]
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
