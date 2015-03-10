package com.augurworks.web

class ClearCacheJob {
    static triggers = {
		cron startDelay: 60000, cronExpression: '0 0 0/1 * * ?'
    }

	def splineService
	
    def execute() {
        log.info 'Clearing cache'
		splineService.clearCache();
    }
}
