/*
 * Copyright (c) 2010-2012 meituan.com
 * All rights reserved.
 * 
 */
package com.sankuai.meituan.hive.udf;

import com.sankuai.meituan.hive.util.DateUtil;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;

import java.util.Date;

/**
 * extract udf
 * http://dev.mysql.com/doc/refman/5.1/en/date-and-time-functions.html#function_extract
 *
 * @author chenchun
 * @version 1.0
 * @created 2013-08-27
 */
@Description(name = "extract",
        value = "_FUNC_(timeStr, timeUnit) - returns time unit of timeStr",
        extended = "extract('2013-08-23 15:20:34', YEAR) = 2013\n" +
                "extract('2013-08-23 15:20:34', MONTH) = 7\n" +
                "extract('2013-08-23 15:20:34', WEEK_OF_YEAR) = 34\n" +
                "extract('2013-08-23 15:20:34', WEEK_OF_MONTH) = 4\n" +
                "extract('2013-08-23 15:20:34', DATE) = 23\n" +
                "extract('2013-08-23 15:20:34', DAY_OF_MONTH) = 23\n" +
                "extract('2013-08-23 15:20:34', DAY_OF_YEAR) = 235\n" +
                "extract('2013-08-23 15:20:34', DAY_OF_WEEK) = 6\n" +
                "extract('2013-08-23 15:20:34', DAY_OF_WEEK_IN_MONTH) = 4\n" +
                "extract('2013-08-23 15:20:34', AM_PM) = 1\n" +
                "extract('2013-08-23 15:20:34', HOUR) = 3\n" +
                "extract('2013-08-23 15:20:34', HOUR_OF_DAY) = 15\n" +
                "extract('2013-08-23 15:20:34', MINUTE) = 20\n" +
                "extract('2013-08-23 15:20:34', SECOND) = 34\n" +
                "extract('2013-08-23 15:20:34', MILLISECOND) = 0\n" +
                "extract('2013-08-23 15:20:34', YEAR_MONTH) = 201308\n" +
                "extract('2013-08-23 15:20:34', YEAR_DAY) = 201323\n" +
                "extract('2013-08-23 15:20:34', MONTH_DAY) = 823\n" +
                "extract('2013-08-23 15:20:34', TIME) = 152034\n" +
                "extract('2013-08-23', MONTH_DAY) = 823\n" +
                "extract(20130823, MONTH_DAY) = 823\n")
public class UDFExtract extends UDF {

    public IntWritable evaluate(final String timeStr, final String part) {
        if (timeStr == null || part == null) {
            return null;
        }
        Date date = DateUtil.parse(timeStr);
        if (date == null) {
            return null;
        }
        DateUtil.TimeUnit timeUnit = DateUtil.TimeUnit.getTimeUnit(part);
        if (timeUnit == null) {
            return null;
        }
        IntWritable result = new IntWritable();
        result.set(DateUtil.extract(date, timeUnit));
        return result;
    }


}
