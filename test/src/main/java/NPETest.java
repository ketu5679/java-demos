import java.util.HashSet;
import java.util.Set;

/**
 * @Author: zjh
 * @Date: 2021/5/13 20:30
 */
public class NPETest {

    public static void main(String[] args) {
        Long a = null;
//        System.out.println(String.valueOf(a.getClass()));
//        System.out.println(a == 123L);
        Boolean as = null;
//        System.out.println(as);
//        System.out.println(Boolean.TRUE.equals(as));
        Set<Long> test = new HashSet<Long>();
        System.out.println(test.add(1L));
        System.out.println(test.add(1L));
        System.out.println(test.add(1L));
    }

    public static void tt(Long a) {
        System.out.println(a == 123L);
    }
}
