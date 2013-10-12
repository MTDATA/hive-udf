/*
 * Copyright (c) 2010-2012 meituan.com
 * All rights reserved.
 * 
 */
package com.sankuai.meituan.hive.udf;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde.serdeConstants;
import org.apache.hadoop.hive.serde.serdeConstants;
import org.apache.hadoop.hive.serde2.io.DoubleWritable;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorUtils;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.LongWritable;

/**
 * AccSum 累积和函数
 *
 * @author chenchun
 * @version 1.0
 * @created 2013-03-18
 */
public class AccSum extends GenericUDF {

    private final LongWritable longResult = new LongWritable();
    private final DoubleWritable doubleResult = new DoubleWritable();
    private ObjectInspector resultOI, valueOI, hashOI, prevHashStandardOI;
    private Object prevHash;

    @Override
    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        if (arguments.length != 2) {
            throw new UDFArgumentException("Exactly two argument is expected.");
        }
        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i].getCategory() != ObjectInspector.Category.PRIMITIVE) {
                throw new UDFArgumentTypeException(i, "Only primitive type arguments are accepted but " + arguments[i].getTypeName() + " is passed.");
            }
        }
        String t = arguments[1].getTypeName();
        if (t.equals(serdeConstants.TINYINT_TYPE_NAME) ||
                t.equals(serdeConstants.SMALLINT_TYPE_NAME) ||
                t.equals(serdeConstants.INT_TYPE_NAME) ||
                t.equals(serdeConstants.BIGINT_TYPE_NAME)) {
            resultOI = PrimitiveObjectInspectorFactory.writableLongObjectInspector;
        } else if (t.equals(serdeConstants.FLOAT_TYPE_NAME) ||
                t.equals(serdeConstants.DOUBLE_TYPE_NAME) ||
                t.equals(serdeConstants.STRING_TYPE_NAME)) {
            resultOI = PrimitiveObjectInspectorFactory.writableDoubleObjectInspector;
        } else {
            throw new UDFArgumentTypeException(1, "Only numeric or string type arguments are accepted but " + arguments[1].getTypeName() + " is passed.");
        }
        longResult.set(0);
        doubleResult.set(0);
        valueOI = arguments[1];
        hashOI = arguments[0];
        prevHashStandardOI = ObjectInspectorUtils.getStandardObjectInspector(hashOI, ObjectInspectorUtils.ObjectInspectorCopyOption.JAVA);
        return resultOI;
    }

    @Override
    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        Object hash = arguments[0].get(), value = arguments[1].get();
        ObjectInspectorConverters.Converter converter = ObjectInspectorConverters.getConverter(valueOI, resultOI);
        if (ObjectInspectorUtils.compare(prevHash, prevHashStandardOI, hash, hashOI) == 0) {
            if (serdeConstants.DOUBLE_TYPE_NAME.equals(resultOI.getTypeName())) {
                DoubleWritable valueW = (DoubleWritable) converter.convert(value);
                doubleResult.set(doubleResult.get() + valueW.get());
                return doubleResult;
            } else {
                LongWritable valueW = (LongWritable) converter.convert(value);
                longResult.set(longResult.get() + valueW.get());
                return longResult;
            }
        } else {
            prevHash = ObjectInspectorUtils.copyToStandardObject(hash, hashOI, ObjectInspectorUtils.ObjectInspectorCopyOption.JAVA);
            if (serdeConstants.DOUBLE_TYPE_NAME.equals(resultOI.getTypeName())) {
                doubleResult.set(((DoubleWritable) converter.convert(value)).get());
                return doubleResult;
            } else {
                longResult.set(((LongWritable) converter.convert(value)).get());
                return longResult;
            }
        }
    }

    @Override
    public String getDisplayString(String[] children) {
        return "accsum(" + StringUtils.join(children, ',') + ")";
    }
}
