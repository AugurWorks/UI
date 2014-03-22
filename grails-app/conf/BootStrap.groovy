import com.augurworks.web.Aggregation
import com.augurworks.web.Algorithm
import com.augurworks.web.DataController
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
				def user = new User(username:"Trial", password:"user", email: 'alfred@augurworks.com', enabled:true).save(flush: true, failOnError: true)
				def user2 = new User(username:"Brian", password:"brian", email: 'brian@augurworks.com', enabled:true).save(flush: true, failOnError: true)
				UserRole.create(admin, adminRole, true)
				UserRole.create(user, userRole, true)
				UserRole.create(user2, userRole, true)
				String brian = 'Brian Conn is a recent MIT graduate with two majors in Physics and General Mathematics and two minors in Business Management and Economics. He took his senior fall off to continue working for a Federal IT contracting company where he worked as a summer intern. He also has a coding background from personal projects and previous jobs.'
				new TeamMember(name:'Brian Conn', position:'Chief Strategic Officer', subPosition:'Developer and Financial Analyst', emailAddress:'brian@augurworks.com', imageName:'Brian_Gray.jpg', description:brian).save()
				String stephen = 'Stephen Freiberg recently graduated from MIT with a Computer Science major and a Math minor. He currently works as a quality engineer in the bay area.'
				new TeamMember(name:'Stephen Freiberg', position:'Chief Technology Officer', subPosition:'Lead Developer', emailAddress:'stephen@augurworks.com', imageName:'Stephen_Gray.jpg', description:stephen).save()
				String drew = 'Drew Showers is a business-focused IT professional who has worked in both Federal and Commercial industries with extensive experience in software solution sales. He graduated from Rowan University with a BS in Computer Science and a minor in Mathematics and attended the University of Pennsylvania earning an MSE degree in Systems Engineering with a focus in Operations Research.'
				new TeamMember(name:'Drew Showers', position:'President and CEO', subPosition:'Infrastructure and Sentiment Analysis', emailAddress:'drew@augurworks.com', imageName:'Drew_Gray.jpg', description:drew).save()
				
				
				new Aggregation(name: 'Day Value', val: 1).save()
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
				
				def tips = new DataType(name:'Treas. Inflation Protected', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'Treasury Rates', unit: '%').save()
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
				crops.addToDataChoices(name: 'Soybean Futures', key: 'OFDP/FUTURE_S1', unit: 'cts/bu', dataCol: 4)
				crops.addToDataChoices(name: 'Soybean Meal Futures', key: 'OFDP/FUTURE_SM1', unit: '$/st', dataCol: 4)
				crops.addToDataChoices(name: 'Soybean Oil Futures', key: 'OFDP/FUTURE_BO1', unit: 'cts/lbs', dataCol: 4)
				crops.addToDataChoices(name: 'Wheat Futures', key: 'OFDP/FUTURE_W1', unit: 'cts/bu', dataCol: 4)
				
				def softs = new DataType(name:'Agriculture Softs', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'Softs').save()
				softs.addToDataChoices(name: 'ICE Cocoa Futures', key: 'OFDP/FUTURE_CC1', unit: '$/mt', url: 'http://www.quandl.com/OFDP/FUTURE_CC1', dataCol: 4)
				softs.addToDataChoices(name: 'ICE Coffee Futures', key: 'OFDP/FUTURE_KC1', unit: 'cts/lb', url: 'http://www.quandl.com/OFDP/FUTURE_KC1', dataCol: 4)
				softs.addToDataChoices(name: 'ICE Cotton No. 2 Futures', key: 'OFDP/FUTURE_CT1', unit: 'cts/lb', url: 'http://www.quandl.com/OFDP/FUTURE_CT1', dataCol: 4)
				softs.addToDataChoices(name: 'ICE Sugar No. 11 Futures', key: 'OFDP/FUTURE_SB1', unit: 'cts/lb', url: 'http://www.quandl.com/OFDP/FUTURE_SB1', dataCol: 4)
				softs.addToDataChoices(name: 'Tea', key: 'ODA/PTEA_USD', unit: 'cts/kg', url: 'http://www.quandl.com/ODA/PTEA_USD')
				softs.addToDataChoices(name: 'Tobacco', key: 'WORLDBANK/WLD_TOBAC_US', unit: '$/mt', url: 'http://www.quandl.com/WORLDBANK/WLD_TOBAC_US')
				
				def fruits = new DataType(name:'Fruits and Nuts', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'Fruits and Nuts').save()
				fruits.addToDataChoices(name: 'Bananas', key: 'ODA/PBANSOP_USD', unit: '$/mt', url: 'http://www.quandl.com/ODA/PBANSOP_USD')
				fruits.addToDataChoices(name: 'Oranges', key: 'ODA/PORANG_USD', unit: '$/mt', url: 'http://www.quandl.com/ODA/PORANG_USD')
				fruits.addToDataChoices(name: 'ICE Orange Juice Futures', key: 'OFDP/FUTURE_OJ1', unit: 'cts/lb', url: 'http://www.quandl.com/OFDP/FUTURE_OJ1', dataCol: 4)
				fruits.addToDataChoices(name: 'Peanuts', key: 'ODA/PGNUTS_USD', unit: '$/mt', url: 'http://www.quandl.com/ODA/PGNUTS_USD')
				
				def oils = new DataType(name:'Vegetable Oils', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'Vegetable Oils').save()
				oils.addToDataChoices(name: 'Corn Oil', key: 'WSJ/CORN_OIL', unit: 'cts/lb', url: 'http://www.quandl.com/WSJ/CORN_OIL')
				oils.addToDataChoices(name: 'Olive Oil, Monthly', key: 'ODA/POLVOIL_USD', unit: '$/mt', url: 'http://www.quandl.com/ODA/POLVOIL_USD')
				oils.addToDataChoices(name: 'Palm Oil, Monthly', key: 'ODA/PPOIL_USD', unit: '$/mt', url: 'http://www.quandl.com/ODA/PPOIL_USD')
				oils.addToDataChoices(name: 'Sunflower Oil, Monthly', key: 'ODA/PSUNO_USD', unit: '$/mt', url: 'http://www.quandl.com/ODA/PSUNO_USD')
				oils.addToDataChoices(name: 'Groundnut Oil, Monthly', key: 'WORLDBANK/WLD_GRNUT_OIL', unit: '$/mt', url: 'http://www.quandl.com/WORLDBANK/WLD_GRNUT_OIL')
				oils.addToDataChoices(name: 'Palmkernal Oil, Monthly', key: 'WORLDBANK/WLD_PLMKRNL_OIL', unit: '$/mt', url: 'http://www.quandl.com/WORLDBANK/WLD_PLMKRNL_OIL')
				oils.addToDataChoices(name: 'Rapeseed Oil, Monthly', key: 'ODA/PROIL_USD', unit: '$/mt', url: 'http://www.quandl.com/ODA/PROIL_USD')
				
				def forestry = new DataType(name:'Forestry', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'Forestry').save()
				forestry.addToDataChoices(name: 'Rubber, Monthly', key: 'ODA/PRUBB_USD', unit: 'cts/lb', url: 'http://www.quandl.com/ODA/PRUBB_USD')
				forestry.addToDataChoices(name: 'Copra, Monthly', key: 'WORLDBANK/WLD_COPRA', unit: '$/mt', url: 'http://www.quandl.com/WORLDBANK/WLD_COPRA')
				forestry.addToDataChoices(name: 'Soft Logs, Monthly', key: 'ODA/PLOGORE_USD', unit: '$/cbm', url: 'http://www.quandl.com/ODA/PLOGORE_USD')
				forestry.addToDataChoices(name: 'Hard Logs, Monthly', key: 'ODA/PLOGSK_USD', unit: '$/cbm', url: 'http://www.quandl.com/ODA/PLOGSK_USD')
				forestry.addToDataChoices(name: 'Hard Sawnwood, Monthly', key: 'ODA/PSAWMAL_USD', unit: '$/mt', url: 'http://www.quandl.com/ODA/PSAWMAL_USD')
				forestry.addToDataChoices(name: 'Soft Sawnwood, Monthly', key: 'ODA/PSAWORE_USD', unit: '$/mt', url: 'http://www.quandl.com/ODA/PSAWORE_USD')
				forestry.addToDataChoices(name: 'Plywood, Monthly', key: 'WORLDBANK/WLD_PLYWOOD', unit: 'cts/sht', url: 'http://www.quandl.com/WORLDBANK/WLD_PLYWOOD')
				forestry.addToDataChoices(name: 'Wood Pulp, Monthly', key: 'WORLDBANK/WLD_WOODPULP', unit: '$/mt', url: 'http://www.quandl.com/WORLDBANK/WLD_WOODPULP')
				
				def fert = new DataType(name:'Fertilizers', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'Fertilizers').save()
				fert.addToDataChoices(name: 'Diammonium Phosphate, Monthly', key: 'WORLDBANK/WLD_DAP', unit: '$/mt', url: 'http://www.quandl.com/WORLDBANK/WLD_DAP')
				fert.addToDataChoices(name: 'Phosphate Rock, Monthly', key: 'WORLDBANK/WLD_PHOSROCK', unit: '$/mt', url: 'http://www.quandl.com/WORLDBANK/WLD_PHOSROCK')
				fert.addToDataChoices(name: 'Potassium Chloride, Monthly', key: 'WORLDBANK/WLD_POTASH', unit: '$/mt', url: 'http://www.quandl.com/WORLDBANK/WLD_POTASH')
				fert.addToDataChoices(name: 'Triple Superphosphate, Monthly', key: 'WORLDBANK/WLD_TSP', unit: '$/mt', url: 'http://www.quandl.com/WORLDBANK/WLD_TSP')
				fert.addToDataChoices(name: 'Urea, Monthly', key: 'WORLDBANK/WLD_UREA_EE_BULK', unit: '$/mt', url: 'http://www.quandl.com/WORLDBANK/WLD_UREA_EE_BULK')
				
				def comm = new DataType(name:'Commodity Indexes', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: 'Commodity Indexes').save()
				comm.addToDataChoices(name: 'Energy, Monthly', key: 'WORLDBANK/WLD_IENERGY', unit: 'index', url: 'http://www.quandl.com/WORLDBANK/WLD_IENERGY')
				comm.addToDataChoices(name: 'Non-Energy, Monthly', key: 'WORLDBANK/WLD_INONFUEL', unit: 'index', url: 'http://www.quandl.com/WORLDBANK/WLD_INONFUEL')
				comm.addToDataChoices(name: 'Metals and Minerals, Monthly', key: 'WORLDBANK/WLD_IMETMIN', unit: 'index', url: 'http://www.quandl.com/WORLDBANK/WLD_IMETMIN')
				comm.addToDataChoices(name: 'Agriculture, Monthly', key: 'WORLDBANK/WLD_IAGRICULTURE', unit: 'index', url: 'http://www.quandl.com/WORLDBANK/WLD_IAGRICULTURE')
				comm.addToDataChoices(name: 'Beverages, Monthly', key: 'WORLDBANK/WLD_IBEVERAGES', unit: 'index', url: 'http://www.quandl.com/WORLDBANK/WLD_IBEVERAGES')
				comm.addToDataChoices(name: 'Food, Monthly', key: 'WORLDBANK/WLD_IFOOD', unit: 'index', url: 'http://www.quandl.com/WORLDBANK/WLD_IFOOD')
				comm.addToDataChoices(name: 'Fats and Oils, Monthly', key: 'WORLDBANK/WLD_IFATS_OILS', unit: 'index', url: 'http://www.quandl.com/WORLDBANK/WLD_IFATS_OILS')
				comm.addToDataChoices(name: 'Grains, Monthly', key: 'WORLDBANK/WLD_IGRAINS', unit: 'index', url: 'http://www.quandl.com/WORLDBANK/WLD_IGRAINS')
				comm.addToDataChoices(name: 'Other Food Items, Monthly', key: 'WORLDBANK/WLD_IOTHERFOOD', unit: 'index', url: 'http://www.quandl.com/WORLDBANK/WLD_IOTHERFOOD')
				comm.addToDataChoices(name: 'Agricultural Raw Materials, Monthly', key: 'WORLDBANK/WLD_IRAW_MATERIAL', unit: 'index', url: 'http://www.quandl.com/WORLDBANK/WLD_IRAW_MATERIAL')
				comm.addToDataChoices(name: 'Timber, Monthly', key: 'WORLDBANK/WLD_ITIMBER', unit: 'index', url: 'http://www.quandl.com/WORLDBANK/WLD_ITIMBER')
				comm.addToDataChoices(name: 'Other Raw Materials, Monthly', key: 'WORLDBANK/WLD_IOTHERRAWMAT', unit: 'index', url: 'http://www.quandl.com/WORLDBANK/WLD_IOTHERRAWMAT')
				comm.addToDataChoices(name: 'Fertilizers, Monthly', key: 'WORLDBANK/WLD_IFERTILIZERS', unit: 'index', url: 'http://www.quandl.com/WORLDBANK/WLD_IFERTILIZERS')
				
				/*def  = new DataType(name:'', valueType:'Number', optionNum: 1, serviceName: 'quandl', label: '').save()
				.addToDataChoices(name: '', key: '', unit: '', url: 'http://www.quandl.com/')*/
				
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
				//new DataType(name:'Twitter', valueType:'Text', optionNum: 1, serviceName: 'twitter').save()
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
				DataController dataC = new DataController();
				
				dataC.recordRequest([0: [name: 'USO', dataType: 'Stock Price', startDate: dataC.halfYearAgo(), endDate: dataC.today(), agg: 'Day Value', custom: '', page: 'index', reqId: -1]], 'index')
				dataC.recordRequest([0: [name: 'USO', dataType: 'Stock Price', startDate: dataC.halfYearAgo(), endDate: dataC.today(), agg: 'Day Value', custom: '', page: 'calendar', reqId: -1]], 'calendar')
				dataC.recordRequest([0: [name: 'USO', dataType: 'Stock Price', startDate: dataC.halfYearAgo(), endDate: dataC.today(), agg: 'Day Value', custom: '', page: 'correlation', reqId: -1],
				   1: [name: 'DJIA', dataType: 'Stock Price', startDate: dataC.halfYearAgo(), endDate: dataC.today(), agg: 'Day Value', custom: '', page: 'correlation', reqId: -1]], 'correlation')
				dataC.recordRequest([0: [name: 'USO', dataType: 'Stock Price', startDate: dataC.halfYearAgo(), endDate: dataC.today(), agg: 'Day Value', custom: '', page: 'covariance', reqId: -1],
				   1: [name: 'DJIA', dataType: 'Stock Price', startDate: dataC.halfYearAgo(), endDate: dataC.today(), agg: 'Day Value', custom: '', page: 'covariance', reqId: -1]], 'covariance')
				dataC.recordRequest([0: [name: 'Oil', dataType: 'Entities', startDate: dataC.halfYearAgo(), endDate: dataC.today(), page: 'sentiment', reqId: -1]], 'sentiment')
				dataC.recordRequest([0: [name: 'Oil', dataType: 'Entities', startDate: dataC.halfYearAgo(), endDate: dataC.today(), page: 'matrix', reqId: -1]], 'matrix')
				dataC.recordRequest([0: [name: 'Oil', dataType: 'Entities', startDate: dataC.halfYearAgo(), endDate: dataC.today(), page: 'node', reqId: -1]], 'node')
				
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
