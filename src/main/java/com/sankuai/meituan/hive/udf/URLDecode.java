package com.sankuai.meituan.hive.udf;

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

    /**
     * 默认编码utf-8
     * 
     * @param key
     * @return
     */
    public static String evaluate(final String key) {
        return evaluate(key, "utf-8");
    }

    public static String evaluate(final String key, final String enc) {
        try {
            return URLDecoder.decode(key, enc);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}