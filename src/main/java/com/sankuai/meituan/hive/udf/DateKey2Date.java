package com.sankuai.meituan.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * Cast datekey to date, eg: 20120823 -> 2012-08-23
 */

public final class DateKey2Date extends UDF {
    public String evaluate(final String s) {
        return new D2d().evaluate(s, "", "-");
    }
}
