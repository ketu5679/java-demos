import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: zjh
 * @Date: 2021/8/21 15:14
 */
public class MapRemoveOnIterator {
    public static void main(String[] args) {
        Map<Integer, AtomicLong> m = new ConcurrentHashMap<>();
        for(int i = 0; i< 100; i++) {
            AtomicLong atomicLong = new AtomicLong();
            atomicLong.set(i);
            m.put(i, atomicLong);
        }
        for (Map.Entry<Integer, AtomicLong> entry : m.entrySet()) {
            Integer k = entry.getKey();
            AtomicLong v = entry.getValue();
            System.out.println(k + " " + v);
            System.out.println("rm size={}" + m.size());
            System.out.println("rm " + m.remove(k));
            System.out.println("after rm " + m.get(k));
            System.out.println("rm get={}" + m.size());
        }

    }
}
