package com.master.demo.temperature;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.net.URI;

public class FileDelete {

    public static void main(String[] args) throws Exception {
        String url = "hdfs://192.168.0.110:9000";
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(url), conf);
        fs.delete(new Path("/user/root/test/copy-test.txt"));
    }
}
