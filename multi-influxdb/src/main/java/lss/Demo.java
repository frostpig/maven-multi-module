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
package lss;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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


    public static void main(String[] args) throws InterruptedException {


        Random random = new Random();


        while (true) {
            InfluxDB influxDB = connect("root", "jumeiops", "http://172.20.4.202:8086");
            influxDB.setDatabase("tset");
           /* influxDB.query(new Query("CREATE RETENTION POLICY MIN ON  tset  DURATION 30h REPLICATION 2 SHARD DURATION 30m DEFAULT"));
            */
           influxDB.setRetentionPolicy("OS");

            for (int i = 0; i < 100; i++) {
                String group = groups[random.nextInt(4)];
                String module = modules[random.nextInt(9)];
                String interf = interfs[random.nextInt(9)];
                int sum = reqCounts[random.nextInt(9)];
                float elapsed = reqElapseds[random.nextInt(10)];
                Map<String, String> tags = new HashMap<String, String>();
                tags.put("group", group);
                tags.put("module", module);
                tags.put("interf", interf);

                Map<String, Object> fields = new HashMap<String, Object>();
                fields.put("sum", sum);
                fields.put("elapsed", elapsed);
                insert("rpcmon", tags, fields, System.currentTimeMillis(), TimeUnit.MILLISECONDS, influxDB);

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
                .time(time, timeUnit);
        System.out.println(builder.build().toString());
        influxDB.write(builder.build());
    }
}
