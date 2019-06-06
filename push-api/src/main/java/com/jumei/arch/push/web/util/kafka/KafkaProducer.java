/*
 * Copyright (C) 2019 jumei, Inc.
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
package com.jumei.arch.push.web.util.kafka;


import com.jumei.arch.push.web.controller.MessageController;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;

/**
 * Function: kafka相关实现类.
 * <p>
 * Created by shuangshuangl on 2019/2/27.
 * Copyright (c) 2019,shuangshuangl@jumei.com All Rights Reserved.
 */
@Component
public class KafkaProducer {
    @Autowired
    private KafkaConfig kafkaConfig;
    private static Producer<String, String> producer;
    private static KafkaProducer kafkaProducer;
    private static final Logger logger = LoggerFactory
            .getLogger(KafkaProducer.class);
    /**
     * 初始化kafka
     */
    @PostConstruct
    public void init() {
        try {
            kafkaProducer = this;
            Properties properties = new Properties();
            properties.setProperty("metadata.broker.list", kafkaConfig.getBrokerAddress());
            properties.setProperty("request.required.acks", "1");
            properties.setProperty("message.send.max.retries", "15");
            properties.setProperty("serializer.class", "kafka.serializer.StringEncoder");
            ProducerConfig producerConfig = new ProducerConfig(properties);
            producer = new Producer<>(producerConfig);
        }catch (Exception e){
            logger.error("init kafka meet error"+e.getMessage(),e);
        }

    }

    /**
     * 发送数据到kafka
     * @param data
     */
    public static void send(String data) {
        KeyedMessage<String, String> keyedMessage = new KeyedMessage<>(kafkaProducer.kafkaConfig.getTopic(), data);
        try {
            producer.send(keyedMessage);
        }catch (Exception e){
           logger.error("kafka send data meet error"+ e.getMessage(),e);
        }

    }


}