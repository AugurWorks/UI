package com.augurworks.web

import grails.converters.JSON
import groovyx.gpars.GParsPool

import java.text.SimpleDateFormat

import org.apache.log4j.Logger

import com.augurworks.web.data.DataTransferObjects

class DataController {

	def springSecurityService
	def dataService

    private static final Logger log = Logger.getLogger(GraphsController.class);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    def index() { }

    /**
     * Takes AJAX request and passes parameters into getData.
     * @return Renders returned map as JSON.
     */
    def ajaxData() {
		def req = JSON.parse(params.req)
		dataService.recordRequest(req, null)
        def data = dataService.getData(req)
        render((data as JSON).toString())
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

    private String daysAgo(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1 * days);
        return DATE_FORMAT.format(cal.getTime());
    }
}
