package com.augurworks.web

import grails.converters.JSON

import org.codehaus.groovy.grails.web.json.*

import grails.transaction.Transactional

@Deprecated
@Transactional
class GetStockService {
	
	def splineService
	
	def getStock(String stock, int startMonth, int startDay, int startYear, int endMonth, int endDay, int endYear, String quoteInterval, startDate, endDate, agg) {
		String url = 'http://ichart.yahoo.com/table.csv?s=';
		url += stock;
		url += '&a=';
		url += startMonth;
		url += '&b=';
		url += startDay;
		url += '&c=';
		url += startYear;
		url += '&d=';
		url += endMonth;
		url += '&e=';
		url += endDay;
		url += '&f=';
		url += endYear;
		url += '&g=';
		url += quoteInterval; //d=daily, w=weekly, m=monthly
		url += '&ignore=.csv';
		URL urlCon;
		def dataList = [:]
		urlCon = new URL(url);
		try {
			URLConnection con = urlCon.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			int line = 0;
			while ((inputLine = br.readLine()) != null) {
				if (line == 0) {
					inputLine.split(",");
				} else {
					String date2 = '' + inputLine.split(",")[0] + ' 4:00PM'
					dataList << [(date2) : inputLine.split(",")[4]]
				}
				line++;
			}
			br.close();
			splineService.spline(dataList, startDate, endDate, agg)
		} catch (e) {
			log.error 'getStock: ' + e
			dataList << ['val' : 'Stock Not Found']
			dataList
		}
	}

    def serviceMethod() {

    }
}
