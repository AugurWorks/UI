package com.augurworks.web

import java.text.SimpleDateFormat;
import java.util.regex.Pattern.All;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import grails.transaction.Transactional

@Transactional
class SearchService {
	
	def dataService

    def search(sets, stocks, start, end, offset, agg) {
		((-offset)..(offset)).each { o ->
			def all = []
			all.addAll(sets.collect {
				def t = CorrelationSet.findByAggAndDataTypeChoiceAndStartAndEnd(agg, it, start, end);
				t ? t : new CorrelationSet(agg: agg, dataTypeChoice: it, start: start.plus(o), end: end.plus(o)).save(flush: true);
			})
			all.addAll(stocks.collect {
				def t = CorrelationSet.findByAggAndInputAndStartAndEnd(agg, it.symbol, start.plus(o), end.plus(o));
				t ? t : new CorrelationSet(agg: agg, input: it.symbol, start: start.plus(o), end: end.plus(o)).save(flush: true);
			})
			all.each { s1 ->
				all.each { s2 ->
					if (s1 != s2) {
						def f = [s1, s2].min { it.id }
						def s = [s1, s2].max { it.id }
						//println 'Starting "' + f + '" vs "' + s + '", off: ' + o
						def corSet = Correlation.findByFirstAndSecond(f, s);
						if (!corSet) {
							def data = dataService.getData(['0': formatRequest(s1, agg.name), '1': formatRequest(s2, agg.name)])
							def temp = data.root.collect { k, v -> v.dates.values() }
							PearsonsCorrelation corr = new PearsonsCorrelation();
							def result = corr.correlation((double[]) temp[0], (double[]) temp[1])
							new Correlation(first: f, second: s, correlation: result).save(flush: true)
						} else {
							//println 'Already done'
						}
					}
				}
			}
		}
		//println 'Done'
	}
	
	def formatRequest(set, agg) {
		if (set.dataTypeChoice) {
			[agg: agg, custom: '', dataType: set.dataTypeChoice.dataType.name, endDate: set.end.format('MM/dd/yyyy'), name: set.dataTypeChoice.name, longName: set.dataTypeChoice.name, startDate: set.start.format('MM/dd/yyyy')]
		} else {
			[agg: agg, custom: '', dataType: DataType.findByName('Stock Price'), endDate: set.end.format('MM/dd/yyyy'), name: set.input, longName: set.input, startDate: set.start.format('MM/dd/yyyy')]
		}
	}
}
