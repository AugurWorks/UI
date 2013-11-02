package com.augurworks.web
import grails.plugins.springsecurity.Secured

class HomeController {
	
	def springSecurityService
	
    def index() {
		[service : springSecurityService]
	}
	
    def about() {
		def members = TeamMember.findAll()
		ArrayList clonedMembers = []
		for (val in members) {
			def member = val.clone()
			clonedMembers.push(member)
			String currentDir = new File(".").getAbsolutePath()
			def input = new File(currentDir.substring(0, currentDir.size() - 2) + "/web-app/images/" + member.imageName)
			if (!input.exists() || member.imageName.size() == 0) {
				member.imageName = 'no-picture.gif'
			}
		}
		[service : springSecurityService, members : clonedMembers]
	}
}
