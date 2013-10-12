package com.sankuai.meituan.hive.udf;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * 
 * 将符合jsonArray形式字符串转化为json字符串列表
 * 
 * @author bull
 * @created 2013-8-30
 * 
 * @version 1.0
 */
public class JsonArrayStr2List extends UDF {

    public static Object evaluate(final String key) {
        if (key == null || key == "") {
            return null;
        }
        try {
            JSONArray ja = new JSONArray(key);
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < ja.length(); i++) {
                list.add(ja.getJSONObject(i).toString());
            }
            return list;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * Test
     * 
     * @param args
     */
    public static void main(String[] args) {
        String s = "[{'stid':'109566862909184_c0','dealid':8851766},{'stid':'109566862909184_c1','dealid':2503862},{'stid':'109566862909184_c2','dealid':5955868},{'stid':'109566862909184_c3','dealid':9333108},{'stid':'109566862909184_c4','dealid':3545132}]";
        List<String> ls = (List<String>) evaluate(s);
        for (String item : ls) {
            System.out.println(item);
        }
    }
}
