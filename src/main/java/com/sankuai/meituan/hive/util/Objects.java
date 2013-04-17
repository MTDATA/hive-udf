/*
 * Copyright (c) 2010-2011 meituan.com
 * All rights reserved.
 * 
 */
package com.sankuai.meituan.hive.util;

/**
 * Object Utils
 *
 * @author chenchun
 * @version 1.0
 * @created 2013-4-9
 */
final public class Objects {

    private Objects() {
        throw new IllegalAccessError();
    }


    /**
     * 比较对象是否相等
     *
     * @param obj1
     * @param obj2
     * @return 当obj1.equals(obj2)或obj1==obj2==null返回true，否则返回false
     */
    public static boolean equals(Object obj1, Object obj2) {
        return (obj1 == obj2) || (obj1 != null && obj1.equals(obj2));
    }


}
