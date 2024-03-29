// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [
    all:           '*/*',
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    hal:           ['application/hal+json','application/hal+xml'],
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

grails.app.context = "/"

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        filteringCodecForContentType {
            //'text/html' = 'html'
        }
    }
    mail {
      host = "smtp.gmail.com"
      port = 465
      from = "alfred@augurworks.com"
      username = "alfred@augurworks.com"
      password = "BatMobile"
      props = ["mail.smtp.auth":"true",
           "mail.smtp.socketFactory.port":"465",
           "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
           "mail.smtp.socketFactory.fallback":"false"]
    }
}

grails.cache.config = {
	cache {
		name 'url'
		overflowToDisk true
		maxElementsInMemory 0
		maxElementsOnDisk 100000
	}
}

grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

aw {
	sentimentAnalysisType = 'ENTITY'
}

environments {
    development {
        grails.logging.jul.usebridge = true
    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console appender:
    //
	appenders {
		console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n'), threshold: org.apache.log4j.Level.INFO
		file name: 'file', layout:pattern(conversionPattern: '%d{dd-MM-yyyy HH:mm:ss} %p %c{2} - %m%n'), file: './AugurWorks.log', threshold: org.apache.log4j.Level.DEBUG
    }
	root {
		org.apache.log4j.Level.OFF
	}
	
	environments {
		development {
			info	stdout:	'com.augurworks', 
							additivity: false
		}
	}
    debug	file:	'com.augurworks',
					additivity: false
}

twitter {
	'default' {
		OAuthConsumerKey = 'iLQm2VraHR2MLe2mwS2mvg'
		OAuthConsumerSecret = 'BNx6ij82BtzMVoEEvk6Y4xBDRjdAV8arWysSU7YmGA'
		OAuthAccessToken = '1135747976-OyBreDVCujPbbfPLLUnV19kJ21qVTwvyxbKMnyn'
		OAuthAccessTokenSecret = 'yWa2a6HOCdWRd5K7Svb6KXb9G7MCd65xIUvDT8rCYo'
	}
}

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.augurworks.web.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.augurworks.web.UserRole'
grails.plugin.springsecurity.authority.className = 'com.augurworks.web.Role'
grails.plugin.springsecurity.successHandler.defaultTargetUrl = '/home'
grails.views.gsp.encoding="UTF-8"

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	'/**':								['permitAll'],
	'/user/**':							['ROLE_ADMIN'],
	'/role/**':							['ROLE_ADMIN'],
	'/algorithm/**':					['ROLE_ADMIN'],
	'/analysis/**':						['ROLE_ADMIN', 'ROLE_USER'],
	'/correlation/**':					['ROLE_ADMIN'],
	'/correlationSet/**':				['ROLE_ADMIN'],
	'/data/**':							['ROLE_ADMIN', 'ROLE_USER'],
	'/dataSet/**':						['ROLE_ADMIN'],
	'/dataTypeChoices/**':				['ROLE_ADMIN'],
	'/dataType/**':						['ROLE_ADMIN'],
	'/graphs/**':						['ROLE_ADMIN', 'ROLE_USER'],
	'/home/controllers/**':				['ROLE_ADMIN'],
	'/home/feed/**':					['ROLE_ADMIN', 'ROLE_USER'],
	'/home/getFeed/**':					['ROLE_ADMIN', 'ROLE_USER'],
	'/neuralNet/**':					['ROLE_ADMIN'],
	'/request/**':						['ROLE_ADMIN'],
	'/search/**':						['ROLE_ADMIN'],
	'/stockTicker/**':					['ROLE_ADMIN'],
	'/teamMember/**':					['ROLE_ADMIN'],
	'/user/**':							['ROLE_ADMIN'],
	'/user/settings/**':				['ROLE_ADMIN', 'ROLE_USER']
]
