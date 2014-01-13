import com.augurworks.web.Algorithm
import com.augurworks.web.DataType
import com.augurworks.web.Role
import com.augurworks.web.StockTicker
import com.augurworks.web.TeamMember
import com.augurworks.web.User
import com.augurworks.web.UserRole
import org.codehaus.groovy.grails.web.context.ServletContextHolder

class BootStrap {

	def init = { servletContext ->
		try {
			if (Role.count() == 0) {
				def adminRole = new Role(authority: "ROLE_ADMIN").save(flush: true, failOnError: true)
				def userRole = new Role(authority: "ROLE_USER").save(flush: true, failOnError: true)
				def admin = new User(username:"Admin", password:"AWadmin!", email: 'alfred@augurworks.com', enabled: true).save(flush: true, failOnError: true)
				def user = new User(username:"User", password:"user", email: 'alfred@augurworks.com', enabled:true).save(flush: true, failOnError: true)
				def user2 = new User(username:"Brian", password:"brian", email: 'brian@augurworks.com', enabled:true).save(flush: true, failOnError: true)
				UserRole.create(admin, adminRole, true)
				UserRole.create(user, userRole, true)
				UserRole.create(user2, userRole, true)
				String brian = 'Brian Conn is a recent MIT graduate with two majors in Physics and General Mathematics and two minors in Business Management and Economics. He took his senior fall off to continue working for a Federal IT Consultant where he worked as a summer intern. He also has a coding background from personal projects and previous jobs.'
				new TeamMember(name:'Brian Conn', position:'Chief Strategic Officer', subPosition:'Developer and Financial Analyst', emailAddress:'brian@augurworks.com', imageName:'Brian.jpg', description:brian).save()
				String stephen = 'Stephen Freiberg recently graduated from MIT with a Computer Science major and a math minor. He currently works as a quality engineer in the bay area.'
				new TeamMember(name:'Stephen Freiberg', position:'Chief Technology Officer', subPosition:'Lead Developer', emailAddress:'stephen@augurworks.com', imageName:'no-picture.gif', description:stephen).save()
				String drew = 'Drew Showers is a business-focused IT professional who has worked in both Federal and Commercial industries with extensive experience in software solution sales. He graduated from Rowan University with a BS in Computer Science and a minor in Mathematics and attended the University of Pennsylvania earning an MSE degree in Systems Engineering with a focus in Operations Research.'
				new TeamMember(name:'Drew Showers', position:'President and CEO', subPosition:'Infrastructure and Sentiment Analysis', emailAddress:'drew@augurworks.com', imageName:'Drew.jpg', description:drew).save()
				
				new DataType(name:'Stock Price', valueType:'Number', optionNum: 1, serviceName: 'stock', label: 'Price', unit: '$').save()
				new DataType(name:'Stock Day Change', valueType:'Number', optionNum: 2, serviceName: 'stock', label: 'Day Change', unit: '%').save()
				new DataType(name:'Stock Period Change', valueType:'Number', optionNum: 3, serviceName: 'stock', label: 'Period Change', unit: '%').save()
				new DataType(name:'Sentiment', valueType:'Number', optionNum: 1, serviceName: 'infinite', label: 'Sentiment', unit: 'Points').save()
				new DataType(name:'Entities', valueType:'Text', optionNum: 2, serviceName: 'infinite').save()
				new DataType(name:'Twitter', valueType:'Text', optionNum: 1, serviceName: 'twitter').save()
				new Algorithm(name:'Decision Tree', minInputs: 2, dataTypes: DataType.findAllByValueType('Number')).save()
	
				/*List files = ['amex', 'nasdaq', 'nyse']
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
				}*/
			}
		} catch (Exception e) {
			log.error(e.toString())
			e.printStackTrace()
		}
	}
	def destroy = {
	}
}
