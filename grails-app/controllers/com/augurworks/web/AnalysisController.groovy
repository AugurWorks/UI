package com.augurworks.web

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import groovy.json.JsonOutput

import java.text.SimpleDateFormat

import org.apache.log4j.Logger

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
class AnalysisController {

	def springSecurityService
	def infiniteService
	private static final Logger log = Logger.getLogger(GraphsController.class);
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	
    def index() {
		def all = [Algorithm: Algorithm.list(), DataType: DataType.list()]
		[service : springSecurityService, dataTypes: DataType.list(), algorithms: Algorithm.list(), allObj: (all as JSON).toString(), startDate: lastWeek(), endDate: today()]
	}
	
	def dtree() {
		[service : springSecurityService, startDate: lastWeek(), endDate: today()]
	}
	
	private String today() {
		Calendar cal = Calendar.getInstance();
		return DATE_FORMAT.format(cal.getTime());
	}

	private String lastWeek() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7);
		return DATE_FORMAT.format(cal.getTime());
	}
}
