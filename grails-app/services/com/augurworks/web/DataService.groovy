package com.augurworks.web

import java.text.SimpleDateFormat;

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class DataService {

	def getStockService
	def infiniteService
	def tickerLookupService
	def twitterService
	def EIAService
	def splineService
	def quandlService
	
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    /**
     * Generic data function which takes request parameters. Performs basic date validation and
     * redirects to data services.
     * @return Map containing all data from all services
     */
    def getData(req) {
        def root;
        try {
            def rawData = [:]
			def temp = []
			//GParsPool.withPool(req.keySet().size()) {
				//temp = req.keySet().collectParallel { val ->
				temp = req.keySet().collect { val ->
					if (val.isInteger()) {
		                def startDate, endDate;
		                if (!validateDates(req[val].startDate, req[val].endDate)) {
		                    startDate = halfYearAgo();
		                    endDate = today();
		                } else {
		                    startDate = formatDate(req[val].startDate);
		                    endDate = formatDate(req[val].endDate);
		                }
		                def dataType = DataType.findByName(req[val].dataType)
						try {
							"${dataType.serviceName}Data"(rawData, val, req[val], dataType)
						} catch (e) {
							log.error req[val]
							log.error 'getData parallel: ' + e
							null
						}
					}
	            }
            //}
			temp.each { if (it) rawData << it }
            root = [root : rawData]
        } catch (e) {
			log.error 'GetData: ' + e
            root = [root: [errorBoolean: true, message: "Internal Error: " + e.getMessage(), error: e]]
        }
        //dothing(root)
    }

    def dothing(root) {
        DataTransferObjects.fromJsonString((root as JSON).toString())
    }

    /**
     * Function to retrieve stock data from the getStockService.
     * @param rawData - Full data map to push data into
     * @param key - Key value
     * @param vals - Values corresponding to key
     */
	@Deprecated
    def stockData(rawData, key, vals, dataType) {
        int startMonth = Integer.parseInt(vals.startDate.split("/")[0]) - 1
        int startDay = Integer.parseInt(vals.startDate.split("/")[1])
        int startYear = Integer.parseInt(vals.startDate.split("/")[2])
        int endMonth = Integer.parseInt(vals.endDate.split("/")[0]) - 1
        int endDay = Integer.parseInt(vals.endDate.split("/")[1])
        int endYear = Integer.parseInt(vals.endDate.split("/")[2])
        def data = getStockService.getStock(vals.name, startMonth, startDay, startYear, endMonth, endDay, endYear, "d", vals.startDate, vals.endDate, vals.agg)
        def finalData = [:]
        boolean valid = true;
        if (dataType.optionNum.toInteger() == 1) {
            double yesterday = -1
            for (day in data.keySet().iterator().reverse()) {
                if (data[day] != 'Stock Not Found') {
                    if (yesterday != -1) {
                        finalData << [(day) : data[day]]
                    } else {
                        yesterday = 0
                    }
                } else {
                    valid = false;
                }
            }
        }
        if (valid) {
            def temp = ['dates' : finalData]
            temp << ['metadata' : ['label' : dataType.label, 'unit' : splineService.checkUnits(dataType.unit, vals.agg), 'req': vals, valid: true, 'errors': [:]]]
            [(key) : temp]
        } else {
            [(key) : ['metadata' : ['label' : dataType.label, 'unit' : splineService.checkUnits(dataType.unit, vals.agg), 'req': vals, valid: false, 'errors': ['stock': ['message': 'Stock Not Found']]]]]
        }
    }

    /**
     * Function to retrieve infinite data from the infiniteService.
     * @param rawData - Full data map to push data into
     * @param key - Key value
     * @param vals - Values corresponding to key
     */
    def infiniteData(rawData, key, vals, dataType) {
        def keyword;
        def raw_keyword = URLDecoder.decode(vals.name, "UTF-8");
        if (!validateKeyword(raw_keyword)) {
            flash.message = "Empty keyword. Defaulting to oil.";
            keyword = "Oil";
        } else {
            keyword = raw_keyword;
        }
        if (dataType.optionNum == 1) {
            def finalData = [:]
            def query = infiniteService.queryInfinite(keyword, vals.startDate, vals.endDate)
            for (i in query['data']) {
                def date = new Date(i.publishedDate).format('yyyy-MM-dd') + ' 4:00PM'
                double sentiment = 0
                for (ent in i.entities) {
                    if (ent.positiveSentiment) {
                        sentiment += ent.positiveSentiment * ent.significance
                    }
                    if (ent.negativeSentiment) {
                        sentiment -= ent.negativeSentiment * ent.significance
                    }
                }
                if (!finalData[date]) {
                    finalData[date] = sentiment
                } else {
                    finalData[date] = sentiment + finalData[date].toDouble()
                }
            }
            def temp = ['dates' : finalData]
            temp << ['metadata' : ['label' : dataType.label, 'unit' : splineService.checkUnits(dataType.unit, vals.agg), 'req': vals, valid: true]]
            [(key) : temp]
        } else if (dataType.optionNum == 2) {
            def meta = [['title' : 'Relevance', 'id' : 'score'], ['title' : 'Total Sentiment', 'id' : 'totalSentiment'], ['title' : 'Positive Sentiment', 'id' : 'pos'], ['title' : 'Negative Sentiment', 'id' : 'neg'], ['title' : 'Published', 'id' : 'publishedDate'], ['title' : 'Title', 'id' : 'title', 'url' : 'url'], ['title' : 'Description', 'id' : 'description']]
            def sub = [['title' : 'Name', 'id' : 'disambiguated_name'], ['title' : 'Frequency', 'id' : 'frequency'], ['title' : 'Type', 'id' : 'type'], ['title' : 'Sentiment', 'id' : 'sentiment'], ['title' : 'Significance', 'id' : 'significance']]
            def temp = [:];
			try {
				temp = [(key): infiniteService.queryInfinite(keyword, vals.startDate, vals.endDate)]
				for (int j = 0; j < temp[key]['data'].size(); j++) {
					def i = temp[key]['data'][j]
					double sentiment = 0
					double pos = 0
					double neg = 0
					for (ent in i.entities) {
						if (ent.positiveSentiment) {
							sentiment += ent.positiveSentiment * ent.significance
							pos += ent.positiveSentiment * ent.significance
						}
						if (ent.negativeSentiment) {
							sentiment += ent.negativeSentiment * ent.significance
							neg += ent.negativeSentiment * ent.significance
						}
					}
					temp[key]['data'][j] << ['totalSentiment': sentiment]
					temp[key]['data'][j] << ['pos': pos]
					temp[key]['data'][j] << ['neg': neg]
				}
				temp[key]['metadata'] = ['req': vals, 'title' : 'title', 'icon' : 'totalSentiment', 'data' : meta, 'sub' : ['title' : 'Entities', 'id' : 'entities', 'data' : sub, 'errors': [:]]]
			} catch (e) {
				log.error 'infiniteData: ' + e
				temp = ['errorBoolean': true, 'error': e]
			}
			temp
        }
    }

    def twitterData(rawData, key, vals, dataType) {
        def data = [:]
        if (dataType.optionNum == 1) {
            [(key) : ['data' : twitterService.twitterSearch(vals.name, vals.startDate, vals.endDate, 100)], 'metadata' : ['errors': [:], 'title' : 'text', 'data' : [['title' : 'Username', 'id' : 'username'], ['title' : 'Date', 'id' : 'date'], ['title' : 'Retweeted', 'id' : 'retweeted'], ['title' : 'Favorited', 'id' : 'favorited'], ['title' : 'Tweet', 'id' : 'text']]]]
        }
    }

    def eiaData(rawData, key, vals, dataType) {
        if (dataType.optionNum == 1) {
            def temp = [(key) : EIAService.getSeries(DataTypeChoices.findByNameIlike(vals.name).key, vals.startDate, vals.endDate, vals.agg)]
			temp[key]['metadata'] << ['errors': [:], 'req': vals, 'unit' : splineService.checkUnits(dataType.unit, vals.agg)]
			temp
        }
    }

    def quandlData(rawData, key, vals, dataType) {
        def data = [:]
        def choice = DataTypeChoices.findByNameIlike(vals.name)
        if (dataType.optionNum == 1) {
            def unit = choice.unit ? choice.unit : dataType.unit
            def temp = [(key) : quandlService.getData(choice.key, vals.startDate, vals.endDate, vals.agg, choice.dataCol)]
			temp[key]['metadata'] << ['errors': [:], 'req': vals, 'unit' : splineService.checkUnits(unit, vals.agg)]
			temp
        }
    }

    def quandlStockData(rawData, key, vals, dataType) {
        def data = [:]
        def choice = StockTicker.findBySymbolIlike(vals.name)
        if (dataType.optionNum == 1) {
            def unit = dataType.unit
            def temp = [(key) : quandlService.getData(choice.code, vals.startDate, vals.endDate, vals.agg, choice.col)]
			temp[key]['metadata'] << ['errors': [:], 'req': vals, 'unit' : splineService.checkUnits(unit, vals.agg)]
			temp
        }
    }

    def getTicker() {
        def result = tickerLookupService.stockLookup(params.query)
        render((["root" : result] as JSON).toString())
    }

    private boolean validateKeyword(String keyword) {
        return keyword != null;
    }

    private String formatDate(String date) {
        Date d = new Date(date);
        return DATE_FORMAT.format(d);
    }

    private boolean validateDates(String startDate, String endDate) {
        try {
            Date start = new Date(startDate);
            Date end = new Date(endDate);
            if (start.after(end)) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private String daysAgo(days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days);
        return DATE_FORMAT.format(cal.getTime());
    }
}
