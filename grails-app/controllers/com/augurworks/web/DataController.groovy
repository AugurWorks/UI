package com.augurworks.web

import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;
import grails.converters.JSON;
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
class DataController {
	
	def getStockService
	def infiniteService
	private static final Logger log = Logger.getLogger(GraphsController.class);
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	
    def index() { }
	
	/**
	 * Generic data function which all AJAX calls point to. Performs basic date validation and
	 * redirects to data services.
	 * @return JSON containing all data from all services
	 */
	def getData() {
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
			def dataType = DataType.findByName(req[val].dataType)
			"${dataType.serviceName}Data"(rawData, val, req[val], dataType)
		}
		def temp = []
		temp << ["root" : rawData]
		render((temp as JSON).toString())
	}
	
	/**
	 * Function to retrieve stock data from the getStockService.
	 * @param rawData - Full data map to push data into
	 * @param key - Key value
	 * @param vals - Values corresponding to key
	 */
	def stockData(rawData, key, vals, dataType) {
		int startMonth = Integer.parseInt(vals.startDate.split("/")[0]) - 1
		int startDay = Integer.parseInt(vals.startDate.split("/")[1])
		int startYear = Integer.parseInt(vals.startDate.split("/")[2])
		int endMonth = Integer.parseInt(vals.endDate.split("/")[0]) - 1
		int endDay = Integer.parseInt(vals.endDate.split("/")[1])
		int endYear = Integer.parseInt(vals.endDate.split("/")[2])
		def data = getStockService.getStock(vals.name, startMonth, startDay, startYear, endMonth, endDay, endYear, "d")
		def finalData = [:]
		boolean valid = true;
		if (dataType.optionNum.toInteger() == 1) {
			double yesterday = -1
			for (day in data.keySet().iterator().reverse()) {
				if (data[day] != 'Stock Not Found') {
					if (yesterday != -1) {
						finalData << [(day) : data[day]]
					} else {
						yesterday = 0
					}
				} else {
					valid = false;
				}
			}
		} else if (dataType.optionNum.toInteger() == 2) {
			double yesterday = -1
			for (day in data.keySet().iterator().reverse()) {
				if (data[day] != 'Stock Not Found') {
					if (yesterday == -1) {
						yesterday = data[day].toDouble()
					} else {
						finalData << [(day) : ((data[day].toDouble() - yesterday) / yesterday * 100)]
						yesterday = data[day].toDouble()
					}
				} else {
					valid = false;
				}
			}
		} else if (dataType.optionNum.toInteger() == 3) {
			double starting = -1
			for (day in data.keySet().iterator().reverse()) {
				if (data[day] != 'Stock Not Found') {
					if (starting == -1) {
						starting = data[day].toDouble()
					} else {
						finalData << [(day) : ((data[day].toDouble() - starting) / starting * 100)]
					}
				} else {
					valid = false;
				}
			}
		}
		if (valid) {
			def temp = ['dates' : finalData]
			temp << ['metadata' : ['label' : dataType.label, 'unit' : dataType.unit, 'req': vals, valid: true]]
			rawData << [(key) : temp]
		} else {
			rawData << [(key) : ['metadata' : ['label' : dataType.label, 'unit' : dataType.unit, 'req': vals, valid: false]]]
		}
	}
	
	/**
	 * Function to retrieve infinite data from the infiniteService.
	 * @param rawData - Full data map to push data into
	 * @param key - Key value
	 * @param vals - Values corresponding to key
	 */
	def infiniteData(rawData, key, vals, dataType) {
		def keyword;
		if (!validateKeyword(vals.name)) {
			flash.message = "Empty keyword. Defaulting to oil.";
			keyword = "Oil";
		} else {
			keyword = vals.name;
		}
		if (dataType.optionNum == 1) {
			def finalData = [:]
			def query = infiniteService.queryInfinite(keyword, vals.startDate, vals.endDate)
			for (i in query['data']) {
				def date = new Date(i.publishedDate).format('yyyy-MM-dd') + ' 4:00PM'
				double sentiment = 0
				for (ent in i.entities) {
					if (ent.positiveSentiment) {
						sentiment += ent.positiveSentiment * ent.significance
					}
					if (ent.negativeSentiment) {
						sentiment -= ent.negativeSentiment * ent.significance
					}
				}
				if (!finalData[date]) {
					finalData[date] = sentiment
				} else {
					finalData[date] = sentiment + finalData[date].toDouble()
				}
			}
			def temp = ['dates' : finalData]
			temp << ['metadata' : ['label' : dataType.label, 'unit' : dataType.unit, 'req': vals, valid: true]]
			rawData << [(key) : temp]
		} else if (dataType.optionNum == 2) {
			rawData << [(key): infiniteService.queryInfinite(keyword, vals.startDate, vals.endDate)]
		}
	}
	
	private boolean validateKeyword(String keyword) {
		return keyword != null;
	}
	
	private String formatDate(String date) {
		Date d = new Date(date);
		return DATE_FORMAT.format(d);
	}
	
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
	
	private String yesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return DATE_FORMAT.format(cal.getTime());
	}
	
	private String halfYearAgo() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -182);
		return DATE_FORMAT.format(cal.getTime());
	}
}
