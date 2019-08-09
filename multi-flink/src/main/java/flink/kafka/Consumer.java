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
package flink.kafka;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer08;

import java.util.Properties;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/7/19.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class Consumer {
    public  static FlinkKafkaConsumer08<String> consumer(){
        //kafka
        String zkCon = System.getProperty("kafka.zookeeper.connect");
        String brokerList = System.getProperty("kafka.broker.list");
        String groupId = System.getProperty("kafka.group.id");
        String topicId = System.getProperty("kafka.topic.id");

        Properties properties = new Properties();

        properties.setProperty("zookeeper.connect", zkCon);
        properties.setProperty("bootstrap.servers", brokerList);
        properties.setProperty("group.id", groupId);

        FlinkKafkaConsumer08<String> consumer = new FlinkKafkaConsumer08<String>(topicId, new SimpleStringSchema(), properties);
        
        return consumer;
    }
}