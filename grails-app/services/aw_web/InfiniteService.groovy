package aw_web

import grails.transaction.Transactional

import org.apache.commons.lang.StringUtils
import org.apache.log4j.Logger

@Transactional
class InfiniteService {
	private static final Logger log = Logger.getLogger(InfiniteService.class);
	private static final INFINITE_URL = "http://ec2-107-20-152-208.compute-1.amazonaws.com";
	private static final LOGIN_URL = INFINITE_URL +
		"/api/auth/login/stephen@augurworks.com/7msCYOF1lJ3aoclnjbV6KH1I9P2Xn5ht42IQx9JaRRo%3D";
	private static final POST_URL = INFINITE_URL +
		"/api/knowledge/document/query/50ecaf5ae4b0ea25955cdfb8";
	private static final LOGOUT_URL = INFINITE_URL + "/api/auth/logout";
	private static final CookieManager manager = new CookieManager();

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

	public String queryInfinite(String etext, String minDate, String maxDate) {
		try {
			doQueryUnsafe(etext, minDate, maxDate);
		} catch (Exception e) {
			log.error(e.getCause().getMessage());
		}
	}

	private doQueryUnsafe(String etext, String minDate, String maxDate) {
		if (!loggedIn()) {
			doLogin();
		}

		HttpURLConnection knowledgeConnection = connectToUrl(POST_URL);

		preparePostAndConnect(knowledgeConnection);

		String query = prepareInfiniteQuery(etext, minDate, maxDate);

		log.info("Searching infinite for query: " + query);
		sendQueryToConnection(knowledgeConnection, query);

		String output = readResponseIntoString(knowledgeConnection);
		output = replaceQuotes(output);
		return output;
	}

	private boolean loggedIn() {
		return manager.getCookieStore().getCookies().size() != 0;
	}

	private HttpURLConnection connectToUrl(String url) {
		URL urlConn = new URL(url);
		return (HttpURLConnection) urlConn.openConnection();
	}

	private String replaceQuotes(String quoted) {
		return quoted.replaceAll("(\\\\\\\")", "'");
	}

	private void sendQueryToConnection(HttpURLConnection conn, String query) {
		byte[] outputBytes = query.getBytes("UTF-8");
		OutputStream os = conn.getOutputStream();
		os.write(outputBytes);
		os.close();
	}

	private String prepareInfiniteQuery(String etext, String minDate, String maxDate) {
		return "{\"qt\": [{\"etext\":\"" + etext + "\"},{\"time\": {\"min\":\"" + minDate + "\"" +
				",\"max\":\"" + maxDate + "\"}}],\"output\": {\"format\": \"json\"}}";
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
