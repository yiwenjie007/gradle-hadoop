package com.master.demo.temperature;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;

import java.net.URI;

public class ListStatus {

    public static void main(String[] args) throws Exception {
        String url = "hdfs://192.168.0.110:9000";
        Path[] paths = new Path[2];
        paths[0] = new Path("hdfs://192.168.0.110:9000/user/root/test");
        paths[1] = new Path("hdfs://192.168.0.110:9000/user/root/input");
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(url), conf);
        FileStatus[] status = fs.listStatus(paths);
        Path[] listedPaths = FileUtil.stat2Paths(status);
        for (Path p:listedPaths) {
            System.out.println(p);
        }
    }
}
