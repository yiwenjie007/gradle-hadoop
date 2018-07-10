package com.master.demo.temperature;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

public class FileDecompressor {

    public static void main(String[] args) throws Exception {
        String url = "hdfs://10.1.1.195:9000/user/root/test/1902.gz";
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(url), conf);
        Path inputPath = new Path(url);
        CompressionCodecFactory factory = new CompressionCodecFactory(conf);
        CompressionCodec codec = factory.getCodec(inputPath);
        if(codec == null){
            System.err.println("No codec find for " + url);
            System.exit(1);
        }
        //去掉压缩后缀，生成文件名
        String outputUrl = CompressionCodecFactory.removeSuffix(url, codec.getDefaultExtension());
        InputStream in = null;
        OutputStream out = null;
        try {
            in = codec.createInputStream(fs.open(inputPath));
            out = fs.create(new Path(outputUrl));
            IOUtils.copyBytes(in, out, conf);
        }finally {
            IOUtils.closeStream(in);
            IOUtils.closeStream(out);
        }
    }
}
