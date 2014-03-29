package com.augurworks.web

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import groovyx.gpars.GParsPool

import java.text.SimpleDateFormat

import org.apache.log4j.Logger

import com.augurworks.web.data.DataTransferObjects

@Secured(['ROLE_ADMIN', 'ROLE_USER'])
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
		recordRequest(req, null)
        def data = dataService.getData(req)
        render((data as JSON).toString())
    }
	
	def recordRequest(req, pageDefault) {
		if (req.values().collect { it.reqId.toInteger() != -1 }.any { !it } || Request.findById(req.values()[0].reqId).dataSets.size() != req.values().size()) {
			def reqVals = [page: req.values()[0].page, requestDate: new Date(), views: 1]
			if (pageDefault) {
				reqVals << [pageDefault: pageDefault, user: User.findByUsername('Admin')]
			} else {
				reqVals << [user: springSecurityService.currentUser]
			}
			Request obj = new Request(reqVals)
			obj.save()
			if (obj.hasErrors()) {
				log.error obj.errors
			}
			for (i in req.keySet()) {
				def vals = [agg: req[i].agg,
					custom: req[i].custom,
					dataType: DataType.findByName(req[i].dataType),
					endDate: req[i].endDate,
					name: req[i].name,
					startDate: req[i].startDate,
					page: req[i].page,
					num: i.toInteger()]
				if (req[i].offset) {
					vals << [offset: req[i].offset]
				}
				DataSet d = new DataSet(vals)
				d.save()
				if (d.hasErrors()) {
					log.error d.errors
				}
				obj.addToDataSets(d)
			}
			obj.save()
		} else {
			Request obj = Request.findById(req.values()[0].reqId)
			obj.views++
			obj.save()
		}
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
