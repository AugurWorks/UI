package com.augurworks.web



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class StocksController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Stocks.list(params), model:[stocksInstanceCount: Stocks.count()]
    }

    def show(Stocks stocksInstance) {
        respond stocksInstance
    }

    def create() {
        respond new Stocks(params)
    }

    @Transactional
    def save(Stocks stocksInstance) {
        if (stocksInstance == null) {
            notFound()
            return
        }

        if (stocksInstance.hasErrors()) {
            respond stocksInstance.errors, view:'create'
            return
        }

        stocksInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.created.message', args: [message(code: 'stocksInstance.label', default: 'Stocks'), stocksInstance.id])
                redirect stocksInstance
            }
            '*' { respond stocksInstance, [status: CREATED] }
        }
    }

    def edit(Stocks stocksInstance) {
        respond stocksInstance
    }

    @Transactional
    def update(Stocks stocksInstance) {
        if (stocksInstance == null) {
            notFound()
            return
        }

        if (stocksInstance.hasErrors()) {
            respond stocksInstance.errors, view:'edit'
            return
        }

        stocksInstance.save flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Stocks.label', default: 'Stocks'), stocksInstance.id])
                redirect stocksInstance
            }
            '*'{ respond stocksInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Stocks stocksInstance) {

        if (stocksInstance == null) {
            notFound()
            return
        }

        stocksInstance.delete flush:true

        request.withFormat {
            form {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Stocks.label', default: 'Stocks'), stocksInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'stocksInstance.label', default: 'Stocks'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
