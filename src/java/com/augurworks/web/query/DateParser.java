package com.augurworks.web.query;

import java.text.SimpleDateFormat;
import java.util.Date;

public enum DateParser {
    FROM_LONG {
        @Override
        public Date parseString(String dateString) {
            try {
                long l = Long.parseLong(dateString);
                return new Date(l);
            } catch (Exception e) {
                throw new IllegalArgumentException("Date couldn't be parsed from " + dateString);
            }
        }
    },
    FROM_MM_DD_YYYY {
        @Override
        public Date parseString(String dateString) {
            try {
                return new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
            } catch (Exception e) {
                throw new IllegalArgumentException("Date couldn't be parsed from " + dateString);
            }
        }
    },
    FROM_MM_DD_YYYY2 {
        @Override
        public Date parseString(String dateString) {
            try {
                return new SimpleDateFormat("MM-dd-yyyy").parse(dateString);
            } catch (Exception e) {
                throw new IllegalArgumentException("Date couldn't be parsed from " + dateString);
            }
        }
    },
    ;

    public abstract Date parseString(String dateString);

    public static Date tryAllParsers(String dateString) {
        for (DateParser dp : DateParser.values()) {
            try {
                return dp.parseString(dateString);
            } catch (Exception e) {
                // try another parser
            }
        }
        throw new IllegalArgumentException("Date couldn't be parsed from " + dateString);
    }
}
