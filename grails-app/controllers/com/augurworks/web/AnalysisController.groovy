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
		[inputNum: null, sameSize: false, service : springSecurityService, dataTypes: DataType.list(), startDate: halfYearAgo(), endDate: today()]
	}
	
	def decisiontree() {
		[page: 'decisionTree', inputNum: null, sameSize: true, service : springSecurityService, startDate: halfYearAgo(), endDate: today(), agg: Aggregation.list(), dataTypes: DataType.findAll { valueType == 'Number' }, dataTypeJson: (DataType.findAll { valueType == 'Number' }.sort() as JSON).toString()]
	}
	
	def decisionTreeResults() {
		println params
		render([success: true] as JSON)
	}
	
	private String today() {
		Calendar cal = Calendar.getInstance();
		return DATE_FORMAT.format(cal.getTime());
	}

	private String halfYearAgo() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -182);
		return DATE_FORMAT.format(cal.getTime());
	}
}
