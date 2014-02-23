package com.augurworks.web

class DataSet {
	
	String agg
	String custom
	DataType dataType
	String endDate
	String name
	String startDate
	int num

    static constraints = {
		num()
		startDate()
		endDate()
		name()
		dataType()
		agg()
		custom nullable: true
    }
	
	String toString() {
		dataType.name + ': ' + name + ' (' + startDate + '-' + endDate + ')'
	}
}
