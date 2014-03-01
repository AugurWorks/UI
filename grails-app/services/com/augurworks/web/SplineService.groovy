package com.augurworks.web

import java.text.SimpleDateFormat;
import grails.converters.JSON;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import grails.transaction.Transactional;
import com.augurworks.web.Aggregation;
import grails.plugin.cache.CachePut;
import grails.plugin.cache.CacheEvict;
import grails.plugin.cache.Cacheable;

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
		for (i in input.keySet().sort { a, b -> dateParser.parse(a) <=> dateParser.parse(b) }) {
			j[counter] = Math.round(dateParser.parse(i).getTime() / (3600000 * 24))
			h[counter] = Double.parseDouble(input[i])
			counter++
		}
		SplineInterpolator inter = new SplineInterpolator()
		PolynomialSplineFunction func = inter.interpolate(j, h)
		int s = Math.round(dateParser2.parse(startDate).getTime() / (3600000 * 24))
		int e = Math.round(dateParser2.parse(endDate).getTime() / (3600000 * 24))
		for (long i = s; i <= e; i++) {
			try {
				long k = (long) i + 1
				splined << [(new Date(k * 3600000 * 24).format('yyyy-MM-dd')): func.value(i)]
			} catch (q) {
				//log.error q
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
		if (data) {
			int val = Aggregation.findByName(agg).val
			def i0 = data[data.keySet()[0]].toDouble();
			def last = i0
			def keys = data.keySet()
			def temp = [:]
			for (int i = 0; i < keys.size(); i++) {
				def cur = data[keys[i]].toDouble()
				if (val == 1) {
					temp << [(keys[i]): cur]
				} else if (val == 2) {
					temp << [(keys[i]): (cur / i0)]
				} else if (val == 3) {
					temp << [(keys[i]): (cur - last)]
				} else if (val == 4) {
					temp << [(keys[i]): (100 * (cur - last) / last)]
				} else if (val == 5) {
					temp << [(keys[i]): (cur - i0)]
				} else if (val == 6) {
					temp << [(keys[i]): (100 * (cur - i0) / i0)]
				}
				last = cur
			}
		temp.sort()
		} else {
			data
		}
	}
	
	def checkUnits(units, agg) {
		int val = Aggregation.findByName(agg).val
		if (val == 4 || val == 6) {
			'%'
		} else if (val == 2) {
			'norm'
		} else {
			units
		}
	}
	
	@Cacheable(value='url', key='#url')
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
			log.error 'getUrlJson: ' + e
		}
		json
	}

	@CacheEvict(value='url', allEntries=true)
	def clearCache() {
		log.info 'clearCache: Clearing cache'
	}

	def serviceMethod() {
		
	}
}
