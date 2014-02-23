package com.augurworks.web

class Request {
	
	User user
	Date requestDate
	String page
	
	static hasMany = [dataSets: DataSet]

    static constraints = {
		user()
		requestDate()
		page()
    }
}
