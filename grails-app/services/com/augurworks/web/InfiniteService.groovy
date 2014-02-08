package com.augurworks.web

import java.util.concurrent.TimeUnit;

import grails.transaction.Transactional
import groovy.json.JsonSlurper

import org.apache.commons.lang.StringUtils
import org.apache.log4j.Logger

import com.augurworks.web.prefs.WebPrefs
import com.augurworks.web.query.InfiniteQuery
import com.augurworks.web.query.InfiniteQueryTerm
import com.augurworks.web.util.EvictingCache

@Transactional
class InfiniteService {
	private static final Logger log = Logger.getLogger(InfiniteService.class);
	private static final INFINITE_URL = WebPrefs.getInfiniteUrl();
	private static final LOGIN_URL = INFINITE_URL + "/api/auth/login/" + WebPrefs.getUserLoginString();
	private static final POST_URL = INFINITE_URL + "/api/knowledge/document/query/50ecaf5ae4b0ea25955cdfb8";
	private static final LOGOUT_URL = INFINITE_URL + "/api/auth/logout";
	private final CookieManager manager;
	private final EvictingCache<InfiniteQuery, Object> cache;
	private static final long CACHE_DURATION = 600000; // 10 minutes in ms
	
	public InfiniteService() {
		this.manager = new CookieManager();
		this.cache = new EvictingCache<InfiniteQuery, Object>();
	}

	private void initializeCookieManager() {
		manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		CookieHandler.setDefault(manager);
	}

	public void doLogin() {
		initializeCookieManager();
		URLConnection loginConnection = connectToUrl(LOGIN_URL);
		loginConnection.getContent();
	}

	public void doLogout() {
		HttpURLConnection logoutConnection = connectToUrl(LOGOUT_URL);
		attachCookies(logoutConnection);
		logoutConnection.connect();
	}

	def queryInfinite(String etext, String minDate, String maxDate) {
		try {
			doQueryUnsafe(etext, minDate, maxDate, 0);
		} catch (Exception e) {
			log.error(e.toString());
			throw e;
		}
	}

	private doQueryUnsafe(String etext, String minDate, String maxDate, int counter) {
		println "Cookies before login check " + getValidCookies();
		if (!loggedIn()) {
			println "Not logged in ... logging in now"
			doLogin();
			println "Login complete. Cookies are " + getValidCookies();
		}
		HttpURLConnection knowledgeConnection = connectToUrl(POST_URL);
		preparePostAndConnect(knowledgeConnection);
		String query = prepareInfiniteQuery(etext, minDate, maxDate);
		Object result = cache.getIfPresentAndValid(query);
		if (result != null) {
			log.info("Cache hit for query " + query);
			println "Cache hit for query " + query
			return result;
		}
		println "Cache miss for query " + query
		log.info("Cache miss for query " + query);
		log.info("Searching infinite for query: " + query);
		sendQueryToConnection(knowledgeConnection, query);

		String output = readResponseIntoString(knowledgeConnection);
		def raw = [minData : parseToObject(output)];
		def out = parseToObject(output)
		if (out?.response?.message == 'Cookie session expired or never existed, please login first' && counter < 10) {
			counter++
			println 'Infinite cookie session failed. Retrying. Attempt: ' + counter
			println "Current cookies are " + getValidCookies()
			Thread.sleep(1000);
			doLogin();
			println "Cookies after login are " + getValidCookies();
			out = doQueryUnsafe(etext, minDate, maxDate, counter)
		}
		cache.put(query, out, CACHE_DURATION);
		return out
	}

	private boolean loggedIn() {
		return manager.getCookieStore().getCookies().size() != 0;
	}

	private HttpURLConnection connectToUrl(String url) {
		URL urlConn = new URL(url);
		return (HttpURLConnection) urlConn.openConnection();
	}

	private Object parseToObject(String string) {
		def slurper = new JsonSlurper();
		return slurper.parseText(string);
	}

	private void sendQueryToConnection(HttpURLConnection conn, String query) {
		byte[] outputBytes = query.getBytes("UTF-8");
		OutputStream os = conn.getOutputStream();
		os.write(outputBytes);
		os.close();
	}

	private String prepareInfiniteQuery(String etext, String minDate, String maxDate) {
		InfiniteQueryTerm term = InfiniteQueryTerm.builder().withEtext(etext)
			.withStartTime(minDate).withEndTime(maxDate).build();
		InfiniteQuery query = InfiniteQuery.builder().withTerm(term).build();
		return query.getQuery();
	}

	private List<HttpCookie> getValidCookies() {
		CookieStore cookieJar =  manager.getCookieStore();
		return cookieJar.getCookies();
	}

	private void attachCookies(HttpURLConnection conn) {
		List<HttpCookie> cookies = getValidCookies();
		conn.setRequestProperty("Cookie",
			StringUtils.join(cookies, ","));
	}

	private void preparePostAndConnect(HttpURLConnection conn) {
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestMethod("POST");
		attachCookies(conn);
		conn.connect();
	}

	private String readResponseIntoString(HttpURLConnection connection) {
		InputStream inStream = connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
		String line = reader.readLine();
		StringBuilder result = new StringBuilder(line);
		while((line = reader.readLine()) != null){
			result = result.append(line);
		}
		return result.toString();
	}
}
