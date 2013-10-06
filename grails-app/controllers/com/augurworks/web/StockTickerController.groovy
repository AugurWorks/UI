package com.augurworks.web



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class StockTickerController {

    def scaffold = true
}
