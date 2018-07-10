package com.master.demo.temperature;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

import java.io.*;
import java.net.URI;

public class FileCopyWithProgress {

    public static void main(String[] args) throws Exception {
        String localFile = "src/main/resources/copy-test.txt";
        String dst = "hdfs://192.168.0.110:9000/user/root/test/copy-test.txt";
        InputStream in = new BufferedInputStream(new FileInputStream(localFile));
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(dst), conf);
        OutputStream out = fs.create(new Path(dst), new Progressable() {
            public void progress() {
                System.out.print(".");
            }
        });
        IOUtils.copyBytes(in, out, 4096, true);
    }
}
