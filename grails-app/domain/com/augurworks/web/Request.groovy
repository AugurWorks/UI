package com.augurworks.web

class Request {
	
	User user
	Date requestDate
	String page
	int views
	String pageDefault
	NeuralNetResult neuralNet
	
	static hasMany = [dataSets: DataSet]

    static constraints = {
		user nullable: true
		requestDate nullable: true
		page()
		pageDefault nullable: true
		neuralNet nullable: true
    }
}
