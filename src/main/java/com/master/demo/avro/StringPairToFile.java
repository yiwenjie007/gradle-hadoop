package com.master.demo.avro;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class StringPairToFile {

    public static void main(String[] args) throws IOException {
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(new FileInputStream(new File("src/main/resources/avro/StringPair.avsc")));
        //写数据到文件
        GenericRecord datum = new GenericData.Record(schema);
        datum.put("left", "L");
        datum.put("right", "R");
        File file = new File("src/main/resources/avro/data.avro");
        DatumWriter<GenericRecord> writer = new GenericDatumWriter<GenericRecord>(schema);
        DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(writer);
        dataFileWriter.create(schema, file);
        dataFileWriter.append(datum);
        dataFileWriter.close();
    }
}
