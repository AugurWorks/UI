package com.augurworks.web

class DataTypeChoices {
	
	String name
	String key

    static constraints = {
		name()
		key()
    }
	
	String toString() {
		name
	}
}
