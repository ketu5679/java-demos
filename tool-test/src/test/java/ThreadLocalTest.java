import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Author: zjh
 * @Date: 2020/11/5 19:24
 */

public class ThreadLocalTest {
    public final static ThreadLocal<Integer> LOCAL_SDF = ThreadLocal.withInitial(() -> {
        System.out.println(Thread.currentThread().getName());
        return 1000;
    });

    @Test
    public void getUUID(){
        System.out.println(UUID.randomUUID().toString());
    }



    @Test
    public void test() throws InterruptedException {
        LOCAL_SDF.get();
        System.out.println("remove");
        LOCAL_SDF.remove();
        LOCAL_SDF.get();
        System.out.println("remove");

        IntStream.range(0, 10).forEach(p->{
            new Thread(()->{
                LOCAL_SDF.get();
                LOCAL_SDF.set(LOCAL_SDF.get()-1);
                System.out.println("thread " + Thread.currentThread().getName());
            }).start();
        });
    }
}
