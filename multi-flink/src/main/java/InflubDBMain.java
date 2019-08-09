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

import flink.flink.Flink;
import flink.influxdb.InfluxDB;
import flink.influxdb.InfluxDBConfig;
import flink.influxdb.InfluxDBPoint;
import flink.influxdb.InfluxDBSink;
import flink.kafka.Consumer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer08;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/8/7.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class InflubDBMain {
    public static void main(String[] args) {

        try {
            InputStream inStream = Main.class.getClassLoader().getResourceAsStream("flink.properties");
            Properties prop = new Properties();
            try {
                prop.load(inStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (Map.Entry<Object, Object> cfg : prop.entrySet()) {
                System.setProperty(String.valueOf(cfg.getKey()), String.valueOf(cfg.getValue()));
            }

            StreamExecutionEnvironment environment = Flink.getExecutionEnvironment();
            FlinkKafkaConsumer08<String> consumer = Consumer.consumer();
            DataStream<String> source = environment.addSource(consumer);
            DataStream<InfluxDBPoint> dataStream = Flink.getDataStream(source);
            dataStream.print();/*
            InfluxDBConfig influxDBConfig = InfluxDB.getInfluxDBConfig();
            dataStream.addSink(new InfluxDBSink(influxDBConfig));*/
            environment.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}