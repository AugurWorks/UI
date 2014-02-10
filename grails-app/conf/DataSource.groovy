dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
//dataSource_MySQL {
//	pooled = true
//	driverClassName = "com.mysql.jdbc.Driver"
//	dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
//}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory' // Hibernate 3
//    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
}

// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
//        dataSource_MySQL {
//			url = "jdbc:mysql://ec2-107-20-152-208.compute-1.amazonaws.com/augurworks?useUnicode=yes&characterEncoding=UTF-8"
//			username = "root"
//			password = "augurworks"
//        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
//        dataSource_MySQL {
//			url = "jdbc:mysql://ec2-107-20-152-208.compute-1.amazonaws.com/augurworks?useUnicode=yes&characterEncoding=UTF-8"
//			username = "root"
//			password = "augurworks"
//        }
    }
    production {
        dataSource {
            dbCreate = "create-drop"
            url = "jdbc:h2:./prodDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
			properties {
			   maxActive = -1
			   minEvictableIdleTimeMillis=1800000
			   timeBetweenEvictionRunsMillis=1800000
			   numTestsPerEvictionRun=3
			   testOnBorrow=true
			   testWhileIdle=true
			   testOnReturn=false
			   validationQuery="SELECT 1"
			   jdbcInterceptors="ConnectionState"
			}
        }
//        dataSource_MySQL {
//			url = "jdbc:mysql://ec2-107-20-152-208.compute-1.amazonaws.com/augurworks?useUnicode=yes&characterEncoding=UTF-8"
//			username = "root"
//			password = "augurworks"
//            properties {
//               maxActive = -1
//               minEvictableIdleTimeMillis=1800000
//               timeBetweenEvictionRunsMillis=1800000
//               numTestsPerEvictionRun=3
//               testOnBorrow=true
//               testWhileIdle=true
//               testOnReturn=false
//               validationQuery="SELECT 1"
//               jdbcInterceptors="ConnectionState"
//            }
//        }
    }
}
