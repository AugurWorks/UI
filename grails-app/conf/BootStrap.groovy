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
				tres.addToDataChoices(name: '30 Year CMT Maturity Rate', key: 'FRED/DGS30')
				tres.addToDataChoices(name: '20 Year CMT Maturity Rate', key: 'FRED/DGS20')
				tres.addToDataChoices(name: '10 Year CMT Maturity Rate', key: 'FRED/DGS10')
				tres.addToDataChoices(name: '5 Year CMT Maturity Rate', key: 'FRED/DGS5')
				tres.addToDataChoices(name: '3 Year CMT Maturity Rate', key: 'FRED/DGS3')
				tres.addToDataChoices(name: '2 Year CMT Maturity Rate', key: 'FRED/DGS2')
				
				def tips = new DataType(name:'Treasury Inflation Protected Rates', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'Treasury Rates', unit: '%').save()
				tips.addToDataChoices(name: '5 Yr Inflation-Indexed Security', key: 'FRED/DFII5')
				tips.addToDataChoices(name: '7 Yr Inflation-Indexed Security', key: 'FRED/DFII7')
				tips.addToDataChoices(name: '10 Yr Inflation-Indexed Security', key: 'FRED/DFII10')
				tips.addToDataChoices(name: '20 Yr Inflation-Indexed Security', key: 'FRED/DFII20')
				tips.addToDataChoices(name: '30 Yr  Inflation-Indexed Security', key: 'FRED/DFII30')
				
				def dep = new DataType(name:'Deposit Rates', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'Deposit Rates', unit: '%').save()
				dep.addToDataChoices(name: 'Money Market Accounts', key: 'FRED/BNKMMAW156N')
				dep.addToDataChoices(name: 'Checking Accounts', key: 'FRED/BNKICRW156N')
				dep.addToDataChoices(name: '1 Month CD', key: 'FRED/DCD1M')
				dep.addToDataChoices(name: '3 Month CD', key: 'FRED/DCD90')
				dep.addToDataChoices(name: '6 Month CD', key: 'FRED/DCD6M')
				dep.addToDataChoices(name: '1 Year Term Deposit', key: 'FRED/BNK1YRW156N')
				dep.addToDataChoices(name: '5 Year Term Deposit', key: 'FRED/BNK5YRW156N')
				
				def bor = new DataType(name:'Borrowing Rates', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'Borrowing Rates', unit: '%').save()
				bor.addToDataChoices(name: 'Bank Prime Loan Rate', key: 'FRED/DPRIME')
				bor.addToDataChoices(name: 'New Car Average Finance Rate', key: 'FRED/TERMAFCNCNSA')
				bor.addToDataChoices(name: 'Comm. Bank Interest Rate on Credit Card Plans', key: 'FRED/TERMCBCCALLNS')
				bor.addToDataChoices(name: 'Finance Rate on Personal Loans, 24 M. Loan', key: 'FRED/TERMCBPER24NS')
				
				def mort = new DataType(name:'Mortgage Rates', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'Mortgage Rates', unit: '%').save()
				mort.addToDataChoices(name: '30 Year Fixed Rate', key: 'FMAC/FIX30YR')
				mort.addToDataChoices(name: '15 Year Fixed Rate', key: 'FMAC/FIX15YR')
				mort.addToDataChoices(name: '5 Year Fixed Rate', key: 'FMAC/FIX5YR')
				mort.addToDataChoices(name: '1 Year Fixed Rate', key: 'FMAC/FIX1YR')
				
				def libor = new DataType(name:'LIBOR Swap Rates', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'LIBOR Fixings', unit: '%').save()
				libor.addToDataChoices(name: '2 Year Swap Rate', key: 'FRED/DSWP2')
				libor.addToDataChoices(name: '3 Year Swap Rate', key: 'FRED/DSWP3')
				libor.addToDataChoices(name: '5 Year Swap Rate', key: 'FRED/DSWP5')
				libor.addToDataChoices(name: '7 Year Swap Rate', key: 'FRED/DSWP7')
				libor.addToDataChoices(name: '10 Year Swap Rate', key: 'FRED/DSWP10')
				libor.addToDataChoices(name: '30 Year Swap Rate', key: 'FRED/DSWP30')
				
				def cur = new DataType(name:'Currency Exchange Rates', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'Currency Exchange Rates', unit: 'Per USD').save()
				cur.addToDataChoices(name: 'USD vs Argentinian Peso', key: 'QUANDL/USDARS')
				cur.addToDataChoices(name: 'USD vs Australian Dollar', key: 'QUANDL/USDAUD')
				cur.addToDataChoices(name: 'USD vs Brazilian Real', key: 'QUANDL/USDBRL')
				cur.addToDataChoices(name: 'USD vs Canadian Dollar', key: 'QUANDL/USDCAD')
				cur.addToDataChoices(name: 'USD vs Swiss Franc', key: 'QUANDL/USDCHF')
				cur.addToDataChoices(name: 'USD vs Chinese Yuan Renminbi', key: 'QUANDL/USDCNY')
				cur.addToDataChoices(name: 'USD vs Danish Krone', key: 'QUANDL/USDDKK')
				cur.addToDataChoices(name: 'USD vs Euro', key: 'QUANDL/USDEUR')
				cur.addToDataChoices(name: 'USD vs Pound Sterling', key: 'QUANDL/USDGBP')
				cur.addToDataChoices(name: 'USD vs Indonesian Rupiah', key: 'QUANDL/USDIDR')
				cur.addToDataChoices(name: 'USD vs Israeli New Shequel', key: 'QUANDL/USDILS')
				cur.addToDataChoices(name: 'USD vs Indian Rupee', key: 'QUANDL/USDINR')
				cur.addToDataChoices(name: 'USD vs Japanese Yen', key: 'QUANDL/USDJPY')
				cur.addToDataChoices(name: 'USD vs Mexican New Peso', key: 'QUANDL/USDMXN')
				cur.addToDataChoices(name: 'USD vs Malaysian Ringgit', key: 'QUANDL/USDMYR')
				cur.addToDataChoices(name: 'USD vs Norwegian Krone', key: 'QUANDL/USDNOK')
				cur.addToDataChoices(name: 'USD vs New Zealand Dollar', key: 'QUANDL/USDNZD')
				cur.addToDataChoices(name: 'USD vs Philippine Peso', key: 'QUANDL/USDPHP')
				cur.addToDataChoices(name: 'USD vs Russian Rouble', key: 'QUANDL/USDRUB')
				cur.addToDataChoices(name: 'USD vs Swedish Krona', key: 'QUANDL/USDSEK')
				cur.addToDataChoices(name: 'USD vs Thai Baht', key: 'QUANDL/USDTHB')
				cur.addToDataChoices(name: 'USD vs New Turkish Lira', key: 'QUANDL/USDTRY')
				
				def ore = new DataType(name:'Industrial Metals', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'Industrial Metals', unit: '$/mt').save()
				ore.addToDataChoices(name: 'Aluminum Price', key: 'OFDP/ALUMINIUM_21')
				ore.addToDataChoices(name: 'Cobalt Price', key: 'OFDP/COBALT_51')
				ore.addToDataChoices(name: 'Aluminum Price', key: 'OFDP/ALUMINIUM_21')
				ore.addToDataChoices(name: 'Copper Price', key: 'OFDP/COPPER_6')
				ore.addToDataChoices(name: 'Iron Ore Price', key: 'ODA/PIORECR_USD')
				ore.addToDataChoices(name: 'Lead Price', key: 'OFDP/LEAD_31')
				ore.addToDataChoices(name: 'Molybdenum Price', key: 'OFDP/MOLYBDENUM_56')
				ore.addToDataChoices(name: 'Nickel Price', key: 'OFDP/NICKEL_41')
				ore.addToDataChoices(name: 'Steel Billet Price', key: 'OFDP/STEELBILLET_46')
				ore.addToDataChoices(name: 'Tin Price', key: 'OFDP/TIN_36')
				ore.addToDataChoices(name: 'Zinc Price', key: 'OFDP/ZINC_26')
				
				def rare = new DataType(name:'Rare Metals', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'Rare Metals', unit: '$/toz').save()
				rare.addToDataChoices(name: 'Gold Price', key: 'OFDP/GOLD_2')
				rare.addToDataChoices(name: 'Silver Price', key: 'OFDP/SILVER_5')
				rare.addToDataChoices(name: 'Platinum Price', key: 'JOHNMATT/PLAT')
				rare.addToDataChoices(name: 'Palladium Price', key: 'JOHNMATT/PALL')
				rare.addToDataChoices(name: 'Iridium Price', key: 'JOHNMATT/IRID')
				rare.addToDataChoices(name: 'Rhodium Price', key: 'JOHNMATT/RHOD')
				rare.addToDataChoices(name: 'Ruthenium Price', key: 'JOHNMATT/RUTH')
				
				def energy = new DataType(name:'Energy', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'Energy').save()
				energy.addToDataChoices(name: 'Crude Oil Futures, WTI', key: 'OFDP/FUTURE_CL1', unit: '$/bbl', dataCol: 4)
				energy.addToDataChoices(name: 'Crude Oil OPEC Ref. Basket Price', key: 'OPEC/ORB', unit: '$/bbl')
				energy.addToDataChoices(name: 'Natural Gas Futures', key: 'OFDP/FUTURE_NG1', unit: '$/mmBtu', dataCol: 4)
				energy.addToDataChoices(name: 'Heating Oil Futures', key: 'OFDP/FUTURE_HO1', unit: '$/gal', dataCol: 4)
				energy.addToDataChoices(name: 'Propane', key: 'WSJ/PROPANE', unit: '$/gal')
				energy.addToDataChoices(name: 'Butane', key: 'WSJ/BUTANE', unit: '$/gasl')
				energy.addToDataChoices(name: 'Denatured Fuel Ethanol Futures', key: 'OFDP/FUTURE_EH1', unit: '$/gal', dataCol: 4)
				energy.addToDataChoices(name: 'Uranium, Monthly', key: 'ODA/PURAN_USD', unit: '$/lb')
				
				def crops = new DataType(name:'Crops', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'Crops').save()
				crops.addToDataChoices(name: 'Barley, Monthly', key: 'ODA/PBARL_USD', unit: '$/mt')
				crops.addToDataChoices(name: 'Bran, Wheat Middlings', key: 'WSJ/BRAN', unit: '$/ton')
				crops.addToDataChoices(name: 'Corn Futures', key: 'OFDP/FUTURE_C1', unit: 'cts/bu', dataCol: 4)
				crops.addToDataChoices(name: 'Corn Gluten Meal', key: 'WSJ/CORN_MEAL', unit: '$/ton')
				crops.addToDataChoices(name: 'Cottonseed Meal', key: 'WSJ/CTNSD_MEAL', unit: '$/ton')
				crops.addToDataChoices(name: 'Oats Futures', key: 'OFDP/FUTURE_O1', unit: 'cts/bu', dataCol: 4)
				crops.addToDataChoices(name: 'Rough Rice Futures', key: 'OFDP/FUTURE_RR1', unit: '$/cwt', dataCol: 4)
				crops.addToDataChoices(name: 'Sorghum, Monthly', key: 'WORLDBANK/WLD_SORGHUM', unit: '$/mt')
				crops.addToDataChoices(name: 'Soybeans Futures', key: 'OFDP/FUTURE_S1', unit: 'cts/bu', dataCol: 4)
				crops.addToDataChoices(name: 'Soybean Meal Futures', key: 'OFDP/FUTURE_SM1', unit: '$/st', dataCol: 4)
				crops.addToDataChoices(name: 'Soybean Oil Futures', key: 'OFDP/FUTURE_BO1', unit: 'cts/lbs', dataCol: 4)
				crops.addToDataChoices(name: 'Wheat Futures', key: 'OFDP/FUTURE_W1', unit: 'cts/bu', dataCol: 4)
				
				def usEcon = new DataType(name:'US Growth', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'US Growth').save()
				usEcon.addToDataChoices(name: 'Industrial Production Index, Monthly', key: 'FRED/INDPRO', unit: 'index', url: 'http://www.quandl.com/FRED/INDPRO')
				usEcon.addToDataChoices(name: 'Capacity Utilization, Total Industry, Monthly', key: 'FRED/TCU', unit: '%', url: 'http://www.quandl.com/FRED/TCU')
				usEcon.addToDataChoices(name: 'Government Employees', key: 'FRED/USGOVT', unit: '1000s', url: 'http://www.quandl.com/FRED/USGOVT')
				usEcon.addToDataChoices(name: 'Avg. Hourly Wage of All Private Emply, Monthly', key: 'FRED/CES0500000003', unit: '$/hour', url: 'http://www.quandl.com/FRED/CES0500000003')
				usEcon.addToDataChoices(name: 'Avg. Weekly Hrs of Private Emply, Monthly', key: 'FRED/AWHNONAG', unit: 'Hours', url: 'http://www.quandl.com/FRED/AWHNONAG')
				usEcon.addToDataChoices(name: 'Civilian Employment-Pop. Ratio, Monthly', key: 'FRED/EMRATIO', unit: '%', url: 'http://www.quandl.com/FRED/EMRATIO')
				usEcon.addToDataChoices(name: 'Civilian Unemployment Rate, Monthly', key: 'FRED/UNRATE', unit: '%', url: 'http://www.quandl.com/FRED/UNRATE')
				usEcon.addToDataChoices(name: 'Initial Jobless Claims, Weekly', key: 'FRED/ICSA', unit: 'persons', url: 'http://www.quandl.com/FRED/ICSA')
				usEcon.addToDataChoices(name: 'Continued Jobless Claims, Weekly', key: 'FRED/CCSA', unit: 'persons', url: 'http://www.quandl.com/FRED/CCSA')
				usEcon.addToDataChoices(name: 'Mean Duration of Unemployment, Monthly', key: 'FRED/UEMPMEAN', unit: 'Weeks', url: 'http://www.quandl.com/FRED/UEMPMEAN')
				
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
