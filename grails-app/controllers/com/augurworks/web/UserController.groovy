package com.augurworks.web

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugins.springsecurity.Secured
import grails.plugins.springsecurity.SpringSecurityService;

@Transactional(readOnly = true)
@Secured(['ROLE_ADMIN'])
class UserController {
	
	def springSecurityService

    def scaffold = true
	
	@Secured(['ROLE_ADMIN', 'ROLE_USER'])
	def settings() {
		[user: User.findByUsername(springSecurityService.authentication.name)]
	}
}
