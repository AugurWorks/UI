package com.augurworks.web

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugins.springsecurity.Secured

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN'])
class NeuralNetResultController {

    static scaffold = true
}
