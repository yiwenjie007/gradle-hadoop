package com.master.demo.Parquet.AvroParquet;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AvroParquetToFile {

    public static void main(String[] args) throws IOException {
        Schema schema = new Schema.Parser().parse(new FileInputStream(new File("src/main/resources/parquet/AvroParquet.avsc")));
        GenericRecord datum = new GenericData.Record(schema);
        datum.put("left", "L");
        datum.put("right", "R");

        Path path = new Path("src/main/resources/parquet/avro_parquet.parquet");
        AvroParquetWriter<GenericRecord> writer = new AvroParquetWriter<GenericRecord>(path, schema);
        writer.write(datum);
        writer.close();
    }
}
