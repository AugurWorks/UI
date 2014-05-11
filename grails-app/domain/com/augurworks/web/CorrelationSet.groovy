package com.augurworks.web

class CorrelationSet {
	DataTypeChoices dataTypeChoice
	String input
	Aggregation agg
	
	Date start
	Date end

    static constraints = {
		dataTypeChoice nullable: true
		input nullable: true
    }
	
	String toString() {
		(dataTypeChoice ? dataTypeChoice.name : input) + ', ' + agg.name + ' (' + start.format('MM/dd/yyyy') + '-' + end.format('MM/dd/yyyy') + ')'
	}
}
