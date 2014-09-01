package com.augurworks.web

class ReadNetsJob {
    static triggers = {
      simple repeatInterval: 60000 // execute job once in 5 seconds
    }
	
	def grailsApplication
	def neuralNetService

    def execute() {
		def dir = grailsApplication.mainContext.getResource('neuralnet').file;
		dir.eachFile {
			if (it.name.endsWith('.augout')) {
				it.renameTo(it.parent + '/../data/' + it.name);
				neuralNetService.readResult(it);
			}
		}
    }
}