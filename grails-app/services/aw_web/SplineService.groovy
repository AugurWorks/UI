package aw_web

import java.text.SimpleDateFormat;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction

import grails.transaction.Transactional

@Transactional
class SplineService {

	SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateParser2 = new SimpleDateFormat("MM/dd/yyyy");

	def spline(input, startDate, endDate) {
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
		for (long i = s; i <= e; i++) {
			try {
				long k = (long) i
				splined << [(new Date(k * 3600000 * 24).format('yyyy-MM-dd')): func.value(i)]
			} catch (q) {
				//println q
			}
		}
		splined.sort()
	}

	def serviceMethod() {
		
	}
}
