package com.augurworks.web

class HomeController {
	
	def springSecurityService

    def index() {
		
		[service : springSecurityService]
	}

    def about() {
		def members = TeamMember.findAll()
		for (member in members) {
			String currentDir = new File(".").getAbsolutePath()
			def input = new File(currentDir + "/web-app/images/" + member.imageName)
			if (!input.exists() || member.imageName.size() == 0) {
				member.imageName = 'no-picture.gif'
			}
		}
		[service : springSecurityService, members : members]
	}
}
