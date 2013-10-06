package com.augurworks.web

class StockTicker {
	String symbol
	String name
	double lastSale
	double marketCap
	int IPOYear
	String sector
	String industry

    static constraints = {
		symbol()
		name()
		lastSale()
		marketCap()
		IPOYear()
		sector()
		industry()
    }
}
