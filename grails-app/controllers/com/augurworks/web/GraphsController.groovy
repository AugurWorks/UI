package com.augurworks.web

import java.text.SimpleDateFormat
import grails.converters.deep.JSON
import org.apache.log4j.Logger
import grails.plugins.springsecurity.Secured


@Secured(['ROLE_ADMIN', 'ROLE_USER'])
class GraphsController {

	def springSecurityService
	def getStockService
	private static final Logger log = Logger.getLogger(GraphsController.class);
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	
	def index() {
		[service : springSecurityService, startDate: halfYearAgo(), endDate: today(), dataTypes: DataType.findAll { valueType == 'Number' }, dataTypeJson: (DataType.findAll { valueType == 'Number' }.sort() as JSON).toString()]
	}
	
	def calendar() {
		[service : springSecurityService, startDate: halfYearAgo(), endDate: today(), dataTypes: DataType.findAll { valueType == 'Number' }, dataTypeJson: (DataType.findAll { valueType == 'Number' }.sort() as JSON).toString()]
	}
	
	def correlation() {
		[service : springSecurityService, startDate: halfYearAgo(), endDate: today(), dataTypes: DataType.findAll { valueType == 'Number' }, dataTypeJson: (DataType.findAll { valueType == 'Number' }.sort() as JSON).toString()]
	}
	
	def covariance() {
		[service : springSecurityService, startDate: halfYearAgo(), endDate: today(), dataTypes: DataType.findAll { valueType == 'Number' }, dataTypeJson: (DataType.findAll { valueType == 'Number' }.sort() as JSON).toString()]
	}
	
	@Deprecated
	def stockData() {
		def req = JSON.parse(params.req)
		def rawData = [:]
		for (val in req.keySet()) {
			def startDate, endDate;
			if (!validateDates(req[val].startDate, req[val].endDate)) {
				flash.message = "Dates were invalid. Defaulting the last year.";
				startDate = halfYearAgo();
				endDate = today();
			} else {
				startDate = formatDate(req[val].startDate);
				endDate = formatDate(req[val].endDate);
			}
			int startMonth = Integer.parseInt(startDate.substring(0, 2)) - 1
			int startDay = Integer.parseInt(startDate.substring(3, 5))
			int startYear = Integer.parseInt(startDate.substring(6, 10))
			int endMonth = Integer.parseInt(endDate.substring(0, 2)) - 1
			int endDay = Integer.parseInt(endDate.substring(3, 5))
			int endYear = Integer.parseInt(endDate.substring(6, 10))
			rawData << [(val) : getStockService.getStock(val, startMonth, startDay, startYear, endMonth, endDay, endYear, "d")]
		}
		def temp = []
		temp << ["root" : rawData]
		render((temp as JSON).toString())
	}

	@Deprecated
	private String formatDate(String date) {
		Date d = new Date(date);
		return DATE_FORMAT.format(d);
	}

	@Deprecated
	private boolean validateDates(String startDate, String endDate) {
		try {
			Date start = new Date(startDate);
			Date end = new Date(endDate);
			if (start.after(end)) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	private String today() {
		Calendar cal = Calendar.getInstance();
		return DATE_FORMAT.format(cal.getTime());
	}
	
	@Deprecated
	private String lastYear() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -365);
		return DATE_FORMAT.format(cal.getTime());
	}
	
	private String halfYearAgo() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -182);
		return DATE_FORMAT.format(cal.getTime());
	}
}
