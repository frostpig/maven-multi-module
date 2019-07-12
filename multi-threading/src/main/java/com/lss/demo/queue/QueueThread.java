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
package com.lss.demo.queue;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/6/4.
 * Copyright (c) 2019,shuangshuangl@jumei.com All Rights Reserved.
 */

public class  QueueThread{
    private static final Logger LOG = LoggerFactory.getLogger(InfluxService.class);
    private static final String TRACE_STATS_MEASUREMENT = "trace_stats";
    private InfluxDB influxDB;
    private final int MAXSIZE = 50000;
    private BlockingQueue<InfluxOverviewDTO> queue = new ArrayBlockingQueue<InfluxOverviewDTO>(MAXSIZE);
    private BatchPoints batchPoints;
    private boolean isRunning = true;
    private List<InfluxOverviewDTO> dataList = new ArrayList<InfluxOverviewDTO>();
    private int DEFTAULT_SIZE = 200;
    private final int MAX_THREADS = 2;
    private final String DATABASE = "influxdb.database";
    private final String URL = "influxdb.url";
    private final String USERNAME = "influxdb.username";
    private final String PASSWORD = "influxdb.password";
    private ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);

        private static class QueueServiceHolder {
        private static QueueThread INSTANCE = new QueueThread();
    }

    public static QueueThread getINSTANCE() {
        return QueueServiceHolder.INSTANCE;
    }

    /**
     * 获取influxdb配置，并建立连接
     * @return
     */
    private InfluxDB connect() {
        String url = System.getProperty(URL);
        String username = System.getProperty(USERNAME);
        String password = System.getProperty(PASSWORD);
        return InfluxDBFactory.connect(url, username, password);
    }

    /**
     * 连接infulxDB
     *
     * @return
     */
    private QueueThread() {
        queueListener();
        Runtime.getRuntime().addShutdownHook(new ShutdownThread());
    }

    public void saveQueue(InfluxOverviewDTO influxOverviewDTO) {
        try {
            queue.offer(influxOverviewDTO);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    /**
     * 保存概要数据到trace_stats
     */
    private void saveDataToTraceStats(List<InfluxOverviewDTO> dataList, String database) {
        try {
            this.batchPoints = BatchPoints
                    .database(database)
                    .consistency(InfluxDB.ConsistencyLevel.ALL)
                    .build();

            for (int i = 0; i < dataList.size(); i++) {
                InfluxOverviewDTO influxOverviewDTO = dataList.get(i);
                Point point = transferDataToPoints(TRACE_STATS_MEASUREMENT, influxOverviewDTO.getTags(), influxOverviewDTO.getFields(),
                        influxOverviewDTO.getTime(), TimeUnit.MILLISECONDS);
                batchPoints.point(point);
            }
            this.influxDB = connect();
            this.influxDB.write(batchPoints);

        } catch (Exception e) {
            LOG.error(e.getMessage());
        } finally {
            this.influxDB.close();
        }
    }

    /**
     * 插入数据公共方法
     *
     * @param measurement
     * @param tags
     * @param fields
     * @param time
     * @param timeUnit
     */
    private Point transferDataToPoints(String measurement, Map<String, String> tags, Map<String, Object> fields, long time,
                                       TimeUnit timeUnit) {

        Point point = Point.measurement(measurement).tag(tags)
                .fields(fields)
                .time(time, timeUnit).build();
        LOG.info(tags.toString() + ", " + fields.toString());
        return point;
    }


    private void queueListener() {

        executorService.submit(new Runnable() {
            InfluxOverviewDTO influxOverviewDTO;
            String database = System.getProperty(DATABASE);
            long startTime = System.currentTimeMillis();
            @Override
            public void run() {
                while (isRunning) {
                    try {
                        influxOverviewDTO = queue.poll();
                        if (influxOverviewDTO != null) {
                            dataList.add(influxOverviewDTO);
                        }
                        if (dataList.size() > DEFTAULT_SIZE ||  (System.currentTimeMillis() - startTime) > 5 * 1000) {
                            saveDataToTraceStats(dataList, database);
                            dataList.clear();
                            startTime = System.currentTimeMillis();
                            LOG.error("队列当前大小===========》"+queue.size());
                        }
                    } catch (Exception e) {
                        LOG.error(e.getMessage());
                    }

                }
                if (!isRunning && dataList.size() > 0) {
                    try {
                        saveDataToTraceStats(dataList, database);
                        dataList.clear();
                    } catch (Exception e) {
                        LOG.error("queueTake meetError:" + JSON.toJSONString(dataList), e);
                    }
                }
            }
        });

    }

    private class ShutdownThread extends Thread {
        @Override
        public void run() {
            isRunning = false;
            executorService.shutdown();
        }
    }
}

