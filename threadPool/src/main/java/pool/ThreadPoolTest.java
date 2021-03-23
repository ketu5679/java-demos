package pool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ThreadPoolTest {
    static List<Integer>  nums = new ArrayList<>();
    static {
        Random r = new Random();
        for (int i = 0; i < 100000; i++) {
            nums.add(r.nextInt(100));
        }
    }
    public static void main(String[] args) throws InterruptedException {
//        ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(100);
//        ThreadPoolExecutor e = new ThreadPoolExecutor(2, 10,
//                60, TimeUnit.SECONDS, workQueue);
//        workQueue.add(new Runnable() {
//            public void run() {
//                System.out.println(123);
//            }
//        });
//        e.prestartAllCoreThreads();
//        TimeUnit.SECONDS.sleep(1);




        nums.parallelStream().forEach(ThreadPoolTest::isPrime);

    }

    public static void foreach() {
        nums.forEach(ThreadPoolTest::isPrime);
    }

    public static boolean isPrime(int num) {
        for (int i = 2; i <= num/2; i++) {
            if (num%i == 0) {
                return false;
            }
        }
//        System.out.println(Thread.currentThread().getName() + " " + num);
        return true;
    }
}