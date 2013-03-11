package com.sankuai.meituan.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * Cast datekey to date, eg: 2012-08-23 -> 20120823
 */

public final class Date2DateKey extends UDF {
    public String evaluate(final String s) {
        return new D2d().evaluate(s, "-", "");
    }
}
