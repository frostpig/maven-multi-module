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
import flink.kafka.Consumer;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch5.ElasticsearchSink;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer08;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.*;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/8/12.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class EsMain {
    //要加载的配置文件
    private static final String KAFKA_CONFIG_NAME = "flink.properties";

    public static void main(String[] args) throws Exception {
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
            DataStream<String> input = environment.addSource(consumer);


            Map<String, String> config = new HashMap<>();
            config.put("cluster.name", "cd_dev_filelog_es");
            // This instructs the sink to emit after every element, otherwise they would be buffered
            config.put("bulk.flush.max.actions", "1");
            List<InetSocketAddress> transportAddresses = new ArrayList<>();
            transportAddresses.add(new InetSocketAddress(InetAddress.getByName("192.168.16.26"), 9310));
            transportAddresses.add(new InetSocketAddress(InetAddress.getByName("192.168.16.27"), 9310));
            input.addSink(new ElasticsearchSink(config, transportAddresses, new ElasticsearchSinkFunction<String>() {

                public IndexRequest createIndexRequest(String element) {
                    Map<String, String> json = new HashMap<>();

                    json.put("time", "2019-08-15T09:02:54.563Z");
                    json.put("project", "order-service");
                    json.put("interf", "charge");
                    json.put("logmark", "order-service");
                    json.put("module", "Payment");
                    json.put("title", "exception");
                    json.put("status", "COde:0");
                    json.put("serverIp", "172.31.9.206");
                    json.put("clientIp", "172.31.9.205");
                    json.put("msg", "exception");

                    return Requests.indexRequest().index("rpcmon_sync_exception").type("exception").source(json);
                }

                @Override
                public void process(String element, RuntimeContext ctx, RequestIndexer indexer) {
                    indexer.add(createIndexRequest(element));
                }
            }));

            environment.execute("ES");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}