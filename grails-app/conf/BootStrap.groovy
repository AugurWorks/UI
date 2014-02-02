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
				def tres = new DataType(name:'Treasury Rates', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'Treasury Rates', unit: '%').save()
				tres.addToDataChoices(name: '30 Year CMT Maturity Rate', key: 'FRED/DGS30.json?')
				tres.addToDataChoices(name: '20 Year CMT Maturity Rate', key: 'FRED/DGS20.json?')
				tres.addToDataChoices(name: '10 Year CMT Maturity Rate', key: 'FRED/DGS10.json?')
				tres.addToDataChoices(name: '5 Year CMT Maturity Rate', key: 'FRED/DGS5.json?')
				tres.addToDataChoices(name: '3 Year CMT Maturity Rate', key: 'FRED/DGS3.json?')
				tres.addToDataChoices(name: '2 Year CMT Maturity Rate', key: 'FRED/DGS2.json?')
				def tips = new DataType(name:'Treasury Inflation Protected Rates', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'Treasury Rates', unit: '%').save()
				tips.addToDataChoices(name: '5 Yr Inflation-Indexed Security', key: 'FRED/DFII5.json?')
				tips.addToDataChoices(name: '7 Yr Inflation-Indexed Security', key: 'FRED/DFII7.json?')
				tips.addToDataChoices(name: '10 Yr Inflation-Indexed Security', key: 'FRED/DFII10.json?')
				tips.addToDataChoices(name: '20 Yr Inflation-Indexed Security', key: 'FRED/DFII20.json?')
				tips.addToDataChoices(name: '30 Yr  Inflation-Indexed Security', key: 'FRED/DFII30.json?')
				def dep = new DataType(name:'Deposit Rates', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'Deposit Rates', unit: '%').save()
				dep.addToDataChoices(name: 'Money Market Accounts', key: 'FRED/BNKMMAW156N.json?')
				dep.addToDataChoices(name: 'Checking Accounts', key: 'FRED/BNKICRW156N.json?')
				dep.addToDataChoices(name: '1 Month CD', key: 'FRED/DCD1M.json?')
				dep.addToDataChoices(name: '3 Month CD', key: 'FRED/DCD90.json?')
				dep.addToDataChoices(name: '6 Month CD', key: 'FRED/DCD6M.json?')
				dep.addToDataChoices(name: '1 Year Term Deposit', key: 'FRED/BNK1YRW156N.json?')
				dep.addToDataChoices(name: '5 Year Term Deposit', key: 'FRED/BNK5YRW156N.json?')
				def bor = new DataType(name:'Borrowing Rates', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'Borrowing Rates', unit: '%').save()
				bor.addToDataChoices(name: 'Bank Prime Loan Rate', key: 'FRED/DPRIME.json?')
				bor.addToDataChoices(name: 'New Car Average Finance Rate', key: 'FRED/TERMAFCNCNSA.json?')
				bor.addToDataChoices(name: 'Comm. Bank Interest Rate on Credit Card Plans', key: 'FRED/TERMCBCCALLNS.json?')
				bor.addToDataChoices(name: 'Finance Rate on Personal Loans, 24 M. Loan', key: 'FRED/TERMCBPER24NS.json?')
				def mort = new DataType(name:'Mortgage Rates', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'Mortgage Rates', unit: '%').save()
				mort.addToDataChoices(name: '30 Year Fixed Rate', key: 'FMAC/FIX30YR.json?')
				mort.addToDataChoices(name: '15 Year Fixed Rate', key: 'FMAC/FIX15YR.json?')
				mort.addToDataChoices(name: '5 Year Fixed Rate', key: 'FMAC/FIX5YR.json?')
				mort.addToDataChoices(name: '1 Year Fixed Rate', key: 'FMAC/FIX1YR.json?')
				def libor = new DataType(name:'LIBOR Swap Rates', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'LIBOR Fixings', unit: '%').save()
				libor.addToDataChoices(name: '2 Year Swap Rate', key: 'FRED/DSWP2.json?')
				libor.addToDataChoices(name: '3 Year Swap Rate', key: 'FRED/DSWP3.json?')
				libor.addToDataChoices(name: '5 Year Swap Rate', key: 'FRED/DSWP5.json?')
				libor.addToDataChoices(name: '7 Year Swap Rate', key: 'FRED/DSWP7.json?')
				libor.addToDataChoices(name: '10 Year Swap Rate', key: 'FRED/DSWP10.json?')
				libor.addToDataChoices(name: '30 Year Swap Rate', key: 'FRED/DSWP30.json?')
				def cur = new DataType(name:'Currency Exchange Rates', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'Currency Exchange Rates', unit: 'Per USD').save()
				cur.addToDataChoices(name: 'USD vs Argentinian Peso', key: 'QUANDL/USDARS.json?')
				cur.addToDataChoices(name: 'USD vs Australian Dollar', key: 'QUANDL/USDAUD.json?')
				cur.addToDataChoices(name: 'USD vs Brazilian Real', key: 'QUANDL/USDBRL.json?')
				cur.addToDataChoices(name: 'USD vs Canadian Dollar', key: 'QUANDL/USDCAD.json?')
				cur.addToDataChoices(name: 'USD vs Swiss Franc', key: 'QUANDL/USDCHF.json?')
				cur.addToDataChoices(name: 'USD vs Chinese Yuan Renminbi', key: 'QUANDL/USDCNY.json?')
				cur.addToDataChoices(name: 'USD vs Danish Krone', key: 'QUANDL/USDDKK.json?')
				cur.addToDataChoices(name: 'USD vs Euro', key: 'QUANDL/USDEUR.json?')
				cur.addToDataChoices(name: 'USD vs Pound Sterling', key: 'QUANDL/USDGBP.json?')
				cur.addToDataChoices(name: 'USD vs Indonesian Rupiah', key: 'QUANDL/USDIDR.json?')
				cur.addToDataChoices(name: 'USD vs Israeli New Shequel', key: 'QUANDL/USDILS.json?')
				cur.addToDataChoices(name: 'USD vs Indian Rupee', key: 'QUANDL/USDINR.json?')
				cur.addToDataChoices(name: 'USD vs Japanese Yen', key: 'QUANDL/USDJPY.json?')
				cur.addToDataChoices(name: 'USD vs Mexican New Peso', key: 'QUANDL/USDMXN.json?')
				cur.addToDataChoices(name: 'USD vs Malaysian Ringgit', key: 'QUANDL/USDMYR.json?')
				cur.addToDataChoices(name: 'USD vs Norwegian Krone', key: 'QUANDL/USDNOK.json?')
				cur.addToDataChoices(name: 'USD vs New Zealand Dollar', key: 'QUANDL/USDNZD.json?')
				cur.addToDataChoices(name: 'USD vs Philippine Peso', key: 'QUANDL/USDPHP.json?')
				cur.addToDataChoices(name: 'USD vs Russian Rouble', key: 'QUANDL/USDRUB.json?')
				cur.addToDataChoices(name: 'USD vs Swedish Krona', key: 'QUANDL/USDSEK.json?')
				cur.addToDataChoices(name: 'USD vs Thai Baht', key: 'QUANDL/USDTHB.json?')
				cur.addToDataChoices(name: 'USD vs New Turkish Lira', key: 'QUANDL/USDTRY.json?')
				def ore = new DataType(name:'Industrial Metals', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'Industrial Metals', unit: '$/mt').save()
				ore.addToDataChoices(name: 'Aluminum Price', key: 'OFDP/ALUMINIUM_21.json?')
				ore.addToDataChoices(name: 'Cobalt Price', key: 'OFDP/COBALT_51.json?')
				ore.addToDataChoices(name: 'Aluminum Price', key: 'OFDP/ALUMINIUM_21.json?')
				ore.addToDataChoices(name: 'Copper Price', key: 'OFDP/COPPER_6.json?')
				ore.addToDataChoices(name: 'Iron Ore Price', key: 'ODA/PIORECR_USD.json?')
				ore.addToDataChoices(name: 'Lead Price', key: 'OFDP/LEAD_31.json?')
				ore.addToDataChoices(name: 'Molybdenum Price', key: 'OFDP/MOLYBDENUM_56.json?')
				ore.addToDataChoices(name: 'Nickel Price', key: 'OFDP/NICKEL_41.json?')
				ore.addToDataChoices(name: 'Steel Billet Price', key: 'OFDP/STEELBILLET_46.json?')
				ore.addToDataChoices(name: 'Tin Price', key: 'OFDP/TIN_36.json?')
				ore.addToDataChoices(name: 'Zinc Price', key: 'OFDP/ZINC_26.json?')
				def rare = new DataType(name:'Rare Metals', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'Rare Metals', unit: '$/toz').save()
				rare.addToDataChoices(name: 'Gold Price', key: 'OFDP/GOLD_2.json?')
				rare.addToDataChoices(name: 'Silver Price', key: 'OFDP/SILVER_5.json?')
				rare.addToDataChoices(name: 'Platinum Price', key: 'JOHNMATT/PLAT.json?')
				rare.addToDataChoices(name: 'Palladium Price', key: 'JOHNMATT/PALL.json?')
				rare.addToDataChoices(name: 'Iridium Price', key: 'JOHNMATT/IRID.json?')
				rare.addToDataChoices(name: 'Rhodium Price', key: 'JOHNMATT/RHOD.json?')
				rare.addToDataChoices(name: 'Ruthenium Price', key: 'JOHNMATT/RUTH.json?')
				def eia = new DataType(name:'Energy Information Admin.', valueType:'Number', optionNum: 1, serviceName: 'eia', label: 'Energy Information Admin.', unit: '').save()
				eia.addToDataChoices(name: 'US Gasoline Prices, Weekly', key: 'PET.EMM_EPM0_PTE_NUS_DPG.W')
				eia.addToDataChoices(name: 'US Oil Production, Monthly', key: 'PET.MCRFPUS2.M')
				eia.addToDataChoices(name: 'Price of Natural Gas Exports, Monthly', key: 'NG.N9130US3.M')
				eia.addToDataChoices(name: 'U.S. Natural Gas Marketed Production, Monthly', key: 'NG.N9050US2.M')
				eia.addToDataChoices(name: 'Total U.S. Coal Electric Power Use (BTUs), Monthly', key: 'ELEC.CONS_TOT_BTU.COW-US-98.M')
				eia.addToDataChoices(name: 'Total U.S. Petroleum Liquid Elec. Power Use (BTUs), Monthly', key: 'ELEC.CONS_TOT_BTU.PEL-US-98.M')
				eia.addToDataChoices(name: 'Total U.S. Petroleum Coke Elec. Power Use (BTUs), Monthly', key: 'ELEC.CONS_TOT_BTU.PC-US-98.M')
				eia.addToDataChoices(name: 'Total U.S. Nat. Gas Elec. Power Use (BTUs), Monthly', key: 'ELEC.CONS_TOT_BTU.NG-US-98.M')
				eia.addToDataChoices(name: 'US Crude Oil Imports, Weekly', key: 'PET.WTTIMUS2.W')
				eia.addToDataChoices(name: 'Net US Crude Oil Imports, Weekly', key: 'PET.WCRNTUS2.W')
				eia.addToDataChoices(name: 'U.S. Current-Dollar GDP, Annual', key: 'SEDS.GDPRV.US.A')
				eia.addToDataChoices(name: 'U.S. Average Energy Price, Annual', key: 'SEDS.TETCD.US.A')
				eia.addToDataChoices(name: 'U.S. Coal Energy Price, Annual', key: 'SEDS.CLTCD.US.A')
				eia.addToDataChoices(name: 'U.S. Net Energy Generation, Monthly', key: 'ELEC.GEN.ALL-US-99.M')
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
