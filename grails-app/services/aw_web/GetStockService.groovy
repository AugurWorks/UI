package aw_web

import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.*
import grails.transaction.Transactional

@Transactional
class GetStockService {
	
	def getStock(String stock, int startMonth, int startDay, int startYear, int endMonth, int endDay, int endYear, String quoteInterval) {
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
		List dataList = []
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
					def datum = [
						date: date2,
						val: inputLine.split(",")[4]
					]
					dataList.push(datum)
				}
				line++;
			}
			br.close();
			def data = [data: [dataList]]
			data as JSON
		} catch (FileNotFoundException e) {
			println 'Caught'
			'Stock Not Found'
		}
	}

    def serviceMethod() {

    }
}
