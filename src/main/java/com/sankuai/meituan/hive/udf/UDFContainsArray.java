package com.sankuai.meituan.hive.udf;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

/**
 * udf contains_arr
 * 
 * @author wangjian
 * @version 1.0
 * @created 2013-10-17
 * 
 */
@Description(name = "contains_arr", value = "_FUNC_(a1,a2) - Return how many array a2s are contained in array a1")
public class UDFContainsArray extends UDF {

    public UDFContainsArray() {

    }

    public int evaluate(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return 0;
        }
        char[] a1 = str1.toCharArray();
        char[] a2 = str2.toCharArray();
        if (a1.length == 0 || a1.length < a2.length) {
            return 0;
        }
        int sum = 0;
        for (int i = 0; i < a1.length - a2.length + 1; i++) {
            if (a1[i] == a2[0]) {
                boolean flag = true;
                for (int j = 1; j < a2.length; j++) {
                    if (a1[i + j] != a2[j]) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    sum++;
                }
            }
        }
        return sum;
    }

}
