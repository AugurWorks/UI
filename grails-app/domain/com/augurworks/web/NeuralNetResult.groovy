package com.augurworks.web

class NeuralNetResult {
	
	User user
	Date created
	String dataLocation
	
	static belongsTo = [request: Request]

    static constraints = {
		user()
		created()
		dataLocation nullable: true
    }
}
