package com.master.demo.Parquet;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.example.data.GroupFactory;
import org.apache.parquet.example.data.simple.SimpleGroupFactory;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.example.GroupWriteSupport;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.MessageTypeParser;

import java.io.IOException;

public class ParquetToFile {

    public static void main(String[] args) throws IOException {
        MessageType schema = MessageTypeParser.parseMessageType(
                "message Pair {\n" +
                        " required binary left (UTF8);\n" +
                        " required binary right (UTF8);\n" +
                        "}"
        );
        GroupFactory groupFactory = new SimpleGroupFactory(schema);
        Group group = groupFactory.newGroup().append("left", "L").append("right", "R");

        Configuration conf = new Configuration();
        Path path = new Path("src/main/resources/parquet/data.parquet");
        GroupWriteSupport writeSupport = new GroupWriteSupport();
        //将Parquet模式设置到Configuration,再把Configuration对象传递给ParquetWriter
        GroupWriteSupport.setSchema(schema, conf);
        ParquetWriter<Group> writer = new ParquetWriter<Group>(path, conf, writeSupport);
        writer.write(group);
        writer.close();
    }
}
