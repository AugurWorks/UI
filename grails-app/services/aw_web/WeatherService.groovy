package aw_web

import grails.transaction.Transactional

@Transactional
class WeatherService {

	def getWeather(int zipCode, String startDate, String endDate) {
		String start = startDate.split('/')[2] + startDate.split('/')[0] + startDate.split('/')[1];
		String end = endDate.split('/')[2] + endDate.split('/')[0] + endDate.split('/')[1];
		String url = 'http://www.ncdc.noaa.gov/cdo-services/services/datasets/GHCND/locations/ZIP:'
		url += zipCode;
		url += '/datatypes/PRCP/data.json?token=PIRcZaKtuiTDVjvtGHrMSrMnWQlgIEcM'
		url += '&startdate=' + start;
		url += '&enddate=' + end;
		println url
		URL urlCon;
		def dataList = [:]
		urlCon = new URL(url);
		try {
			URLConnection con = urlCon.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			int line = 0;
			while ((inputLine = br.readLine()) != null) {
				println inputLine
				if (line == 0) {
					inputLine.split(",");
				} else {
					String date2 = '' + inputLine.split(",")[0] + ' 4:00PM'
					dataList << [(date2) : inputLine.split(",")[4]]
				}
				line++;
			}
			br.close();
			dataList
		} catch (FileNotFoundException e) {
			dataList << ['val' : 'Weather Not Found']
			dataList
		}
	}
	
    def serviceMethod() {

    }
}
