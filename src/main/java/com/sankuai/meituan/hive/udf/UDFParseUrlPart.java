package com.sankuai.meituan.hive.udf;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.udf.UDFParseUrl;

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
@Description(name = "parse_url",
        value = "_FUNC_(url, partToExtract[, key]) - extracts a part from a URL",
        extended = "Parts: HOST, PATH, QUERY, REF, PROTOCOL, AUTHORITY, FILE, "
                + "USERINFO\nkey specifies which query to extract\n"
                + "Example:\n"
                + "  > SELECT _FUNC_('http://facebook.com/path/p1.php?query=1', "
                + "'HOST') FROM src LIMIT 1;\n"
                + "  'facebook.com'\n"
                + "  > SELECT _FUNC_('http://facebook.com/path/p1.php?query=1', "
                + "'PATH'), _FUNC_('http://facebook.com/path/p1.php?query=1', "
                + "'PATH', 2) FROM src LIMIT 1;\n"
                + "/path/p1.php  'p1.php'\n"
                + "  > SELECT _FUNC_('http://facebook.com/path/p1.php?query=1', "
                + "'QUERY') FROM src LIMIT 1;\n"
                + "  'query=1'\n"
                + "  > SELECT _FUNC_('http://facebook.com/path/p1.php?query=1', "
                + "'QUERY', 'query') FROM src LIMIT 1;\n" + "  '1'")
public class UDFParseUrlPart extends UDFParseUrl {
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
