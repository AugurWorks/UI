import com.augurworks.web.Aggregation
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
				println 'Starting bootstrap'
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
				String stephen = 'Stephen Freiberg recently graduated from MIT with a Computer Science major and a Math minor. He currently works as a quality engineer in the bay area.'
				new TeamMember(name:'Stephen Freiberg', position:'Chief Technology Officer', subPosition:'Lead Developer', emailAddress:'stephen@augurworks.com', imageName:'no-picture.gif', description:stephen).save()
				String drew = 'Drew Showers is a business-focused IT professional who has worked in both Federal and Commercial industries with extensive experience in software solution sales. He graduated from Rowan University with a BS in Computer Science and a minor in Mathematics and attended the University of Pennsylvania earning an MSE degree in Systems Engineering with a focus in Operations Research.'
				new TeamMember(name:'Drew Showers', position:'President and CEO', subPosition:'Infrastructure and Sentiment Analysis', emailAddress:'drew@augurworks.com', imageName:'Drew.jpg', description:drew).save()
				
				new Aggregation(name: 'Data Value', val: 1).save()
				new Aggregation(name: 'Normalized Value', val: 2).save()
				new Aggregation(name: 'Day Change', val: 3).save()
				new Aggregation(name: 'Day Percent Change', val: 4).save()
				new Aggregation(name: 'Period Change', val: 5).save()
				new Aggregation(name: 'Period Percent Change', val: 6).save()
				
				new DataType(name:'Stock Price', valueType:'Number', optionNum: 1, serviceName: 'stock', label: 'Price', unit: '$').save()
				new DataType(name:'Sentiment', valueType:'Number', optionNum: 1, serviceName: 'infinite', label: 'Sentiment', unit: 'Points').save()
				def quandl = new DataType(name:'Treasury Rates', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'Treasury Rates', unit: '%').save()
				quandl.addToDataChoices(name: '30 Year CMT Treasury Maturity Rate', key: 'FRED/DGS30.json?')
				quandl.addToDataChoices(name: '20 Year CMT Treasury Maturity Rate', key: 'FRED/DGS20.json?')
				quandl.addToDataChoices(name: '10 Year CMT Treasury Maturity Rate', key: 'FRED/DGS10.json?')
				quandl.addToDataChoices(name: '5 Year CMT Treasury Maturity Rate', key: 'FRED/DGS5.json?')
				quandl.addToDataChoices(name: '3 Year CMT Treasury Maturity Rate', key: 'FRED/DGS3.json?')
				quandl.addToDataChoices(name: '2 Year CMT Treasury Maturity Rate', key: 'FRED/DGS2.json?')
				def eia = new DataType(name:'EIA', valueType:'Number', optionNum: 1, serviceName: 'eia', label: 'EIA', unit: '').save()
				eia.addToDataChoices(name: 'US Gasoline Prices, Weekly', key: 'PET.EMM_EPM0_PTE_NUS_DPG.W')
				eia.addToDataChoices(name: 'US Oil Production, Monthly', key: 'PET.MCRFPUS2.M')
				eia.addToDataChoices(name: 'Price of Natural Gas Exports, Monthly', key: 'NG.N9130US3.M')
				eia.addToDataChoices(name: 'U.S. Natural Gas Marketed Production, Monthly', key: 'NG.N9050US2.M')
				eia.addToDataChoices(name: 'Total U.S. Coal Electric Power Consumption (BTUs), Monthly', key: 'ELEC.CONS_TOT_BTU.COW-US-98.M')
				eia.addToDataChoices(name: 'Total U.S. Petroleum Liquid Electric Power Consumption (BTUs), Monthly', key: 'ELEC.CONS_TOT_BTU.PEL-US-98.M')
				eia.addToDataChoices(name: 'Total U.S. Petroleum Coke Electric Power Consumption (BTUs), Monthly', key: 'ELEC.CONS_TOT_BTU.PC-US-98.M')
				eia.addToDataChoices(name: 'Total U.S. Natural Gas Electric Power Consumption (BTUs), Monthly', key: 'ELEC.CONS_TOT_BTU.NG-US-98.M')
				eia.addToDataChoices(name: 'US Crude Oil Imports, Weekly', key: 'PET.WTTIMUS2.W')
				eia.addToDataChoices(name: 'Net US Crude Oil Imports, Weekly', key: 'PET.WCRNTUS2.W')
				eia.addToDataChoices(name: 'U.S. Current-Dollar GDP, Annual', key: 'SEDS.GDPRV.US.A')
				eia.addToDataChoices(name: 'U.S. Average Energy Price, Annual', key: 'SEDS.TETCD.US.A')
				eia.addToDataChoices(name: 'U.S. Coal Energy Price, Annual', key: 'SEDS.CLTCD.US.A')
				eia.addToDataChoices(name: 'U.S. Net Energy Generation, Monthly', key: 'ELEC.GEN.ALL-US-99.M')
				eia.save()
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
				println 'Done bootstrapping'
			}
		} catch (Exception e) {
			log.error(e.toString())
			e.printStackTrace()
		}
	}
	def destroy = {
	}
}
