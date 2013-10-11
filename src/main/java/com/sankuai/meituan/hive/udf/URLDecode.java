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
            return "";
        }
    }

    public static void main(String args[]) {
        System.out.println(evaluate("%e9%98%bf%e9%87%8c%e9%83%8e%e7%83%a4%"));
    }

}