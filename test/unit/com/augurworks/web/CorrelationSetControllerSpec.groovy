package com.augurworks.web



import grails.test.mixin.*
import spock.lang.*

@TestFor(CorrelationSetController)
@Mock(CorrelationSet)
class CorrelationSetControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.correlationSetInstanceList
            model.correlationSetInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.correlationSetInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            def correlationSet = new CorrelationSet()
            correlationSet.validate()
            controller.save(correlationSet)

        then:"The create view is rendered again with the correct model"
            model.correlationSetInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            correlationSet = new CorrelationSet(params)

            controller.save(correlationSet)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/correlationSet/show/1'
            controller.flash.message != null
            CorrelationSet.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def correlationSet = new CorrelationSet(params)
            controller.show(correlationSet)

        then:"A model is populated containing the domain instance"
            model.correlationSetInstance == correlationSet
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def correlationSet = new CorrelationSet(params)
            controller.edit(correlationSet)

        then:"A model is populated containing the domain instance"
            model.correlationSetInstance == correlationSet
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"A 404 error is returned"
            status == 404

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def correlationSet = new CorrelationSet()
            correlationSet.validate()
            controller.update(correlationSet)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.correlationSetInstance == correlationSet

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            correlationSet = new CorrelationSet(params).save(flush: true)
            controller.update(correlationSet)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/correlationSet/show/$correlationSet.id"
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
            def correlationSet = new CorrelationSet(params).save(flush: true)

        then:"It exists"
            CorrelationSet.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(correlationSet)

        then:"The instance is deleted"
            CorrelationSet.count() == 0
            response.redirectedUrl == '/correlationSet/index'
            flash.message != null
    }
}
