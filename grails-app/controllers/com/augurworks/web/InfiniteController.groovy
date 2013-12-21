package com.augurworks.web

import grails.converters.JSON
import java.text.SimpleDateFormat;
import grails.plugins.springsecurity.Secured

import org.apache.log4j.Logger;

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
class InfiniteController {

	def springSecurityService
	def infiniteService
	private static final Logger log = Logger.getLogger(GraphsController.class);
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	
	def index() {
		[service : springSecurityService, startDate: lastWeek(), endDate: today(), dataTypes: DataType.findAll { valueType == 'Text' }]
	}
	
	def matrix() {
		[service : springSecurityService, startDate: lastWeek(), endDate: today(), dataTypes: DataType.findAll { valueType == 'Text' }]
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
