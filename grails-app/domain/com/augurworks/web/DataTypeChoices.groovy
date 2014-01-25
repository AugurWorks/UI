package com.augurworks.web

class DataTypeChoices {
	
	String name
	String key

    static constraints = {
		name()
		key()
    }
	
	static mapping = {
		sort 'name'
	}
	
	String toString() {
		name
	}
}
