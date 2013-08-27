/*
 * Copyright (c) 2010-2012 meituan.com
 * All rights reserved.
 * 
 */
package com.sankuai.meituan.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDFUtils;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;

/**
 * abstract class for generic variable param udfs
 *
 * @author chenchun
 * @version 1.0
 * @created 2013-08-27
 */
public abstract class GenericVariableParamUDF extends GenericUDF {

    protected ObjectInspector[] argumentOIs;
    protected GenericUDFUtils.ReturnObjectInspectorResolver returnOIResolver;

    @Override
    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        argumentOIs = arguments;

        returnOIResolver = new GenericUDFUtils.ReturnObjectInspectorResolver(true);
        for (int i = 0; i < arguments.length; i++) {
            if (!returnOIResolver.update(arguments[i])) {
                throw new UDFArgumentTypeException(i,
                        "The expressions should all have the same type: \""
                                + returnOIResolver.get().getTypeName()
                                + "\" is expected but \"" + arguments[i].getTypeName()
                                + "\" is found");
            }
        }
        return returnOIResolver.get();
    }

    @Override
    public String getDisplayString(String[] children) {
        StringBuilder sb = new StringBuilder();
        sb.append(getUdfName()).append("(");
        if (children.length > 0) {
            sb.append(children[0]);
            for (int i = 1; i < children.length; i++) {
                sb.append(",");
                sb.append(children[i]);
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public abstract String getUdfName();

}
