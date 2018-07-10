package com.master.demo.temperature;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


public class MaxTemperatureLocalDriver {

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");
        conf.set("mapreduce.framework.name", "local");
        conf.setInt("mapreduce.task.io.sort.mb", 1);
        Path input = new Path("src/main/resources/input");
        Path output = new Path("src/main/resources/output");

        FileSystem fs = FileSystem.getLocal(conf);
        fs.delete(output, true);//delete old output

        MaxTemperatureDriver driver = new MaxTemperatureDriver();
        driver.setConf(conf);

        int exitCode = driver.run(new String[]{input.toString(), output.toString()});
        System.exit(exitCode);
    }
}
