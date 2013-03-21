/*
 * Copyright (c) 2010-2011 meituan.com
 * All rights reserved.
 * 
 */
package com.sankuai.meituan.hive.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 处理json的工具类
 * 
 * @author zhaolei
 * @created 2011-5-9
 * 
 * @version 1.0
 */
public final class JsonUtil {

    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    private JsonUtil() {
    }

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 对象转换成json字符串
     * 
     * @author zhaolei
     * @created 2011-5-9
     * 
     * @param o
     *            对象
     * @return
     */
    public static String toJsonString(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonGenerationException e) {
            log.error(e.getMessage(), e);
        } catch (JsonMappingException e) {
            log.error(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将json串转换成对象
     * 
     * @author lichengwu
     * @created 2011-8-17
     * 
     * @param <T>
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Throwable e) {
            log.error("", e);
            return null;
        }
    }

    /**
     * 将json串转换成map
     * 
     * @author lichengwu
     * @created 2011-8-17
     * 
     * @param json
     * @return
     */
    public static Map<?, ?> toMap(String json) {
        try {
            return objectMapper.readValue(json, Map.class);
        } catch (Throwable e) {
            log.error("", e);
            return null;
        }
    }

    public static List<?> toList(String json) {
        try {
            return objectMapper.readValue(json, List.class);
        } catch (Throwable e) {
            log.error("", e);
            return null;
        }
    }

    /**
     * 将json串转换成数组
     * 
     * @author lichengwu
     * @created 2011-8-17
     * 
     * @param <T>
     * @param json
     * @param array
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(String json, T[] array) {
        try {
            array = (T[]) objectMapper.readValue(json, Object[].class);
            return array;
        } catch (Throwable e) {
            log.error("", e);
            return null;
        }
    }

    /**
     * @author chenchun
     * @created 2013-03-21
     *
     * @param content
     * @param valueTypeRef
     * @param <T>
     * @return
     */
    public static <T, E> T toObject(String content, TypeReference<E> valueTypeRef) {
        try {
            return objectMapper.readValue(content, valueTypeRef);
        } catch (Throwable e) {
            log.error("", e);
            return null;
        }
    }
}
