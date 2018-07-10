package com.master.demo.temperature;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;

import java.net.URI;

public class GlobalStatus {

    public static void main(String[] args) throws Exception {
        String url = "hdfs://192.168.0.110:9000";
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(url), conf);
        FileStatus[] status = fs.globStatus(new Path("/user/root/*/*"), new RegexExcludeFilter("^.*/user/root/input/.*$"));
        Path[] listedPaths = FileUtil.stat2Paths(status);
        for (Path p:listedPaths) {
            System.out.println(p);
        }
    }
}
