package com.augurworks.web

import java.text.SimpleDateFormat;
import grails.converters.JSON
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class SearchController {

	def searchService
	
	SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");

    def index() {
		[sets: DataTypeChoices.list(), agg: Aggregation.list(), stocks: StockTicker.findAllByActive(true)]
	}
	
	def submit() {
		def p = params;
		def search = new Search(name: params.name).save(flush: true);
		runAsync {
			searchService.search(search, p.sets ? p.sets.collect { DataTypeChoices.get(it) } : [], p.stocks ? p.stocks.collect { StockTicker.get(it) } : [], dateParser.parse(p.start), dateParser.parse(p.end), p.offset.toInteger(), Aggregation.findByName(p.agg))
		}
		redirect(action: 'check', id: search.id)
	}
	
	def check() {
		[searches: Search.list(), current: params.id]
	}
	
	def refresh() {
		def search = Search.get(params.id)
		render([success: true, sets: search.sets, correlations: search.correlations, done: search.done, choices: search.sets.grep { !it.input }.collect { it.dataTypeChoice }.unique()] as JSON)
	}
}
