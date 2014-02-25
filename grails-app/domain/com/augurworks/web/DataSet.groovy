package com.augurworks.web

class DataSet {
	
	String agg
	String custom
	DataType dataType
	String endDate
	String name
	String startDate
	String page
	int offset
	int num

    static constraints = {
		num()
		startDate()
		endDate()
		name()
		dataType()
		agg()
		offset nullable: true
		custom nullable: true
    }
	
	String toString() {
		dataType.name + ': ' + name + ' (' + startDate + '-' + endDate + ')'
	}
}
