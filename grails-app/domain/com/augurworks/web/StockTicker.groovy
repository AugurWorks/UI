package com.augurworks.web

class StockTicker {
	String symbol
	String name
	String code
	String ratiosCode
	Boolean active
	int col

    static constraints = {
		symbol()
		name()
		code nullable: true
		ratiosCode()
		active()
		col()
    }
	
	String toString() {
		symbol + ": " + name
	}
}
