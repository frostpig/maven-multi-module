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


import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;
import java.util.Random;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/7/17.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class KafkaProducer {
    private static String brokerlist = "172.20.4.201:9092,172.20.4.204:9092,172.20.4.203:9092";
    private static String data = "{\"uid\":\"ljqw283823\",\"brand\":\"HUAWEI\",\"module\":\"P30\",\"os\":\"android\",\"version\":\"1.32\",\"client_ip\": \"xx\",\"video_url\": \"xxx\",\"server_ip\": \"xxx\",\"dns_time\": 1542453234561 ,\"establish_time\": 1542453234561,\"first_play\": 1542453234561,\"caton_time\": 1542453234561,\"caton_counter\": 2,\"video_duration\": 435,\"play_duration\": 12,\"http_status\": \"xxxx\"}";



    public static void main(String[] args) {
        producer();
    }

    public static void producer() {
        Properties properties = new Properties();
        properties.setProperty("metadata.broker.list", brokerlist);
        properties.setProperty("request.required.acks", "1");
        properties.setProperty("message.send.max.retries", "15");
        properties.setProperty("serializer.class", "kafka.serializer.StringEncoder");
        ProducerConfig producerConfig = new ProducerConfig(properties);
        Producer<String, String> producer = new Producer<String, String>(producerConfig);
        Random random = new Random();

        KeyedMessage<String, String> keyedMessage = new KeyedMessage<String, String>("owl_rpcmon_overview", data);
        try {
            while (true) {
                producer.send(keyedMessage);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }


    }

}
