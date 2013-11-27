package com.augurworks.web

class RegisterController {

	def springSecurityService
	
	def mailService
	
    def index() {
		
		[service : springSecurityService]
	}
	
	def register = {
		if (params.j_password != params.j_password2 || params.j_password.length() < 7 || params.j_username.length() < 3) {
			flash.message = 'You broke our JavaScript!'
			redirect(controller: 'register')
		} else if (User.findByUsername(params.j_username)) {
			flash.message = 'Username taken.'
			redirect(controller: 'register')
		} else {
			def user = new User(username:params.j_username, password:params.j_password, email: params.email, enabled:true).save(flush: true, failOnError: true)
			UserRole.create(user, Role.findByAuthority('ROLE_USER'), true)
			mailService.sendMail {
				to params.email
				subject 'AugurWorks Registration'
				html '<h1>Welcome!</h1><p>Thank you for registering for the AugurWorks alpha release! You username is: ' + params.j_username + '. Please let us know what you think at <a href="mailto:feedback@augurworks.com">feedback@augurworks.com</a>.<br><br>Thanks,<br>--The AugurWorks Team</p>'
			}
			redirect(controller: 'home')
		}
	}
}
