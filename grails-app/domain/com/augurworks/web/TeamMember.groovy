package com.augurworks.web
import groovy.transform.AutoClone

@AutoClone
class TeamMember {
	
	String name
	String position
	String subPosition
	String emailAddress
	String imageName = 'no-picture.gif'
	String description
	

    static constraints = {
    	name blank: false
		position blank: false
		subPosition nullable: true
		emailAddress email: true
		imageName blank: false
		description(maxSize: 1000)
	}
}
