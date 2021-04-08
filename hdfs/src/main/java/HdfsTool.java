import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

/**
 * @Author: zjh
 * @Date: 2021/4/8 21:40
 */
public class HdfsTool {

    private static Configuration cfg;
    private static FileSystem fs;


    static {
        cfg = new Configuration();
        cfg.set("fs.defaultFS", "hdfs://hdfs.ketu.zjh:9000");
        try {
            fs = FileSystem.get(URI.create("hdfs://hdfs.ketu.zjh:9000"), cfg, "root");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
//        String path = "/test2";
//        mkdir(path + "1");
//        list(path);
//        uploadFile("D:\\002tmp\\a.txt", path);
//        uploadFile("D:\\002tmp\\a.txt", path);
//        list(path);

        FSDataInputStream open = fs.open(new Path("/test2/ttt.txt"));

        System.out.println(open.getPos());
        IOUtils.copyBytes(open, System.out, 4096, false);
        System.out.println(open.getPos());
        open.seek(2);
        System.out.println(open.getPos());
        IOUtils.copyBytes(open, System.out, 4096, false);
        System.out.println(open.getPos());

    }

    public static void mkdir(String path) throws IOException {
        Path p = new Path(path);
        fs.mkdirs(p);
        fs.setOwner(p, "zhoujunhao", "supergroup");
    }

    public static void list(String path) throws IOException {
        System.out.println("list " + path);
        RemoteIterator<LocatedFileStatus> iterator =
                fs.listFiles(new Path(path), false);
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    public static void uploadFile(String src, String des) throws IOException {
        fs.copyFromLocalFile(false, new Path(src), new Path(des));
    }
}
