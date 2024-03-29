package com.augurworks.web



import grails.test.mixin.*
import spock.lang.*

@TestFor(StockTickerController)
@Mock(StockTicker)
class StockTickerControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.stockTickerInstanceList
            model.stockTickerInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.stockTickerInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            def stockTicker = new StockTicker()
            stockTicker.validate()
            controller.save(stockTicker)

        then:"The create view is rendered again with the correct model"
            model.stockTickerInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            stockTicker = new StockTicker(params)

            controller.save(stockTicker)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/stockTicker/show/1'
            controller.flash.message != null
            StockTicker.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def stockTicker = new StockTicker(params)
            controller.show(stockTicker)

        then:"A model is populated containing the domain instance"
            model.stockTickerInstance == stockTicker
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def stockTicker = new StockTicker(params)
            controller.edit(stockTicker)

        then:"A model is populated containing the domain instance"
            model.stockTickerInstance == stockTicker
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"A 404 error is returned"
            status == 404

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def stockTicker = new StockTicker()
            stockTicker.validate()
            controller.update(stockTicker)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.stockTickerInstance == stockTicker

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            stockTicker = new StockTicker(params).save(flush: true)
            controller.update(stockTicker)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/stockTicker/show/$stockTicker.id"
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
            def stockTicker = new StockTicker(params).save(flush: true)

        then:"It exists"
            StockTicker.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(stockTicker)

        then:"The instance is deleted"
            StockTicker.count() == 0
            response.redirectedUrl == '/stockTicker/index'
            flash.message != null
    }
}
