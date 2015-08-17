package com.augurworks.web;

import org.apache.commons.io.IOUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

public class PrefService {

    private static final Logger log = LoggerFactory.getLogger(PrefService.class);

    private static final String SENTIMENT_ANALYSIS_TYPE_PREF = "SENTIMENT_ANALYSIS_TYPE";
    private static final String SENTIMENT_ANALYSIS_TYPE_DEFAULT = "entity";

    private static Properties reloadPrefs() throws Exception {
        File propsFile = new File("augurworks.prefs");
        System.out.println(propsFile.getAbsolutePath());
        InputStream fis;
        Properties props = new Properties()
        try {
            fis = new FileInputStream(propsFile);
            props.load(fis);
        } finally {
            IOUtils.closeQuietly(fis);
        }
        return props;
    }

    public static String getSentimentAnalysisType() {
        try {
            Properties prefs = reloadPrefs();
            return prefs.getProperty(SENTIMENT_ANALYSIS_TYPE_PREF, SENTIMENT_ANALYSIS_TYPE_DEFAULT)
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Unable to read pref " + SENTIMENT_ANALYSIS_TYPE_PREF +
                    ", using default value of " + SENTIMENT_ANALYSIS_TYPE_DEFAULT);
            return SENTIMENT_ANALYSIS_TYPE_DEFAULT;
        }
    }

}
