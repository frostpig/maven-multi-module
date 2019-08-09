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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/8/7.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class KafkaProducerRpcmon {
    private static String brokerlist = "172.20.4.201:9092,172.20.4.204:9092,172.20.4.203:9092";
private static String data = "{\"host\":\"10.17.72.188\",\"logmark\":\"antifraud-service\",\"value\":\"123\"}";
private static String data1 = "{\"host\":\"10.17.72.188\",\"logmark\":\"fraud-service\",\"value\":\"1\"}";
private static String data2 = "{\"host\":\"10.17.72.189\",\"logmark\":\"fraud-service\",\"value\":\"3\"}";
private static String data3 = "{\"host\":\"10.17.72.189\",\"logmark\":\"dfs-service\",\"value\":\"12\"}";

/*
    private static String data = "{\"host\":\"10.17.72.188\",\"logmark\":\"antifraud-service\",\"message\":\"127.0.0.1\\t" + (String.valueOf(System.currentTimeMillis())).substring(0, 10) + "\\t9\\t0.21038770675659\\t1\\t2\\t[9]\",\"offset\":31802,\"path\":\"/home/www/PHPServer/logs/statistic/st/RiskScore/getScoreV2|2019-06-12\",\"project\":\"antifraud-service\",\"topicid\":\"owl_rpcmon_overview\",\"type\":\"owl\",\"@version\":\"1\",\"@timestamp\":\"2019-06-12T08:49:04.498Z\"}";
*/
           ;

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
        KeyedMessage<String, String> keyedMessage1 = new KeyedMessage<String, String>("owl_rpcmon_overview", data1);
        KeyedMessage<String, String> keyedMessage2 = new KeyedMessage<String, String>("owl_rpcmon_overview", data2);
        KeyedMessage<String, String> keyedMessage3 = new KeyedMessage<String, String>("owl_rpcmon_overview", data3);
        List<KeyedMessage<String,String>> list = new ArrayList<>();
        list.add(keyedMessage);
        list.add(keyedMessage1);
        list.add(keyedMessage2);
        list.add(keyedMessage3);

        try {
            while (true) {
                producer.send(list);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }


    }

}