package aw_web

import grails.transaction.Transactional

@Transactional
class QuandlService {
	
	def splineService
	def key = 'ANPCepUdefJHgJSm9MVx'
	def pre = 'http://www.quandl.com/api/v1/datasets/'
	def post = 'auth_token=' + key
	
	def getTreasuryData(String query, startDate, endDate, agg) {
		String url = pre + query + post
		def data = [:]
		def temp = [:]
		def json = splineService.getUrlJson(url)
		for (int i = 0; i < json.data.size(); i++) {
			temp << [(json.data[i][0]): json.data[i][1].toString()]
		}
		data << ['dates': splineService.spline(temp, startDate, endDate, agg)]
		data << ['metadata': ['valid': true, 'unit': '%']]
	}

    def serviceMethod() {

    }
}
