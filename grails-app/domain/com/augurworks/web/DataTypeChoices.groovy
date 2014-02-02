package com.augurworks.web

class DataTypeChoices {
	
	String name
	String key
	String unit
	int dataCol = 1
	String url = ''

    static constraints = {
		name()
		key()
		unit nullable: true
		dataCol()
		url()
    }
	
	static mapping = {
		sort 'name'
	}
	
	String toString() {
		name
	}
}
