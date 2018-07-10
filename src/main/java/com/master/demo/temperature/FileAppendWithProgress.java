package com.master.demo.temperature;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

public class FileAppendWithProgress {

    public static void main(String[] args) throws Exception {
        String localFile = "src/main/resources/copy-test-add.txt";
        String dst = "hdfs://192.168.0.110:9000/user/root/test/copy-test.txt";
        InputStream in = new BufferedInputStream(new FileInputStream(localFile));
        Configuration conf = new Configuration();
        //当集群个数小于3个时，应用此配置
        conf.set("dfs.support.append", "true");
        conf.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER");
        conf.set("dfs.client.block.write.replace-datanode-on-failure.enable", "true");
        FileSystem fs = FileSystem.get(URI.create(dst), conf);
        OutputStream out = fs.append(new Path(dst));
        IOUtils.copyBytes(in, out, 4096, true);
    }
}
