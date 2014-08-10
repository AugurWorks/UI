package com.augurworks.web

class NeuralNetResult {
	
	User user
	Date created
	String data
	
	static belongsTo = [request: Request]

    static constraints = {
		user()
		created()
		data maxSize: 5000, nullable: true
    }
}
