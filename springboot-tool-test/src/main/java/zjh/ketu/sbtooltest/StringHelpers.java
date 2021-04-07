package zjh.ketu.sbtooltest;

import org.apache.commons.lang.StringUtils;
import org.owasp.esapi.ESAPI;

import java.util.regex.Pattern;

/**
 * 字符串处理通用方法
 *
 * @author zhoujunhao
 */
public class StringHelpers {
    /**
     * XSS标记过滤
     */
    private static final Pattern[] XSS_FILTER_PATTERNS = new Pattern[]{
            // Avoid anything between script tags
            Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
            // Avoid anything in a src='...' type of expression
            Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // Remove any lonesome </script> tag
            Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
            // Remove any lonesome <script ...> tag
            Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // Avoid eval(...) expressions
            Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // Avoid expression(...) expressions
            Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // Avoid javascript:... expressions
            Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
            // Avoid vbscript:... expressions
            Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
            // Avoid οnlοad= expressions
            Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
    };

    /**
     * 非 [0-9a-zA-z_]过滤
     */
    private static final Pattern[] SIMPLE_STR_FILTER_PATTERNS = new Pattern[]{
            // Avoid anything between script tags
            Pattern.compile("\\W", Pattern.CASE_INSENSITIVE),
    };
    /**
     * 首字母大写
     *
     * @param word
     * @return
     */
    public static String ucFirst(String word) {
        if (StringUtils.isBlank(word)) {
            return word;
        }

        return word.substring(0, 1).toUpperCase().concat(word.substring(1));
    }

    /**
     * 首字母小写
     *
     * @param word
     * @return
     */
    public static String ucLower(String word) {
        if (StringUtils.isBlank(word)) {
            return word;
        }

        return word.substring(0, 1).toLowerCase().concat(word.substring(1));
    }

    /**
     * 去除首尾的指定字符串
     *
     * @param name
     * @param trim
     * @return
     */
    public static String trimFirstAndEnd(String name, String trim) {
        int length = trim.length();
        if (name.startsWith(trim)) {
            name = name.substring(length);
        }
        if (name.endsWith(trim)) {
            name = name.substring(0, 1 + name.length() - length);
        }
        return name;
    }

    /**
     * 规范化字符串
     * @param value
     * @return
     */
    public static String canonicalize(String value) {
        return filter(value, null);
    }

    /**
     * 过滤xss标记
     * @param value
     * @return
     */
    public static String stripXss(String value) {
        return filter(value, XSS_FILTER_PATTERNS);
    }

    public static String stripSimpleStr(String value) {
        return filter(value, SIMPLE_STR_FILTER_PATTERNS);
    }

    private static String filter(String value, Pattern[] patterns) {
        if (value == null) {
            return null;
        }
        // avoid encoded attacks.
        //注意：若前端使用get方式提交经过encodeURI的中文，此处会乱码
        value = ESAPI.encoder().canonicalize(value);
        if (patterns != null) {
            for (Pattern pattern : patterns) {
                value = pattern.matcher(value).replaceAll("");
            }
        }
        return value;
    }
}
