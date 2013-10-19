package com.augurworks.web

class TeamMember {
	
	String name
	String position
	String subPosition
	String email
	String description
	

    static constraints = {
    	name blank: false
		position blank: false
		email email true
	}
}
