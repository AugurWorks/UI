package com.augurworks.web

import java.text.SimpleDateFormat
import grails.converters.JSON
import org.apache.log4j.Logger
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
class GraphsController {

	def springSecurityService
	def getStockService
	private static final Logger log = Logger.getLogger(GraphsController.class);
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	
	/*
	 * InputNum - Number of inputs, (1, 2, null)
	 * SameSize - Boolean for if datasets are required to be the same size
	 * Page - Page type, (graph, analysis)
	 */
	
	def index() {
		def req = [0: [name: 'USO', dataType: 'Stock Price', startDate: halfYearAgo(), endDate: today(), agg: 'Day Value', custom: '', page: 'index']]
		def map = getDefault()
		map << [req: req as JSON, inputNum: null, sameSize: false]
	}
	
	def calendar() {
		def req = [0: [name: 'USO', dataType: 'Stock Price', startDate: halfYearAgo(), endDate: today(), agg: 'Day Value', custom: '', page: 'calendar']]
		def map = getDefault()
		map << [req: req as JSON, page: 'graph', inputNum: 1, sameSize: false]
	}
	
	def correlation() {
		def req = [0: [name: 'USO', dataType: 'Stock Price', startDate: halfYearAgo(), endDate: today(), agg: 'Day Value', custom: '', page: 'correlation'],
				   1: [name: 'DJIA', dataType: 'Stock Price', startDate: halfYearAgo(), endDate: today(), agg: 'Day Value', custom: '', page: 'correlation']]
		def map = getDefault()
		map << [req: req as JSON, page: 'graph', inputNum: 2, sameSize: true]
	}
	
	def covariance() {
		Random r = new Random()
		def req = [:]
		int num = r.nextInt(3) + 6
		int cnt = 0
		def dataTypes = DataType.list()
		def aggs = Aggregation.list()
		while (cnt < num) {
			DataType type = dataTypes[r.nextInt(dataTypes.size())]
			if (type.dataChoices.size() > 0) {
				DataTypeChoices choice = type.dataChoices[r.nextInt(type.dataChoices.size())]
				req[cnt] = [name: choice.name, dataType: type.name, startDate: halfYearAgo(), endDate: today(), agg: aggs[r.nextInt(aggs.size())].name, custom: '', page: 'covariance', offset: 0]
				cnt++
			}
		}
		def map = getDefault()
		map << [req: req as JSON, page: 'graph', inputNum: null, sameSize: true]
	}
	
	def getDefault() {
		String deep;
		JSON.use('deep') {
			deep = DataType.findAll { valueType == 'Number' }.sort() as JSON
		}
		[page: 'graph', service : springSecurityService, startDate: halfYearAgo(), endDate: today(), agg: Aggregation.list(), dataTypes: DataType.findAll { valueType == 'Number' }, dataTypeJson: deep]
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
