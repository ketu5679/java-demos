import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.*;

public class Test {
    public static void main(String[] args) {
//        new WebDownloader().download("https://avatars.githubusercontent.com/u/42973839?s=400&u=38a2d352a39b1ebf7cd58137e1f0802d767d7e06&v=4", "a.jpg");
        Cal cal = new Cal();
        ExecutorService es = Executors.newFixedThreadPool(10);
        Future<String> submit = es.submit(cal);
        String s = null;
        try {
            s = submit.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(s);
        System.out.println(12333);
//        es.shutdown();
        es.shutdownNow();
    }


}
class Cal implements Callable<String> {

    public String call() {
        return "call";
    }
}

class WebDownloader {
    public void download(String url, String name) {
        try {
            FileUtils.copyURLToFile(new URL(url), new File(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
