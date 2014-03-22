package com.augurworks.web

class ClearCacheJob {
    static triggers = {
      cron '0 0 0/2 * * ?'
    }

	def splineService
	
    def execute() {
        log.info 'Clearing cache'
		splineService.clearCache()
    }
}
