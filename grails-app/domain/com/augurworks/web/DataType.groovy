package com.augurworks.web

class DataType {
	
	String name
	String valueType
	int optionNum
	String serviceName
	String label
	String unit
	

    static constraints = {
		name blank: false, unique: true
		valueType blank: false, inList: ['Number', 'Text']
		optionNum min: 0
		serviceName blank: false
		label nullable: true
		unit nullable: true
    }
	
	String toString() {
		name
	}
}
