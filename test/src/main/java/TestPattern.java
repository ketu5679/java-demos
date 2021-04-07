import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @Author: zjh
 * @Date: 2021/2/1 16:44
 */
public class TestPattern {
    public static void main(String[] args) {
        Pattern p = compile("d(([ws]_(pty|ord|ssa|pub|stk|svc|prm|lgs))|m)_([a-z0-9_]*)_([hdmwpqyr][fi]?|td|his)");
        System.out.println(p.matcher("dm_test_d").find());;
        System.out.println(p.matcher("dm_test_dd").find());;
        System.out.println(p.matcher("dw_test_d").find());;
        System.out.println(p.matcher("dw_pty_test_d").find());;
        System.out.println(p.matcher("dw_pty_test_td").find());;
        System.out.println(p.matcher("dw_pty_test_fff_td").find());;
    }
}
