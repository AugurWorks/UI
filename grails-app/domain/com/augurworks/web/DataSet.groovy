package com.augurworks.web

class DataSet {
	
	String agg
	String custom
	DataType dataType
	String endDate
	String longName
	String name
	String startDate
	int num

    static constraints = {
		num()
		startDate()
		endDate()
		name()
		dataType()
		longName()
		agg()
		custom nullable: true
    }
	
	String toString() {
		dataType.name + ': ' + name + ' (' + startDate + '-' + endDate + ')'
	}
}
