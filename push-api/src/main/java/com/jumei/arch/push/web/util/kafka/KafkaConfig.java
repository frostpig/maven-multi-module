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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/2/27.
 * Copyright (c) 2019,shuangshuangl@jumei.com All Rights Reserved.
 */
@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaConfig {
    private  String topic;
    private  String brokerAddress;


    public KafkaConfig() {
    }

    public KafkaConfig(String topic, String brokerAddress) {
        this.topic = topic;
        this.brokerAddress = brokerAddress;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getBrokerAddress() {
        return brokerAddress;
    }

    public void setBrokerAddress(String brokerAddress) {
        this.brokerAddress = brokerAddress;
    }

    @Override
    public String toString() {
        return "KafkaConfig{" +
                "topic='" + topic + '\'' +
                ", brokerAddress='" + brokerAddress + '\'' +
                '}';
    }
}