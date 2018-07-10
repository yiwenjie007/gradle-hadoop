package com.master.demo.Parquet.AvroParquet;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.avro.AvroReadSupport;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AvroParquetFromFile {

    public static void main(String[] args) throws IOException {
        Schema schema = new Schema.Parser().parse(new FileInputStream(new File("src/main/resources/parquet/AvroParquet.avsc")));
        Configuration conf = new Configuration();
        AvroReadSupport.setRequestedProjection(conf, schema);
        Path path = new Path("src/main/resources/parquet/avro_parquet.parquet");
        AvroParquetReader<GenericRecord> reader = new AvroParquetReader<GenericRecord>(conf, path);
        GenericRecord result = reader.read();
        System.out.printf("left is: %s\n", result.get("left").toString());
        System.out.printf("right is: %s\n", result.get("right").toString());
    }
}
