import com.augurworks.web.Role
import com.augurworks.web.StockTicker
import com.augurworks.web.TeamMember
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
			String brian = 'Brian Conn is also a recent MIT graduate with two majors in Physics and General Mathematics and two minors in Business Management and Economics. He took his senior fall off to continue working for a Federal IT Consultant where he worked as a summer intern. He also has a coding background from personal projects and previous jobs.'
			new TeamMember(name:'Brian Conn', position:'Chief Strategic Officer', subPosition:'Developer and Fiancial Analyst', emailAddress:'brian@augurworks.com', description:brian).save()
			String stephen = 'Stephen Freiberg recently graduated from MIT with a Computer Science major and a math minor. He’s had extensive experience in many programming languages and algorithms. His multiple internships have been with software companies doing a range of coding projects.'
			new TeamMember(name:'Stephen Freiberg', position:'Chief Technology Officer', subPosition:'Lead Developer', emailAddress:'stephen@augurworks.com', description:stephen).save()
			String drew = 'Drew Showers is a business-focused IT professional who has worked in both Federal and Commercial industries with extensive experience in software solution sales. He graduated from Rowan University with a BS in Computer Science and a minor in Mathematics and attended the University of Pennsylvania earning an MSE degree in Systems Engineering with a focus in Operations Research.'
			new TeamMember(name:'Drew Showers', position:'President and CEO', subPosition:'Sentiment Analysis', emailAddress:'drew@augurworks.com', description:drew).save()

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
