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
package com.jumei.arch.push.web.service;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Function: 1、连接hive；.
 *           2、操作hive数据库
 * <p>
 * Created by shuangshuangl on 2019/3/1.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
@Service
public class HiveService {
    @Autowired
    @Qualifier("hiveJdbcDataSource")
    private DataSource jdbcDataSource;
    private Statement statement;
    private ResultSet res ;
    public List<String> getData(){
        List<String> list = new ArrayList<>();
        statement = connectHive();
        String sql = "select * from test223 where ts= 20180510 limit 1";
        try {
            res = statement.executeQuery(sql);
            while (res.next()) {
                list.add(res.getString(" log_rowkey"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    private Statement connectHive(){
        Statement statement = null;
        try {
            statement = jdbcDataSource.getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

}