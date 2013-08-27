/*
 * Copyright (c) 2010-2012 meituan.com
 * All rights reserved.
 * 
 */
package com.sankuai.meituan.hive.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * date utils
 *
 * @author chenchun
 * @version 1.0
 * @created 2013-08-27
 */
public class DateUtil {

    public final static String DATE_TIME_FORMAT_L = "yyyy-MM-dd HH:mm:ss";
    public final static String DATE_FORMAT_L = "yyyy-MM-dd";
    public final static String DATE_MINUTE_FORMAT_L = "yyyy-MM-dd HH:mm";
    public final static String TIME_FORMAT_L = "HH:mm:ss";

    public final static String DATE_TIME_FORMAT = "yyyyMMddHHmmss";
    public final static String DATE_FORMAT = "yyyyMMdd";
    public final static String DATE_MINUTE_FORMAT = "yyyyMMddHHmm";
    public final static String YEAR_MONTH_FORMAT = "yyyyMM";
    public final static String YEAR_DAY_FORMAT = "yyyydd";
    public final static String MONTH_DAY_FORMAT = "MMdd";
    public final static String TIME_FORMAT = "HHmmss";


    private static final Collection<String> DEFAULT_PATTERNS = Arrays.asList(DATE_TIME_FORMAT_L,
            DATE_FORMAT_L, DATE_TIME_FORMAT, DATE_FORMAT);

    /**
     * Parses the date value using <code>DEFAULT_PATTERNS</code> date formats.
     *
     * @see #DEFAULT_PATTERNS
     *
     * @param date date string
     * @return the parsed date or null
     */
    public static Date parse(String date) {
        for (String expression : DEFAULT_PATTERNS) {
            try {
                return new SimpleDateFormat(expression).parse(date);
            } catch (ParseException e) {
            }
        }
        return null;
    }

    /**
     * format date
     * @param date
     * @param expression
     * @return
     */
    public static String format(Date date, String expression) {
        try {
            return new SimpleDateFormat(expression).format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * extract time field from the given date
     * @param date
     * @param timeUnit
     * @return
     */
    public static int extract(Date date, TimeUnit timeUnit) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        switch (timeUnit) {
            case YEAR:
                return c.get(Calendar.YEAR);
            case MONTH:
                return c.get(Calendar.MONTH);
            case WEEK_OF_YEAR:
                return c.get(Calendar.WEEK_OF_YEAR);
            case WEEK_OF_MONTH:
                return c.get(Calendar.WEEK_OF_MONTH);
            case DATE:
                return c.get(Calendar.DATE);
            case DAY_OF_MONTH:
                return c.get(Calendar.DAY_OF_MONTH);
            case DAY_OF_YEAR:
                return c.get(Calendar.DAY_OF_YEAR);
            case DAY_OF_WEEK:
                return c.get(Calendar.DAY_OF_WEEK);
            case DAY_OF_WEEK_IN_MONTH:
                return c.get(Calendar.DAY_OF_WEEK_IN_MONTH);
            case AM_PM:
                return c.get(Calendar.AM_PM);
            case HOUR:
                return c.get(Calendar.HOUR);
            case HOUR_OF_DAY:
                return c.get(Calendar.HOUR_OF_DAY);
            case MINUTE:
                return c.get(Calendar.MINUTE);
            case SECOND:
                return c.get(Calendar.SECOND);
            case MILLISECOND:
                return c.get(Calendar.MILLISECOND);
            case YEAR_MONTH:
                return Integer.parseInt(format(date, YEAR_MONTH_FORMAT));
            case YEAR_DAY:
                return Integer.parseInt(format(date, YEAR_DAY_FORMAT));
            case MONTH_DAY:
                return Integer.parseInt(format(date, MONTH_DAY_FORMAT));
            case TIME:
                return Integer.parseInt(format(date, TIME_FORMAT));
        }
        return -1;
    }

    public enum TimeUnit {
        YEAR, MONTH, WEEK_OF_YEAR, WEEK_OF_MONTH, DATE, DAY_OF_MONTH, DAY_OF_YEAR, DAY_OF_WEEK,
        DAY_OF_WEEK_IN_MONTH, AM_PM, HOUR, HOUR_OF_DAY, MINUTE, SECOND, MILLISECOND,

        YEAR_MONTH, YEAR_DAY, MONTH_DAY, TIME;

        public final static Map<String, TimeUnit> timeUnitMap = new HashMap<String, TimeUnit>();

        static {
            for (TimeUnit timeUnit : TimeUnit.values()) {
                timeUnitMap.put(timeUnit.name(), timeUnit);
            }
        }

        public static TimeUnit getTimeUnit(String str) {
            return timeUnitMap.containsKey(str)? timeUnitMap.get(str) : null;
        }
    }

}
