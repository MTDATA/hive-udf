/*
 * Copyright (c) 2010-2011 meituan.com
 * All rights reserved.
 * 
 */
package com.sankuai.meituan.hive.util;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 集合工具类
 * 
 * @author lichengwu
 * @created 2011-11-30
 * 
 * @version 1.0
 */
final public class CollectionUtil {

    /**
     * 将集合变成字符串
     * 
     * @author lichengwu
     * @created 2011-11-30
     * 
     * @param collection
     * @param separator
     *            分隔符 默认","
     * @return
     */
    public static String toString(Collection<?> collection, String separator) {
        if (StringUtil.isBlank(separator)) {
            separator = ",";
        }
        StringBuilder str = new StringBuilder();
        if (collection == null) {
            str.append("null");
        } else if (collection.isEmpty()) {
            str.append("");
        } else {
            for (Object obj : collection) {
                str.append(obj.toString()).append(separator);
            }
            str.delete(str.length() - separator.length(), str.length());
        }
        return str.toString();
    }

    /**
     * 把集合中某个字段变成字符串
     * 
     * @author lichengwu
     * @created 2012-5-21
     * 
     * @param collection
     * @param filedName
     *            集合泛型对应对象字段
     * @param separator
     *            分隔符
     * @return 字符串
     */
    public static <T> String generateString(Collection<T> collection, String filedName,
            String separator) {
        assert filedName != null;
        if (StringUtil.isBlank(separator)) {
            separator = ",";
        }
        if (collection == null) {
            return "null";
        } else if (collection.isEmpty()) {
            return "";
        }
        try {
            StringBuilder str = new StringBuilder();
            for (T obj : collection) {
                Field field = obj.getClass().getDeclaredField(filedName);
                field.setAccessible(true);
                Object object = field.get(obj);
                str.append(object.toString()).append(separator);
            }
            str.delete(str.length() - separator.length(), str.length());
            return str.toString();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> String generateString4Sql(Collection<T> collection, String filedName,
            String separator) {
        assert filedName != null;
        if (StringUtil.isBlank(separator)) {
            separator = ",";
        }
        if (collection == null) {
            return "null";
        } else if (collection.isEmpty()) {
            return "";
        }
        try {
            StringBuilder str = new StringBuilder();
            for (T obj : collection) {
                Field field = obj.getClass().getDeclaredField(filedName);
                field.setAccessible(true);
                Object object = field.get(obj);
                str.append("'");
                str.append(object.toString()).append("'").append(separator);
            }
            str.delete(str.length() - separator.length(), str.length());
            return str.toString();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成JavaBean集合的属性集合
     * 
     * @author lichengwu
     * @created 2012-6-14
     * 
     * @param collection
     *            JavaBean集合
     * @param property
     *            JavaBean属性
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> generatePropertyList(Collection<?> collection, String property) {
        assert property != null;
        if (collection == null || collection.isEmpty()) {
            return new ArrayList<T>(0);
        }
        List<T> list = new ArrayList<T>(collection.size());
        try {
            for (Object obj : collection) {
                Field field = obj.getClass().getDeclaredField(property);
                field.setAccessible(true);
                Object object = field.get(obj);
                list.add((T) object);
            }
            return list;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将集合编程字符串，字符串用符号引起来
     * 
     * @author chenchun
     * @created 2012-3-7
     * 
     * @param collection
     * @param separator
     * @param quotation
     * @return
     */
    public static String toString(Collection<?> collection, String separator, String quotation) {
        if (StringUtil.isBlank(separator)) {
            separator = ",";
        }
        StringBuilder str = new StringBuilder();
        if (collection == null) {
            str.append("null");
        } else if (collection.isEmpty()) {
            str.append("");
        } else {
            for (Object obj : collection) {
                str.append(quotation).append(obj.toString()).append(quotation).append(separator);
            }
            str.delete(str.length() - separator.length(), str.length());
        }
        return str.toString();
    }

    /**
     * 将集合编程字符串
     * 
     * @author lichengwu
     * @created 2011-11-30
     * 
     * @param array
     * @param separator
     * @return
     */
    public static String toString(Object[] array, String separator) {
        if (StringUtil.isBlank(separator)) {
            separator = ",";
        }
        StringBuilder str = new StringBuilder();
        if (array == null) {
            str.append("null");
        } else if (array.length == 0) {
            str.append("");
        } else {
            for (Object obj : array) {
                str.append(obj.toString()).append(separator);
            }
            str.delete(str.length() - separator.length(), str.length());
        }
        return str.toString();
    }

    /**
     * 返回默认填充defaultValue的集合 如果集合支持，则清空集合原始数据
     * 
     * @author lichengwu
     * @created 2011-12-1
     * 
     * @param <T>
     * @param col
     * @param size
     *            集合大小
     * @param defaultValue
     *            默认值
     * @return
     */
    public static <T> Collection<T> defaultCollection(Collection<T> col, int size, T defaultValue) {
        try {
            col.clear();
        } catch (Exception e) {
        }
        for (int i = 0; i < size; i++) {
            col.add(defaultValue);
        }
        return col;
    }

    /**
     * 获得map的子集
     * 
     * @author lichengwu
     * @created 2011-12-22
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @param keys
     * @return
     */
    public static <K, V> Map<K, V> subMap(Map<K, V> map, Collection<K> keys) {
        Map<K, V> subMap = new HashMap<K, V>(keys.size());
        for (K key : keys) {
            V value = map.get(key);
            if (value != null) {
                subMap.put(key, value);
            }
        }
        return subMap;
    }


    /**
     * obj装成list
     * 
     * @author chenchun
     * @created 2012-8-24
     * 
     * @param obj
     * @return
     */
    public static <T> List<T> toList(T obj) {
        List<T> list = new ArrayList<T>();
        list.add(obj);
        return list;
    }

    public static <T> List<T> asList(T... a) {
        if (a == null || a.length == 0) {
            return new ArrayList<T>();
        }
        return Arrays.asList(a);
    }

    public static void main(String[] args) {
        List<Long> list = generatePropertyList(Arrays.asList(new Date(), new Date()), "fastTime");
        System.out.println(list);
    }
}
