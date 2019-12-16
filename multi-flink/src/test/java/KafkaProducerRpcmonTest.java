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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/8/7.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class KafkaProducerRpcmonTest {
    private static String brokerlist = "172.20.4.201:9092,172.20.4.204:9092,172.20.4.203:9092";
    private static String data = "{\"host\":\"11.17.72.188\",\"logmark\":\"antifraud-service\",\"value\":\"123\"}";
    private static String data1 = "{\"host\":\"11.17.72.188\",\"logmark\":\"fraud-service\",\"value\":\"1\"}";
    private static String data2 = "{\"host\":\"11.17.72.189\",\"logmark\":\"fraud-service\",\"value\":\"3\"}";
    private static String ss1 = "{\"host\":\"11.17.72.188\",\"logmark\":\"antifraud-service\",\"message\":\"127.0.0.1\\t" + (String.valueOf(System.currentTimeMillis())).substring(0, 10) + "\\t9\\t0.21038770675659\\t1\\t2\\t[9]\",\"offset\":31802,\"path\":\"/home/www/PHPServer/logs/statistic/st/RiskScore/getScoreV2|2019-06-12\",\"project\":\"antifraud-service\",\"topicid\":\"owl_rpcmon_overview\",\"type\":\"owl\",\"@version\":\"1\",\"@timestamp\":\"2019-08-16T19:40:04.498Z\"}";
    private static String exception = "{\"host\":\"11.16.32.114\",\"logmark\":\"cart-service\",\"message\":\"2019-08-16 10:59:06\\tCart::getCartForOut\\tCODE:500\\tMSG:WORKER EXIT UNEXPECTED  1 Call to undefined method Thrift\\\\Transport\\\\TFramedTransport::setSpecifiedAddress() in /home/www/Services/Vendor/Thrift/Lib/Thrift/ThriftInstance.php on line 274<br>REQUEST_DATA:[00000064800100010000000d67657443617274466f724f7574000000010b0001000000147b22756964223a2022313230343239363738227d0b00020000000966696e616e6369616c0b0003000000187b2270726f647563745f6e756d5f6c696d6974223a20337d00]<br>\\tsource_ip:0.0.0.0\\ttarget_ip:127.0.0.1\",\"offset\":22808,\"path\":\"/home/www/PHPServer/logs/statistic/log/2019-08-15\",\"project\":\"cart-service-backend\",\"topicid\":\"owl_rpcmon_exception\",\"type\":\"owl\",\"@version\":\"1\",\"@timestamp\":\"2019-08-16T10:59:15.885Z\"}\n";
/*
    private static String data = "{\"host\":\"11.17.72.188\",\"logmark\":\"antifraud-service\",\"message\":\"127.0.0.1\\t" + (String.valueOf(System.currentTimeMillis())).substring(0, 10) + "\\t9\\t0.21038770675659\\t1\\t2\\t[9]\",\"offset\":31802,\"path\":\"/home/www/PHPServer/logs/statistic/st/RiskScore/getScoreV2|2019-06-12\",\"project\":\"antifraud-service\",\"topicid\":\"owl_rpcmon_overview\",\"type\":\"owl\",\"@version\":\"1\",\"@timestamp\":\"2019-06-12T08:49:04.498Z\"}";
*/;

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");



        try {
            while (true) {
                String ss = "{\"host\":\"11.17.72.188\",\"logmark\":\"antifraud-service\",\"message\":\"127.0.0.1\\t" + (String.valueOf(System.currentTimeMillis())).substring(0, 10) + "\\t9\\t0.21038770675659\\t1\\t2\\t[9]\",\"offset\":31802,\"path\":\"/home/www/PHPServer/logs/statistic/st/RiskScore/getScoreV2|2019-06-12\",\"project\":\"antifraud-service\",\"topicid\":\"owl_rpcmon_overview\",\"type\":\"owl\",\"@version\":\"1\",\"@timestamp\":\""+sdf.format(new Date())+"\"}";
/*
                String s1 = "{\"host\":\"11.17.72.188\",\"logmark\":\"jiki-service\",\"message\":\"127.0.0.1\\t" + (String.valueOf(System.currentTimeMillis())).substring(0, 10) + "\\t9\\t0.21038770675659\\t1\\t2\\t[9]\",\"offset\":31802,\"path\":\"/home/www/PHPServer/logs/statistic/st/RiskScore/getScoreV2|2019-06-12\",\"project\":\"jiki-service\",\"topicid\":\"owl_rpcmon_overview\",\"type\":\"owl\",\"@version\":\"1\",\"@timestamp\":\""+sdf.format(new Date())+"\"}";
*/

                KeyedMessage<String, String> keyedMessage = new KeyedMessage<String, String>("owl_rpcmon_overview", ss);
/*
                KeyedMessage<String, String> keyedMessage1 = new KeyedMessage<String, String>("owl_rpcmon_overview", s1);
*/
                producer.send(keyedMessage);
/*
                producer.send(keyedMessage1);
*/

                Thread.sleep(1000);
            }

        } catch (Exception e) {
            throw new RuntimeException();
        }


    }

}