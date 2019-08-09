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
package flink.flink;

import com.alibaba.fastjson.JSON;
import flink.domain.Message;
import flink.domain.Msg;
import flink.influxdb.InfluxDBPoint;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.HashMap;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/7/19.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class Flink {
    private static final  String MEASURMENT = "trace_test";

    public static StreamExecutionEnvironment getExecutionEnvironment(){
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        environment.enableCheckpointing(5000); // 要设置启动检查点
        environment.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        return environment;
    }

    public static DataStream<InfluxDBPoint> getDataStream(DataStream<String> source){
        DataStream<InfluxDBPoint> stream = source.map(new RichMapFunction<String, InfluxDBPoint>() {

            @Override
            public InfluxDBPoint map(String s) throws Exception {
                Msg message = JSON.parseObject(s, Msg.class);
                long timestamp = System.currentTimeMillis();

                HashMap<String, String> tags = new HashMap<>();
                tags.put("project", String.valueOf(message.getProject()));
                /*tags.put("module", String.valueOf(message.getFirst_play()));
                tags.put("interf", String.valueOf(message.getCaton_time()));
                tags.put("env", message.getUid());*/
                tags.put("ip", message.getIp());


                HashMap<String, Object> fields = new HashMap<>();
                fields.put("num",message.getValue());
               /* fields.put("total", message.getUid());
                fields.put("fail", message.getUid());
                fields.put("success", message.getUid());
                fields.put("totalElapsed", message.getUid());
                fields.put("failElapsed", message.getUid());
                fields.put("sucElapsed", message.getUid());*/
                InfluxDBPoint influxDBPoint = new InfluxDBPoint(MEASURMENT, timestamp, tags, fields);
                return influxDBPoint;
            }
        }).keyBy(String.valueOf(new KeySelector<Msg, Object>() {
            @Override
            public Object getKey(Msg msg) throws Exception {
                return msg.project;
            }
        }));
        stream.print();
        return stream;
    }

}