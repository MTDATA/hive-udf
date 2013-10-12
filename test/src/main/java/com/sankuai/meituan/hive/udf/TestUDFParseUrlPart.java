package com.sankuai.meituan.hive.udf;

import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author chenjianzhong02
 * @version 1.0
 * @created 2013-08-19
 */
public class TestUDFParseUrlPart {

    @Test
    public void test1() {
        UDFParseUrlPart udfParseUrlPart = new UDFParseUrlPart();
        Assert.assertEquals("first", udfParseUrlPart.evaluate("http://www.meituan.com/first", "PATH", 1));
    }

//    @Test(expected = Exception.class)
//    public void test2() {
//        UDFParseUrlPart udfParseUrlPart = new UDFParseUrlPart();
//        udfParseUrlPart.evaluate("www.meituan.com/first", "PATH", 1);
//    }

    @Test
    public void test3() {
        UDFParseUrlPart udfParseUrlPart = new UDFParseUrlPart();
        Assert.assertEquals("second", udfParseUrlPart.evaluate("http://www.meituan.com/first/second", "PATH", 2));
    }

    @Test
    public void test4() {
        UDFParseUrlPart udfParseUrlPart = new UDFParseUrlPart();
        Assert.assertEquals(null, udfParseUrlPart.evaluate("http://www.meituan.com/first/second////", "PATH", 5));
    }

    @Test
    public void test5() {
        UDFParseUrlPart udfParseUrlPart = new UDFParseUrlPart();
        Assert.assertEquals("third", udfParseUrlPart.evaluate("http://www.meituan.com/first/second/third?abc=123", "PATH", 3));
    }
}
