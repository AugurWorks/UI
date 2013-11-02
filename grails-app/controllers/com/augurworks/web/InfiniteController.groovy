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
		[service : springSecurityService, startDate: yesterday(), endDate: today()]
	}
	
	@Deprecated
	def infiniteData() {
		def req = JSON.parse(params.req)
		def rawData = [:]
		for (val in req.keySet()) {
			def startDate, endDate, keyword;
			def sortVals = [srt: '', order: '']
			if (!validateKeyword(val)) {
				flash.message = "Empty keyword. Defaulting to oil.";
				keyword = "Oil";
			} else {
				keyword = val;
			}
			if (!validateDates(req[val].startDate, req[val].endDate)) {
				flash.message = "Dates were invalid. Defaulting to today.";
				startDate = yesterday();
				endDate = today();
			} else {
				startDate = formatDate(req[val].startDate);
				endDate = formatDate(req[val].endDate);
			}
			rawData << [(val): infiniteService.queryInfinite(keyword, startDate, endDate)]
		}
		render((rawData as JSON).toString())
	}

	@Deprecated
	private boolean validateKeyword(String keyword) {
		return keyword != null;
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

	@Deprecated
	private String today() {
		Calendar cal = Calendar.getInstance();
		return DATE_FORMAT.format(cal.getTime());
	}

	@Deprecated
	private String yesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return DATE_FORMAT.format(cal.getTime());
	}
}
