/*
 * Copyright (c) 2010-2012 meituan.com
 * All rights reserved.
 * 
 */
package com.sankuai.meituan.hive.udf;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.metadata.HiveException;

/**
 * udf greatest
 *
 * @author chenchun
 * @version 1.0
 * @created 2013-08-27
 */
@Description(name = "greatest",
        value = "_FUNC_(a1, a2, ...) - Returns the greatest argument",
        extended = "Example:\n"
                + "  > SELECT _FUNC_(2, 1, 5) FROM src LIMIT 1;\n" + "  5")
public class GenericUDFGreatest extends GenericVariableParamUDF {

    @Override
    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        Comparable greatest = null;
        for (int i = 0; i < arguments.length; i++) {
            Object ai = arguments[i].get();
            if (ai == null) {
                continue;
            }
            Comparable o = (Comparable) returnOIResolver.convertIfNecessary(ai, argumentOIs[i]);
            greatest = greatest == null ? o : greatest.compareTo(o) < 0 ? o : greatest;
        }
        return greatest;
    }

    @Override
    public String getUdfName() {
        return "GREATEST";
    }
}
