import com.augurworks.web.Role
import com.augurworks.web.StockTicker
import com.augurworks.web.User
import com.augurworks.web.UserRole
import org.codehaus.groovy.grails.web.context.ServletContextHolder

class BootStrap {

	def init = { servletContext ->
			def adminRole = new Role(authority: 'Admin').save(flush: true)
			def userRole = new Role(authority: 'User').save(flush: true)
			def admin = new User(username:'Admin', password:'admin', enabled:true).save()
			def user = new User(username:'User', password:'user', enabled:true).save()
			def adminUserRole = new UserRole(user: admin, role: adminRole).save()
			def userUserRole = new UserRole(user: user, role: userRole).save()

			List files = ['amex', 'nasdaq', 'nyse']
			files.each {
				String path =  servletContext.getRealPath('/resources/' + it + '.csv')
				new File(path).toCsvReader(['skipLines' : 1]).eachLine { tokens ->
					int year = 0
					if (tokens[5].isNumber()) {
						year = tokens[5].toInteger()
					}
					try {
						new StockTicker(symbol: tokens[0], name: tokens[1], lastSale: tokens[2].toDouble(), marketCap: tokens[3].toDouble(), IPOYear: year, sector: tokens[6], industry: tokens[7]).save()
					} catch(e) {
						println e.toString()
					}
				}
			}
	}
	def destroy = {
	}
}
