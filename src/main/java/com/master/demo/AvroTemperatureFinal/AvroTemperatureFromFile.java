package com.master.demo.AvroTemperatureFinal;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AvroTemperatureFromFile {

    public static void main(String[] args) throws IOException {
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(new FileInputStream(new File("src/main/resources/avro/AvroTemperature.avsc")));
        //从file读取数据
        File file = new File("src/main/resources/avro/output/part-00000.avro");
        DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>(schema);
        DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(file, reader);
        while (dataFileReader.hasNext()){
            GenericRecord result = dataFileReader.next();
            System.out.printf("year is: %s\n", result.get("year"));
            System.out.printf("temperature is: %s\n", result.get("temperature"));
            System.out.printf("stationId is: %s\n", result.get("stationId"));
        }
    }
}
