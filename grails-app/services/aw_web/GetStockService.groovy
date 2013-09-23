package aw_web

import grails.transaction.Transactional

@Transactional
class GetStockService {
	
	ArrayList getStock(String stock, int startMonth, int startDay, int startYear, int endMonth, int endDay, int endYear, String quoteInterval) {
		String url = 'http://ichart.yahoo.com/table.csv?s=';
		ArrayList stockValues = [];
		ArrayList temp = [];
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
		try {
			urlCon = new URL(url);
			URLConnection con = urlCon.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			int line = 0;
			while ((inputLine = br.readLine()) != null) {
				if (line == 0) {
					inputLine.split(",");
				} else {
					stockValues.push(inputLine.split(",")[4]);
				}
				line++;
			}
			br.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		temp.push(stockValues);
		return temp;
	}

    def serviceMethod() {

    }
}
