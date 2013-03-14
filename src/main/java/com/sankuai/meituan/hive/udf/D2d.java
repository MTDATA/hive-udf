package com.sankuai.meituan.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Cast datekey to date, or otherwise
 * datekey to date, eg : select dt, d2d(dt, "", "-") from log.blog where dt=20120909 and hour='01' limit 3;
 * date to datekey, eg : select regdate, d2d(regdate, '-', '') from dim.user limit 3;
 */
public final class D2d extends UDF {
    public String evaluate(final String s, final String sourceSep, final String targetSep) {
        if (s == null) {
            return null;
        }
        try {
            Date d = new SimpleDateFormat("yyyy" + sourceSep + "MM" + sourceSep + "dd").parse(s);
            return new SimpleDateFormat("yyyy" + targetSep + "MM" + targetSep + "dd").format(d);
        } catch (Exception ex) {
            System.out.println("d2d Exception:" + s + " parse error");
        }
        return null;
    }
}

