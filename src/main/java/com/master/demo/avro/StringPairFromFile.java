package com.master.demo.avro;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;

import java.io.File;
import java.io.IOException;

public class StringPairFromFile {

    public static void main(String[] args) throws IOException {
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(StringPairFromFile.class.getResourceAsStream("StringPair.avsc"));
        //从file读取数据
        File file = new File("src/main/resources/avro/data.avro");
        DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>(schema);
        DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(file, reader);
        while (dataFileReader.hasNext()){
            GenericRecord result = dataFileReader.next();
            System.out.printf("left is: %s\n", result.get("left"));
            System.out.printf("right is: %s\n", result.get("right"));
        }
    }
}
