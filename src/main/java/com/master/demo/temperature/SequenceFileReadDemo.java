package com.master.demo.temperature;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.util.ReflectionUtils;

import java.net.URI;

public class SequenceFileReadDemo {

    public static void main(String[] args) throws Exception {
        String url = "hdfs://10.1.1.195:9000/user/root/test/seq-test.seq";
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(url), conf);
        Path path = new Path(url);
        SequenceFile.Reader reader = null;
        try {
            reader = new SequenceFile.Reader(fs, path, conf);
            Writable key = (Writable)ReflectionUtils.newInstance(reader.getKeyClass(), conf);
            Writable value = (Writable)ReflectionUtils.newInstance(reader.getValueClass(), conf);
            while (reader.next(key, value)){
                long position = reader.getPosition();
                String syncSeen = reader.syncSeen()? "*":"";
                System.out.printf("[%s%s]\t%s\t%s\n", position, syncSeen, key, value);
            }
        }finally {
            IOUtils.closeStream(reader);
        }
    }
}
