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

	public void doLogin() {
		manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		CookieHandler.setDefault(manager);

		URL loginUrl = new URL(LOGIN_URL);
		URLConnection loginConnection = loginUrl.openConnection();
		loginConnection.getContent();
	}

	public void doLogout() {
		CookieStore cookieJar =  manager.getCookieStore();
		List <HttpCookie> cookies = cookieJar.getCookies();

		URL logoutUrl = new URL(LOGOUT_URL);
		HttpURLConnection logoutConnection = (HttpURLConnection) logoutUrl.openConnection();
		logoutConnection.setRequestProperty("Cookie",
				StringUtils.join(cookies, ","));
		logoutConnection.connect();
	}

	def queryInfinite(String etext, String minDate, String maxDate) {
		try {
			CookieStore cookieJar =  manager.getCookieStore();
			List <HttpCookie> cookies = cookieJar.getCookies();

			URL loginUrl = new URL(POST_URL);
			HttpURLConnection knowledgeConnection = (HttpURLConnection) loginUrl.openConnection();

			knowledgeConnection.setDoOutput(true);
			knowledgeConnection.setRequestProperty("Content-Type", "application/json");
			knowledgeConnection.setRequestProperty("Accept", "application/json");
			knowledgeConnection.setRequestMethod("POST");
			knowledgeConnection.setRequestProperty("Cookie",
				StringUtils.join(cookies, ","));
			knowledgeConnection.connect();

			String query = "{\"qt\": [{\"etext\":\"" + etext + "\"},{\"time\": {\"min\":\"" + minDate + "\"" +
				",\"max\":\"" + maxDate + "\"}}],\"output\": {\"format\": \"json\"}}";

			log.info("Searching infinite for query: " + query);
			byte[] outputBytes = query.getBytes("UTF-8");
			OutputStream os = knowledgeConnection.getOutputStream();
			os.write(outputBytes);
			os.close();

			String output = readResponse(knowledgeConnection);
			output = output.replaceAll("(\\\\\\\")", "'");
			return output;
		} catch (Exception e) {
			e.printStackTrace();
			println e;
		}
	}

	private String readResponse(HttpURLConnection connection) {
		InputStream inStream = connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
		String result, line = reader.readLine();
		result = line;
		while((line=reader.readLine()) != null){
			result+=line;
		}
		return result;
	}

    def serviceMethod() {

    }
}
