package com.master.demo.Parquet;

import org.apache.hadoop.fs.Path;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.example.GroupReadSupport;

import java.io.IOException;

public class ParquetFromFile {

    public static void main(String[] args) throws IOException {
        Path path = new Path("src/main/resources/parquet/data.parquet");
        GroupReadSupport readSupport = new GroupReadSupport();
        ParquetReader<Group> reader = new ParquetReader<Group>(path, readSupport);
        Group group = reader.read();
        System.out.printf("left is: %s\n", group.getString("left", 0));
        System.out.printf("right is: %s\n", group.getString("right", 0));
    }
}
