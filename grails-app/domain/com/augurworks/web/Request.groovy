package com.augurworks.web

class Request {
	
	User user
	Date requestDate
	String page
	int views
	String pageDefault
	
	static hasMany = [dataSets: DataSet, neuralNets: NeuralNetResult]

    static constraints = {
		user nullable: true
		requestDate nullable: true
		page()
		pageDefault nullable: true
    }
}
