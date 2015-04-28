package com.augurworks.web

class NeuralNetResult {
	
	User user
	Date created
	String stop
	long time
	long rounds
	double rms
	
	static hasMany = [lines: NeuralNetLine]
	
	static belongsTo = [request: Request]

    static constraints = {
		user()
		created()
		stop nullable: true
		time nullable: true
		rounds nullable: true
		rms nullable: true
    }
}
