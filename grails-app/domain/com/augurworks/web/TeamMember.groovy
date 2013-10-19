package com.augurworks.web

class TeamMember {
	
	String name
	String position
	String subPosition
	String emailAddress
	String description
	

    static constraints = {
    	name blank: false
		position blank: false
		subPosition()
		emailAddress email: true
		description type: "text"
	}
}
