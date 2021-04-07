import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.text.StringEscapeUtils;
import org.owasp.esapi.ESAPI;

import java.util.concurrent.TimeUnit;

/**
 * @Author: zjh
 * @Date: 2020/11/5 16:42
 */

public class RunTimeTest {
    private static final long RUN_TIMES = 100L;

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String t = "hello i'm <script>ff<pre>ff</pre>";
        long l = System.currentTimeMillis();
//        for (int i = 0; i < RUN_TIMES; i++) {
            System.out.println(StringEscapeUtils.escapeHtml4("I'm zh"));
//            System.out.println(StringEscapeUtils.escapeHtml4("I'm zh"));
//            System.out.println(ESAPI.encoder().encodeForHTML());
//        }
//        String ts = ESAPI.encoder().canonicalize(t);
//        System.out.println(ts);
        System.out.println(stopWatch.getTime(TimeUnit.MILLISECONDS));
    }
}
