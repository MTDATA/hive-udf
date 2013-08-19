package com.sankuai.meituan.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDF;

import java.net.URL;

/**
 * 提取url各层次信息
 * urlStr代表url
 * partToExact代表执行哪项操作
 * partIndex代表返回的部分
 *
 * @author chenjianzhong02
 * @version 1.0
 * @created 2013-08-19
 */
public class UDFParseUrlPart extends UDF {
    public String evaluate(String urlStr, String partToExtract, int partIndex) {
        URL url = null;
        if (urlStr == null || partToExtract == null) {
            return null;
        }
        try {
            url = new URL(urlStr);
        } catch (Exception e) {
            return null;
        }
        String path = url.getPath();
        if (path == null || path.equals("")) {
            return null;
        }
        if ("PATH".equals(partToExtract)) {
            while (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }
            String pathParts[] = path.split("/");
            if (partIndex <= 0 || partIndex >= pathParts.length) {
                return null;
            } else {
                return pathParts[partIndex];
            }
        }
        return null;

    }
}
