/*
 * Copyright (c) 2010-2012 meituan.com
 * All rights reserved.
 * 
 */
package com.sankuai.meituan.hive.udf;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.metadata.HiveException;

/**
 * udf least
 *
 * @author chenchun
 * @version 1.0
 * @created 2013-08-27
 */
@Description(name = "least",
        value = "_FUNC_(a1, a2, ...) - Returns the least argument",
        extended = "Example:\n"
                + "  > SELECT _FUNC_(2, 1, 5) FROM src LIMIT 1;\n" + "  1")
public class GenericUDFLeast extends GenericVariableParamUDF {

    @Override
    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        Comparable least = null;
        for (int i = 0; i < arguments.length; i++) {
            Object ai = arguments[i].get();
            if (ai == null) {
                continue;
            }
            Comparable o = (Comparable) returnOIResolver.convertIfNecessary(ai, argumentOIs[i]);
            least = least == null ? o : least.compareTo(o) > 0 ? o : least;
        }
        return least;
    }

    @Override
    public String getUdfName() {
        return "LEAST";
    }
}
