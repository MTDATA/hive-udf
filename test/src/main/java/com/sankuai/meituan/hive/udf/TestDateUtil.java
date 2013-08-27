/*
 * Copyright (c) 2010-2012 meituan.com
 * All rights reserved.
 * 
 */
package com.sankuai.meituan.hive.udf;

import com.sankuai.meituan.hive.util.DateUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 *
 * @author chenchun
 * @version 1.0
 * @created 2013-08-27
 */
public class TestDateUtil {

    @Test
    public void testParse() {
        Date d1 = DateUtil.parse("2013-08-23 15:20:34"), d2 = DateUtil.parse("20130823152034"),
                d3 = DateUtil.parse("2013-08-23"), d4 = DateUtil.parse("20130823");
        Assert.assertEquals(d1, d2);
        Assert.assertEquals(d3, d4);
        Assert.assertFalse(d1.equals(d3));
    }

    @Test
    public void testExtract() {
        Date now = DateUtil.parse("2013-08-23 15:20:34");
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.YEAR), 2013);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.MONTH), 7);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.WEEK_OF_YEAR), 34);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.WEEK_OF_MONTH), 4);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.DATE), 23);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.DAY_OF_MONTH), 23);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.DAY_OF_YEAR), 235);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.DAY_OF_WEEK), 6);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.DAY_OF_WEEK_IN_MONTH), 4);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.AM_PM), 1);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.HOUR), 3);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.HOUR_OF_DAY), 15);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.MINUTE), 20);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.SECOND), 34);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.MILLISECOND), 0);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.YEAR_MONTH), 201308);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.YEAR_DAY), 201323);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.MONTH_DAY), 823);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.TIME), 152034);
//        for (DateUtil.TimeUnit timeUnit : DateUtil.TimeUnit.values()) {
//            System.out.println("Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit." +
//                    timeUnit.name() + "), " + DateUtil.extract(now, timeUnit) + ");");
//            System.out.println("\"extract('" + DateUtil.format(now, DateUtil.DATE_TIME_FORMAT_L) + "', " +
//                    timeUnit.name() + ") = " + DateUtil.extract(now, timeUnit) + "\\n\" + ");
//        }
        now = DateUtil.parse("2013-08-23");
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.YEAR), 2013);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.MONTH), 7);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.WEEK_OF_YEAR), 34);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.WEEK_OF_MONTH), 4);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.DATE), 23);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.DAY_OF_MONTH), 23);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.DAY_OF_YEAR), 235);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.DAY_OF_WEEK), 6);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.DAY_OF_WEEK_IN_MONTH), 4);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.AM_PM), 0);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.HOUR), 0);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.HOUR_OF_DAY), 0);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.MINUTE), 0);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.SECOND), 0);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.MILLISECOND), 0);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.YEAR_MONTH), 201308);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.YEAR_DAY), 201323);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.MONTH_DAY), 823);
        Assert.assertEquals(DateUtil.extract(now, DateUtil.TimeUnit.TIME), 0);
    }
}
