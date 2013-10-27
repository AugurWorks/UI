package com.augurworks.web

import grails.converters.JSON
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

class InfiniteController {

	def springSecurityService
	def infiniteService
	private static final Logger log = Logger.getLogger(GraphsController.class);
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

	def index() {
		[service : springSecurityService, startDate: yesterday(), endDate: today()]
	}
	
	def infiniteData() {
		def keyword, startDate, endDate, reply;
		def sortVals = [srt: '', order: '']
		if (!validateKeyword(params.keyword)) {
			flash.message = "Empty keyword. Defaulting to oil.";
			keyword = "Oil";
		} else {
			keyword = params.keyword;
		}
		if (!validateDates(params.startDate, params.endDate)) {
			flash.message = "Dates were invalid. Defaulting to today.";
			startDate = yesterday();
			endDate = today();
		} else {
			startDate = formatDate(params.startDate);
			endDate = formatDate(params.endDate);
		}
		if (params.sort == null) {
			sortVals.srt = 'significance'
		} else {
			sortVals.srt = params.sort
		}
		if (params.order == null) {
			sortVals.order = 'desc'
		} else {
			sortVals.order = params.order
		}

		reply = infiniteService.queryInfinite(keyword, startDate, endDate);
		render((reply as JSON).toString())
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
}
