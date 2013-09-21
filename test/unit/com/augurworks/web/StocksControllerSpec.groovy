package com.augurworks.web



import grails.test.mixin.*
import spock.lang.*

@TestFor(StocksController)
@Mock(Stocks)
class StocksControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.stocksInstanceList
            model.stocksInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.stocksInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            def stocks = new Stocks()
            stocks.validate()
            controller.save(stocks)

        then:"The create view is rendered again with the correct model"
            model.stocksInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            stocks = new Stocks(params)

            controller.save(stocks)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/stocks/show/1'
            controller.flash.message != null
            Stocks.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def stocks = new Stocks(params)
            controller.show(stocks)

        then:"A model is populated containing the domain instance"
            model.stocksInstance == stocks
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def stocks = new Stocks(params)
            controller.edit(stocks)

        then:"A model is populated containing the domain instance"
            model.stocksInstance == stocks
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"A 404 error is returned"
            status == 404

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def stocks = new Stocks()
            stocks.validate()
            controller.update(stocks)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.stocksInstance == stocks

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            stocks = new Stocks(params).save(flush: true)
            controller.update(stocks)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/stocks/show/$stocks.id"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            controller.delete(null)

        then:"A 404 is returned"
            status == 404

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def stocks = new Stocks(params).save(flush: true)

        then:"It exists"
            Stocks.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(stocks)

        then:"The instance is deleted"
            Stocks.count() == 0
            response.redirectedUrl == '/stocks/index'
            flash.message != null
    }
}
