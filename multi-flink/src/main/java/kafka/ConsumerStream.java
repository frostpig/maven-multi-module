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
package kafka;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import domain.Message;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.*;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/7/17.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class ConsumerStream {
    private static BlockingQueue<InfluxDBPoint> queue= new ArrayBlockingQueue<InfluxDBPoint>(50000);
    private static ExecutorService executorService = Executors.newFixedThreadPool(3);
    private static final  String MEASURMENT = "app_monitor";
    public static void main(String[] args) {
        InputStream inStream = ConsumerStream.class.getClassLoader().getResourceAsStream("flink.properties");
        Properties prop = new Properties();
        try {
            prop.load(inStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //kafka
        String zkCon = prop.getProperty("kafka.zookeeper.connect");
        String brokerList = prop.getProperty("kafka.broker.list");
        String groupId = prop.getProperty("kafka.group.id");
        String topicId = prop.getProperty("kafka.topic.id");

        //influxDB
        String influxUrl = prop.getProperty("influxdb.url");
        String username = prop.getProperty("influxdb.username");
        String password = prop.getProperty("influxdb.password");
        String database = prop.getProperty("influxdb.database");

        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        environment.enableCheckpointing(5000); // 要设置启动检查点
        environment.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);


        Properties properties = new Properties();

        properties.setProperty("zookeeper.connect", zkCon);
        properties.setProperty("bootstrap.servers", brokerList);
        properties.setProperty("group.id", groupId);

        FlinkKafkaConsumer08<String> consumer = new FlinkKafkaConsumer08<String>(topicId, new SimpleStringSchema(), properties);

        DataStream<String> source = environment.addSource(consumer);
        DataStream<InfluxDBPoint> stream = source.map(new RichMapFunction<String, InfluxDBPoint>() {

            @Override
            public InfluxDBPoint map(String s) throws Exception {
/*
                System.out.println("message =="+s);
*/
                Message message = JSON.parseObject(s, Message.class);
                long timestamp = System.currentTimeMillis();

                HashMap<String, String> tags = new HashMap<>();
                tags.put("establishTime", String.valueOf(message.getEstablish_time()));
                tags.put("firstTime", String.valueOf(message.getFirst_play()));
                tags.put("catonTime", String.valueOf(message.getCaton_time()));
                tags.put("uid", message.getUid());

                HashMap<String, Object> fields = new HashMap<>();
                fields.put("video_url", message.getVideo_url());
                InfluxDBPoint influxDBPoint = new InfluxDBPoint(MEASURMENT, timestamp, tags, fields);
                queue.offer(influxDBPoint);
/*
                System.out.println(tags.toString() + fields.toString()+ "measurement ===" + measurement);
*/
                return influxDBPoint;
            }
        });

        InfluxDBConfig influxDBConfig = InfluxDBConfig.builder(influxUrl, username, password, database)
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