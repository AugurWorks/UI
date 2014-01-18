package com.augurworks.web



import grails.test.mixin.*
import spock.lang.*

@TestFor(DataTypeChoicesController)
@Mock(DataTypeChoices)
class DataTypeChoicesControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.dataTypeChoicesInstanceList
            model.dataTypeChoicesInstanceCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.dataTypeChoicesInstance!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            def dataTypeChoices = new DataTypeChoices()
            dataTypeChoices.validate()
            controller.save(dataTypeChoices)

        then:"The create view is rendered again with the correct model"
            model.dataTypeChoicesInstance!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            dataTypeChoices = new DataTypeChoices(params)

            controller.save(dataTypeChoices)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/dataTypeChoices/show/1'
            controller.flash.message != null
            DataTypeChoices.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def dataTypeChoices = new DataTypeChoices(params)
            controller.show(dataTypeChoices)

        then:"A model is populated containing the domain instance"
            model.dataTypeChoicesInstance == dataTypeChoices
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def dataTypeChoices = new DataTypeChoices(params)
            controller.edit(dataTypeChoices)

        then:"A model is populated containing the domain instance"
            model.dataTypeChoicesInstance == dataTypeChoices
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            controller.update(null)

        then:"A 404 error is returned"
            status == 404

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def dataTypeChoices = new DataTypeChoices()
            dataTypeChoices.validate()
            controller.update(dataTypeChoices)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.dataTypeChoicesInstance == dataTypeChoices

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            dataTypeChoices = new DataTypeChoices(params).save(flush: true)
            controller.update(dataTypeChoices)

        then:"A redirect is issues to the show action"
            response.redirectedUrl == "/dataTypeChoices/show/$dataTypeChoices.id"
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
            def dataTypeChoices = new DataTypeChoices(params).save(flush: true)

        then:"It exists"
            DataTypeChoices.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(dataTypeChoices)

        then:"The instance is deleted"
            DataTypeChoices.count() == 0
            response.redirectedUrl == '/dataTypeChoices/index'
            flash.message != null
    }
}
