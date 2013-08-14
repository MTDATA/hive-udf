package com.sankuai.meituan.hive.udf;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * 
 * 
 * Desc 'abc%20def' -> 'abc def'
 * 
 * @author bull
 * @created 2013-8-14
 * 
 * @version 1.0
 * 
 */
public class URLDecode extends UDF {
    public URLDecode() {
    }

    public static String evaluate(final String key) {
        try {
            return URLDecoder.decode(key, "utf-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
