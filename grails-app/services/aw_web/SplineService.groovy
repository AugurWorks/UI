package aw_web

import java.text.SimpleDateFormat;
import grails.converters.JSON
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction
import grails.transaction.Transactional

@Transactional
class SplineService {

	SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateParser2 = new SimpleDateFormat("MM/dd/yyyy");

	/*
	 * Function used to fill in holes in data.
	 */
	def spline(input, startDate, endDate, agg) {
		def splined = [:]
		int arraySize = input.keySet().size()
		double[] j = new double[arraySize]
		double[] h = new double[arraySize]
		int counter = 0
		for (i in input.keySet().sort()) {
			j[counter] = Math.round(dateParser.parse(i).getTime() / (3600000 * 24))
			h[counter] = Double.parseDouble(input[i])
			counter++
		}
		SplineInterpolator inter = new SplineInterpolator()
		PolynomialSplineFunction func = inter.interpolate(j, h)
		int s = Math.round(dateParser2.parse(startDate).getTime() / (3600000 * 24))
		int e = Math.round(dateParser2.parse(endDate).getTime() / (3600000 * 24))
		for (long i = (s - 1); i <= e; i++) {
			try {
				long k = (long) i
				splined << [(new Date(k * 3600000 * 24).format('yyyy-MM-dd')): func.value(i)]
			} catch (q) {
				//println q
			}
		}
		aggregate(splined.sort(), agg)
	}
	
	/*
	 * Aggregates data either
	 * 1: daily value
	 * 2: normalized daily value
	 * 3: daily change
	 * 4: daily percent change
	 * 5: period change
	 * 6: period percent change
	 */
	def aggregate(data, agg) {
		
		data
	}
	
	def getUrlJson(String url) {
		URL urlCon = new URL(url);
		def json;
		try {
			URLConnection con = urlCon.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				json = JSON.parse(inputLine)
			}
			br.close();
		} catch (e) {
			json = ['valid': false]
		}
		json
	}

	def serviceMethod() {
		
	}
}
