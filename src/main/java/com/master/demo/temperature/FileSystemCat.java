package com.master.demo.temperature;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class FileSystemCat {

    public static void main(String[] args) throws IOException {
        //String url = args[0];
        String url = "hdfs://192.168.0.110:9000/user/root/test/mapred-site.xml";
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(URI.create(url), configuration);
        InputStream inputStream = null;
        try {
            inputStream = fileSystem.open(new Path(url));
            IOUtils.copyBytes(inputStream, System.out, 4096, false);
        } finally {
            IOUtils.closeStream(inputStream);
        }
    }
}
