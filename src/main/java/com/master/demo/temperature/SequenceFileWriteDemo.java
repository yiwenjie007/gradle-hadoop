package com.master.demo.temperature;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

import java.net.URI;

public class SequenceFileWriteDemo {

    public static final String[] DATA = {
         "One, two0, buckle my shoe" ,
         "One, two1, buckle my shoe" ,
         "One, two2, buckle my shoe" ,
         "One, two3, buckle my shoe" ,
         "One, two4, buckle my shoe"
    };

    public static void main(String[] agrs) throws Exception {
        String url = "hdfs://10.1.1.195:9000/user/root/test/seq-test.seq";
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(url), conf);
        Path path = new Path(url);

        IntWritable key = new IntWritable();
        Text value = new Text();
        SequenceFile.Writer writer = null;
        try {
            writer = SequenceFile.createWriter(fs, conf, path, key.getClass(), value.getClass());
            for (int i = 0; i < 100; i++) {
                key.set(100-i);
                value.set(DATA[i % DATA.length]);
                System.out.printf("[%s]\t%s\t%s\n", writer.getLength(), key, value);
                writer.append(key, value);
            }
        }finally {
            IOUtils.closeStream(writer);
        }
    }
}
