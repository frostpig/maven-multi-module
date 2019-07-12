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

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Function: Please Descrip This Class.
 * <p> root/jumeiops 172.20.4.202
 *
 * @author timothy
 * @date 2019-05-23
 * Copyright (c) 2019,timothy.yue12@gmail.com All Rights Reserved.
 */
public class Demo {

    private static String[] tags = {"group", "module", "interf", "ip"};
    private static String[] groups = {"show", "user-service", "pay-web", "shoppe", "growth", "sociality", "openapi", "work-server", "mob-service"};
    private static String[] modules = {"m1", "m2", "m3", "m4", "m5", "m6", "m7", "m8", "m9"};
    private static String[] interfs = {"i1", "i2", "i3", "i4", "i5", "i6", "i7", "i8", "i9"};
    private static int[] reqCounts = {5, 10, 15, 20, 25, 30, 35, 40, 55};
    private static float[] reqElapseds = {0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1f, 1.5f, 2.0f, 2.5f, 3f};


    public static void main(String[] args) throws InterruptedException, URISyntaxException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.currentTimeMillis();


        Random random = new Random();


        while (true) {
          /*  InfluxDB influxDB = connect("root", "jumeiops", "http://172.20.4.202:8086");
            influxDB.setDatabase("jmtest");*/
            InfluxDB influxDB = connect("root", "jumeiops", "http://172.20.4.202:8086");
            influxDB.setDatabase("rpcmon");
            influxDB.setRetentionPolicy("retention_one_week");
       /*     QueryResult queryResult =  query(influxDB,"2019-06-19 14:24:12","2019-06-19 14:27:12");
            QueryResult.Result result = queryResult.getResults().get(0);
            Map<String, Object> map = new HashMap<String, Object>();
            List<QueryResult.Series> seriesList = result.getSeries();
            for (QueryResult.Series series :seriesList){
                List<String> columns = series.getColumns();
                List<List<Object>> values = series.getValues();
                for (List<Object> objects : values){
                    for (String column :columns){
                       Object value =  objects.get(columns.indexOf(column));
                        map.put(column,value);
                    }
                }

            }*/


            for (int i = 0; i < 100; i++) {
                String group = groups[random.nextInt(4)];
                String module = modules[random.nextInt(9)];
                String interf = interfs[random.nextInt(9)];
                int sum = reqCounts[random.nextInt(9)];
                float elapsed = reqElapseds[random.nextInt(10)];
                Map<String, String> tags = new HashMap<String, String>();
                tags.put("project", "order-service");
                tags.put("module", "getNum");
                tags.put("interf", "sfds");
                tags.put("host", "127.0.0.1");
                tags.put("env", "04");
                tags.put("failnum","22");


                Map<String, Object> fields = new HashMap<String, Object>();
                fields.put("success", reqCounts[random.nextInt(9)]);
                fields.put("sucElapsed",  reqElapseds[random.nextInt(10)]);
                fields.put("fail", reqCounts[random.nextInt(9)]);
                fields.put("failElapsed",  reqElapseds[random.nextInt(10)]);
                fields.put("total", reqCounts[random.nextInt(9)]);
                fields.put("totalElapsed", reqElapseds[random.nextInt(10)]);
                fields.put("date", sdf.format(new Date().getTime()));
                fields.put("timestamp", new Date().getTime());
                insert("influx_trace_stats", tags, fields, new Date().getTime(), TimeUnit.SECONDS, influxDB);

                System.out.println(tags.toString() + ", " + fields.toString());
            }
            influxDB.close();
            Thread.sleep(1000 * 60);

        }
    }


    public static InfluxDB connect(String username, String password, String url) {
        return InfluxDBFactory.connect(url, username, password);
    }

    public static void insert(String measurement, Map<String, String> tags, Map<String, Object> fields, long time,
                              TimeUnit timeUnit, InfluxDB influxDB) {
        Point.Builder builder = Point.measurement(measurement);
        builder.tag(tags)
                .fields(fields)
        .time(time*1000000,timeUnit);
        influxDB.write(builder.build());
    }

    public static QueryResult query(InfluxDB influxDB, String startTime, String endTime) {
        Query query = new Query("select * from trace_stats where time <= '" + endTime + "' tz('Asia/Shanghai')", "jmtest");
        return influxDB.query(query);
    }

}
