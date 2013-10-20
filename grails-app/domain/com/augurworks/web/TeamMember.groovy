package com.augurworks.web

class TeamMember {
	
	String name
	String position
	String subPosition
	String emailAddress
	String imageName = ''
	String description
	

    static constraints = {
    	name blank: false
		position blank: false
		subPosition()
		emailAddress email: true
		imageName()
		description(maxSize: 1000)
	}
}
