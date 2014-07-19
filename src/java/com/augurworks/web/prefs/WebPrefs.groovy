package com.augurworks.web.prefs;

import grails.util.Environment;

public class WebPrefs {
	public static String getUserLoginString() {
		if (isProductionEnvironment()) {
			return "produser@augurworks.com/CxTVAaWURCoBxoWVQbyz6BZNGD0yk3uFGDVEL2nVyU4%3D";
		} else if (isDevelopmentEnvironment()) {
			return "stephen@augurworks.com/7msCYOF1lJ3aoclnjbV6KH1I9P2Xn5ht42IQx9JaRRo%3D";
		} else {
			throw new IllegalStateException("Cannot determine the application environment status.");
		}
	}
	
	public static boolean isProductionEnvironment() {
		return Environment.current.getName().equalsIgnoreCase("production");
	}
	
	public static boolean isDevelopmentEnvironment() {
		return Environment.current.getName().equalsIgnoreCase("development");
	}
	
	public static String getInfiniteUrl() {
		return "http://beta.augurworks.net:3088";
	}
}
