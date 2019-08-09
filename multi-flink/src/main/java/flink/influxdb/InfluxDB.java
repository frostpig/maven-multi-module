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
package flink.influxdb;

import java.util.concurrent.TimeUnit;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/7/19.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class InfluxDB {
    public static InfluxDBConfig getInfluxDBConfig(){

        //influxDB
        String influxUrl = System.getProperty("influxdb.url");
        String username = System.getProperty("influxdb.username");
        String password = System.getProperty("influxdb.password");
        String database = System.getProperty("influxdb.database");

        InfluxDBConfig influxDBConfig = InfluxDBConfig.builder(influxUrl, username, password, database)
                .batchActions(1000)
                .flushDuration(100, TimeUnit.MILLISECONDS)
                .enableGzip(true)
                .build();
        influxDBConfig.setRetentionPolicy("OS");
        return influxDBConfig;
    }
}