package com.augurworks.web

class Stocks {
	int id
	String ticker
	double price
	String time
	double day_change
	double open
	double daily_high
	double daily_low
	int volume
	Date date
	double adjusted_close

    static constraints = {
    }
	
	static mapping = {
		datasource 'MySQL'
	}
}
