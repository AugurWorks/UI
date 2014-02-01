package com.augurworks.web

class ClearCacheJob {
    static triggers = {
      simple startDelay: 100000, repeatInterval: 2 * 3600 * 1000
    }

	def splineService
	
    def execute() {
        println 'Clearing cache'
		splineService.clearCache()
    }
}
