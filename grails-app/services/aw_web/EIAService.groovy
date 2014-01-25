package aw_web

import grails.transaction.Transactional
import grails.converters.JSON
import java.text.SimpleDateFormat
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction

@Transactional
class EIAService {
	
	def splineService
	def key = '5E75660C4E93721C9E2A30F1057F0D72'
	def base = 'http://api.eia.gov/series/?api_key=' + key + '&series_id='
	SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");
	
	def getSeries(String seriesId, startDate, endDate) {
		String url = base + seriesId
		URL urlCon;
		def data = [:]
		def temp = [:]
		def temp2 = [:]
		urlCon = new URL(url);
		def json;
		try {
			URLConnection con = urlCon.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			int line = 0;
			while ((inputLine = br.readLine()) != null) {
				json = JSON.parse(inputLine)
			}
			br.close();
		} catch (FileNotFoundException e) {
			data << ['val' : 'Stock Not Found']
			data
		}
		int arraySize = json.series[0].data.length()
		double[] j = new double[arraySize]
		double[] h = new double[arraySize]
		for (i in json.series[0].data) {
			if (json.series[0].f == 'M') {
				temp << [(i[0].substring(0, 4) + '-' + i[0].substring(4, 6) + '-01 4:00PM'): i[1]]
			} else if (json.series[0].f == 'W') {
				temp << [(i[0].substring(0, 4) + '-' + i[0].substring(4, 6) + '-' + i[0].substring(6, 8) + ' 4:00PM'): i[1]]
			} else if (json.series[0].f == 'A') {
				temp << [(i[0].substring(0, 4) + '-01-01 4:00PM'): i[1]]
			}
		}
		data << ['dates': splineService.spline(temp, startDate, endDate)]
		data << ['metadata': ['valid': true, 'unit': json.series[0].unitsshort]]
	}

    def serviceMethod() {

    }
}
