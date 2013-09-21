package com.augurworks.web

class GraphsController {

	def springSecurityService
	def infiniteService

	def index() {
		System.out.println "MySQL"
		
		[service : springSecurityService, infinite : infiniteService]
	}
}
	