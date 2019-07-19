/*
 * Copyright (C) 2018 jumei, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package influxdb;


import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer08;
import util.CustomWatermarkEmitter;
import util.InfluxDBConfig;
import util.InfluxDBPoint;
import util.InfluxDBSink;

import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/7/17.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class StoreToInfluxdb {
    public static void main(String[] args) {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        environment.enableCheckpointing(5000); // 要设置启动检查点
        environment.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        Properties properties = new Properties();

        properties.setProperty("zookeeper.connect", "172.20.4.201:2181,172.20.4.203:2181,172.20.4.204:2181");
        properties.setProperty("bootstrap.servers", "172.20.4.201:9092,172.20.4.203:9092,172.20.4.204:9092");
        properties.setProperty("group.id", "flink");

        FlinkKafkaConsumer08<String> consumer = new FlinkKafkaConsumer08<String>("owl_rpcmon_overview", new SimpleStringSchema(), properties);
        consumer.assignTimestampsAndWatermarks(new CustomWatermarkEmitter());

        DataStream<String> source = environment.addSource(consumer);
        DataStream<InfluxDBPoint> stream = source.map(new RichMapFunction<String, InfluxDBPoint>() {
            @Override
            public InfluxDBPoint map(String s) throws Exception {
                String[] input = s.split(" ");

                String measurement = input[0];
                long timestamp = System.currentTimeMillis();

                HashMap<String, String> tags = new HashMap<>();
                tags.put("host", input[1]);
                tags.put("region", "region#" + String.valueOf(input[1].hashCode() % 20));

                HashMap<String, Object> fields = new HashMap<>();
                fields.put("value1", input[1].hashCode() % 100);
                fields.put("value2", input[1].hashCode() % 50);

                System.out.println(tags.toString() + fields.toString()+ "me" + measurement);
                return new InfluxDBPoint(measurement, timestamp, tags, fields);
            }
        });
        InfluxDBConfig influxDBConfig = InfluxDBConfig.builder("http://172.20.4.202:8086", "root", "jumeiops", "tset")
                .batchActions(1000)
                .flushDuration(100, TimeUnit.MILLISECONDS)
                .enableGzip(true)
                .build();
        influxDBConfig.setRetentionPolicy("OS");

        stream.addSink(new InfluxDBSink(influxDBConfig));
        try {
            environment.execute("\"Flink Streaming Java API Skeleton\"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}