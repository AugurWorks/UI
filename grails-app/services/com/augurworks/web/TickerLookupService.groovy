package com.augurworks.web

import grails.transaction.Transactional
import groovy.json.JsonSlurper

@Transactional
class TickerLookupService {
	
	def stockLookup(String query) {
		String url = 'http://d.yimg.com/autoc.finance.yahoo.com/autoc?query=' + query.replaceAll(" ", "%20") + '&callback=YAHOO.Finance.SymbolSuggest.ssCallback';
		URL urlCon;
		def result;
		urlCon = new URL(url);
		try {
			URLConnection con = urlCon.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			def slurper = new JsonSlurper();
			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				result = slurper.parseText(inputLine.substring(39, inputLine.length() - 1))
			}
			br.close();
			result.ResultSet.Result
		} catch (FileNotFoundException e) {
			[]
		}
	}

    def serviceMethod() {

    }
}
