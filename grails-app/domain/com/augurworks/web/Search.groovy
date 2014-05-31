package com.augurworks.web

class Search {
	
	String name = 'No Name'
	boolean done = false
	
	static hasMany = [sets: CorrelationSet, correlations: Correlation]

    static constraints = {
		
    }
	
	String toString() {
		name
	}
}
