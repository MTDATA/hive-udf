/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sankuai.meituan.hive.udf;

import java.util.ArrayList;
import java.util.Collections;

import com.sankuai.meituan.hive.util.CollectionUtil;
import org.apache.hadoop.hive.ql.exec.UDAF;
import org.apache.hadoop.hive.ql.exec.UDAFEvaluator;
import org.apache.hadoop.hive.ql.metadata.HiveException;

/**
 * This is a simple UDAF that concatenates all arguments from different rows
 * into a single string.
 * <p/>
 * It should be very easy to follow and can be used as an example for writing
 * new UDAFs.
 * <p/>
 * Note that Hive internally uses a different mechanism (called GenericUDAF) to
 * implement built-in aggregation functions, which are harder to program but
 * more efficient.
 */
public class UDAFExampleGroupConcat extends UDAF {

    /**
     * The actual class for doing the aggregation. Hive will automatically look
     * for all internal classes of the UDAF that implements UDAFEvaluator.
     */
    public static class UDAFExampleGroupConcatEvaluator implements UDAFEvaluator {

        ArrayList<String> data;

        public UDAFExampleGroupConcatEvaluator() {
            super();
            data = new ArrayList<String>();
        }

        /**
         * Reset the state of the aggregation.
         */
        public void init() {
            data.clear();
        }

        /**
         * Iterate through one row of original data.
         * <p/>
         * This UDF accepts arbitrary number of String arguments, so we use
         * String[]. If it only accepts a single String, then we should use a single
         * String argument.
         * <p/>
         * This function should always return true.
         */
        public boolean iterate(String value, String separator, long key) {
            if (data.size() == 0) {
                data.add(separator);
            }
            data.add(String.valueOf(key));
            data.add(value);
            return true;
        }

        /**
         * Terminate a partial aggregation and return the state.
         */
        public ArrayList<String> terminatePartial() {
            return data;
        }

        /**
         * Merge with a partial aggregation.
         * <p/>
         * This function should always have a single argument which has the same
         * type as the return value of terminatePartial().
         * <p/>
         * This function should always return true.
         */
        public boolean merge(ArrayList<String> o) {
            if (o != null && o.size() > 0) {
                if (data.size() == 0) {
                    data.add(o.get(0));
                }
                for (int i = 1; i < o.size(); i++) {
                    data.add(o.get(i));
                }
            }
            return true;
        }

        /**
         * Terminates the aggregation and return the final result.
         */
        public String terminate() throws HiveException {
            if (data.size() == 0) {
                return null;
            }
            String separator = data.get(0);
            ArrayList<ExKeyValue> exKeyValues = new ArrayList<ExKeyValue>();
            for (int i = 1; i < data.size() - 1; i+=2) {
                try {
                    exKeyValues.add(new ExKeyValue(Long.parseLong(data.get(i)), data.get(i + 1)));
                } catch (Exception e) {
                    throw new HiveException(CollectionUtil.toString(data,
                            "==") + "==   i=" + i + " seq=" + separator, e);
                }
            }

            Collections.sort(exKeyValues);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < exKeyValues.size(); i++) {
                sb.append(exKeyValues.get(i).getValue());
                if (i != exKeyValues.size() - 1) {
                    sb.append(separator);
                }
            }
            return sb.toString();
        }
    }

    public static class ExKeyValue implements Comparable<ExKeyValue> {
        private Long key;
        private String value;

        public ExKeyValue() {
        }

        public ExKeyValue(Long key, String value) {
            this.key = key;
            this.value = value;
        }

        public Long getKey() {
            return key;
        }

        public void setKey(Long key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public int compareTo(ExKeyValue o) {
            if (o == null) {
                return 1;
            }
            if (this.key == null) {
                if (o.getKey() == null) {
                    return 0;
                } else {
                    return -1;
                }
            } else {
                return this.key.compareTo(o.getKey());
            }
        }
    }

}
