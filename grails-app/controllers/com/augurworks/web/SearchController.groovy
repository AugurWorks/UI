package com.augurworks.web

import java.text.SimpleDateFormat;

import grails.converters.JSON

class SearchController {
	
	def backgroundService
	def searchService
	
	SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");

    def index() {
		println StockTicker.count()
		println StockTicker.findAllByActive(true).size()
		[sets: DataTypeChoices.list(), agg: Aggregation.list(), stocks: StockTicker.findAllByActive(true)]
	}
	
	def submit() {
		def d = JSON.parse(params.data);
		println d
		backgroundService.execute('Correlate', {
			searchService.search(d.sets ? d.sets.collect { DataTypeChoices.get(it) } : [], d.stocks ? d.stocks.collect { StockTicker.get(it) } : [], dateParser.parse(d.start), dateParser.parse(d.end), d.offset.toInteger(), Aggregation.findByName(d.agg))
		})
		render([success: true] as JSON)
	}
}
