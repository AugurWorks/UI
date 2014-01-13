package com.augurworks.web

class Algorithm {
	
	String name
	int minInputs
	int maxInputs
	
	static hasMany = [dataTypes: DataType]

    static constraints = {
		name()
		minInputs min: 1
		maxInputs nullable: true
    }
	
	String toString() {
		name
	}
}
