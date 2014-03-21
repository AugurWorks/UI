package com.augurworks.web

import java.text.SimpleDateFormat
import grails.converters.JSON
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
class GraphsController {

	def springSecurityService
	def getStockService
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	
	/*
	 * InputNum - Number of inputs, (1, 2, null)
	 * SameSize - Boolean for if datasets are required to be the same size
	 * Page - Page type, (graph, analysis)
	 */
	
	def index() {
		def req = [:]
		Request requestVal;
		if (params.id) {
			requestVal = Request.findById(params.id)
		} else {
			requestVal = Request.findByPageDefault('index')
		}
		for (i in requestVal.dataSets) {
			req[i.num] = [name: i.name, dataType: i.dataType.name, startDate: i.startDate, endDate: i.endDate, agg: i.agg, custom: i.custom ? i.custom : '', page: i.page, offset: i.offset, reqId: requestVal.id]
		}
		def map = getDefault()
		map << [req: req as JSON, page: 'graph', inputNum: null, sameSize: false]
	}
	
	def calendar() {
		def req = [:]
		Request requestVal;
		if (params.id) {
			requestVal = Request.findById(params.id)
		} else {
			requestVal = Request.findByPageDefault('calendar') 
		}
		for (i in requestVal.dataSets) {
			req[i.num] = [name: i.name, dataType: i.dataType.name, startDate: i.startDate, endDate: i.endDate, agg: i.agg, custom: i.custom ? i.custom : '', page: i.page, offset: i.offset, reqId: requestVal.id]
		}
		def map = getDefault()
		map << [req: req as JSON, page: 'graph', inputNum: 1, sameSize: false]
	}
	
	def correlation() {
		def req = [:]
		Request requestVal;
		if (params.id) {
			requestVal = Request.findById(params.id)
		} else {
			requestVal = Request.findByPageDefault('correlation')
		}
		for (i in requestVal.dataSets) {
			req[i.num] = [name: i.name, dataType: i.dataType.name, startDate: i.startDate, endDate: i.endDate, agg: i.agg, custom: i.custom ? i.custom : '', page: i.page, offset: i.offset, reqId: requestVal.id]
		}
		def map = getDefault()
		map << [req: req as JSON, page: 'graph', inputNum: 2, sameSize: true]
	}
	
	def covariance() {
		def req = [:]
		if (!params.id) {
			/*Random r = new Random()
			int num = r.nextInt(3) + 6
			req = generateReq(num)*/
			Request requestVal = Request.findByPageDefault('covariance')
			for (i in requestVal.dataSets) {
				req[i.num] = [name: i.name, dataType: i.dataType.name, startDate: i.startDate, endDate: i.endDate, agg: i.agg, custom: i.custom ? i.custom : '', page: i.page, offset: i.offset, reqId: requestVal.id]
			}
		} else {
			Request requestVal = Request.findById(params.id)
			for (i in requestVal.dataSets) {
				req[i.num] = [name: i.name, dataType: i.dataType.name, startDate: i.startDate, endDate: i.endDate, agg: i.agg, custom: i.custom ? i.custom : '', page: i.page, offset: i.offset, reqId: params.id]
			}
		}
		def map = getDefault()
		map << [req: req as JSON, page: 'graph', inputNum: null, sameSize: true]
	}
	
	def sentiment() {
		def req = [:]
		Request requestVal;
		if (params.id) {
			requestVal = Request.findById(params.id)
		} else {
			requestVal = Request.findByPageDefault('sentiment') 
		}
		for (i in requestVal.dataSets) {
			req[i.num] = [name: i.name, dataType: i.dataType.name, startDate: i.startDate, endDate: i.endDate, agg: i.agg, custom: i.custom ? i.custom : '', page: i.page, offset: i.offset, reqId: requestVal.id]
		}
		def map = getDefault()
		map << [req: req as JSON, inputNum: 1, sameSize: false, page: 'sentiment', dataTypes: DataType.findAll { valueType == 'Text' }, startDate: lastWeek()]
	}
	
	def matrix() {
		def req = [:]
		Request requestVal;
		if (params.id) {
			requestVal = Request.findById(params.id)
		} else {
			requestVal = Request.findByPageDefault('matrix') 
		}
		for (i in requestVal.dataSets) {
			req[i.num] = [name: i.name, dataType: i.dataType.name, startDate: i.startDate, endDate: i.endDate, agg: i.agg, custom: i.custom ? i.custom : '', page: i.page, offset: i.offset, reqId: requestVal.id]
		}
		def map = getDefault()
		map << [req: req as JSON, inputNum: 1, sameSize: false, page: 'matrix', dataTypes: DataType.findAll { valueType == 'Text' }, startDate: lastWeek()]
	}
	
	def nodes() {
		def req = [:]
		Request requestVal;
		if (params.id) {
			requestVal = Request.findById(params.id)
		} else {
			requestVal = Request.findByPageDefault('node') 
		}
		for (i in requestVal.dataSets) {
			req[i.num] = [name: i.name, dataType: i.dataType.name, startDate: i.startDate, endDate: i.endDate, agg: i.agg, custom: i.custom ? i.custom : '', page: i.page, offset: i.offset, reqId: requestVal.id]
		}
		def map = getDefault()
		map << [req: req as JSON, inputNum: 1, sameSize: false, page: 'node', dataTypes: DataType.findAll { valueType == 'Text' }, startDate: lastWeek()]
	}
	
	def getDefault() {
		String deep;
		JSON.use('deep') {
			deep = DataType.findAll { valueType == 'Number' }.sort() as JSON
		}
		[service : springSecurityService, startDate: halfYearAgo(), endDate: today(), agg: Aggregation.list(), dataTypes: DataType.findAll { valueType == 'Number' }, dataTypeJson: deep]
	}
	
	def generateReq(num) {
		def req = [:]
		Random r = new Random()
		int cnt = 0
		def dataTypes = DataType.list()
		def aggs = Aggregation.list()
		while (cnt < num) {
			DataType type = dataTypes[r.nextInt(dataTypes.size())]
			if (type.dataChoices.size() > 0) {
				DataTypeChoices choice = type.dataChoices[r.nextInt(type.dataChoices.size())]
				req[cnt] = [name: choice.name, dataType: type.name, startDate: halfYearAgo(), endDate: today(), agg: aggs[r.nextInt(aggs.size())].name, custom: '', page: 'covariance', offset: 0, reqId: -1]
				cnt++
			}
		}
		req
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
	
	private String lastWeek() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -4);
		return DATE_FORMAT.format(cal.getTime());
	}
}
