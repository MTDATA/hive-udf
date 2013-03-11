package com.sankuai.meituan.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

public class SubStringIndex extends UDF {
    final Text r = new Text();

    public Text evaluate(final Text _input, final Text _delim, final IntWritable _pos) {
        if ((_input == null) || (_delim == null) || (_pos == null)) {
            return null;
        }

        final int pos = _pos.get();
        final String input = _input.toString();
        final String delim = _delim.toString();

        r.clear();

        if (pos == 0 || delim.equals("")) {
            return r;
        }

        int count = 0;

        if (pos > 0) {
            int k = -1;

            while (count++ < pos) {
                k = input.indexOf(delim, k + 1);
                if (k < 0) {
                    r.set(input);
                    return r;
                }
            }

            r.set(input.substring(0, k));
            return r;
        }

        int k = input.length() + 1;

        while (count-- > pos) {
            k = input.lastIndexOf(delim, k - 1);
            if (k < 0) {
                r.set(input);
                return r;
            }
        }

        r.set(input.substring(k + delim.length()));
        return r;
    }
}
