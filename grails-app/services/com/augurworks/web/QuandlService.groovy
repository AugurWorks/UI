package com.augurworks.web

import grails.transaction.Transactional

@Transactional
class QuandlService {
	
	def splineService
	def key = '5e9eS5tr9wcB4x2iS2t4'
	def pre = 'http://www.quandl.com/api/v1/datasets/'
	def post = '.json?auth_token=' + key
	
	def getData(String query, startDate, endDate, agg, col) {
		String url = pre + query + post
		def data = [:]
		def temp = [:]
		def json = splineService.getUrlJson(url)
		for (int i = 0; i < json.data.size(); i++) {
			if (json.data[i][0] && json.data[i][col]) {
				temp << [(json.data[i][0]): json.data[i][col].toString()]
			}
		}
		data << ['dates': splineService.spline(temp, startDate, endDate, agg)]
		if (data.dates.size() == 0) {
			data << ['metadata': ['valid': false]]
		} else {
			data << ['metadata': ['valid': true]]
		}
	}

    def serviceMethod() {

    }
}
