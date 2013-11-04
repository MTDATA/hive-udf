package com.sankuai.meituan.hive.udf;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author jianwang
 * @version 1.0
 * @created 2013-11-01
 */
public class TestUDFContainsArray {

    @Test
    public void testNullParams() {
        UDFContainsArray udf = new UDFContainsArray();
        Assert.assertTrue(udf.evaluate("abc", null) == 0);
        Assert.assertTrue(udf.evaluate(null, "abc") == 0);
        Assert.assertTrue(udf.evaluate(null, null) == 0);
    }

    @Test
    public void testEvaluate() {
        UDFContainsArray udf = new UDFContainsArray();
        Assert.assertTrue(udf.evaluate("abc", "abc") == 1);
        Assert.assertTrue(udf.evaluate("abc", "abcde") == 0);
        Assert.assertTrue(udf.evaluate("abcab", "abc") == 1);
        Assert.assertTrue(udf.evaluate("abcabc", "abc") == 2);
        Assert.assertTrue(udf.evaluate("bcabc", "abc") == 1);
        Assert.assertTrue(udf.evaluate("abcaaabc", "abc") == 2);
    }

}
