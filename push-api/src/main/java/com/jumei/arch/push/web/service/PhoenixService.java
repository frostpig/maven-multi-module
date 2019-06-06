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

import com.alibaba.druid.pool.DruidDataSource;
import com.jumei.arch.push.web.util.phoenix.PhoenixConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/3/4.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
@Service
public class PhoenixService {
    private Connection conn;
    private Statement stat;
    private ResultSet rs;

    @Autowired
    private  PhoenixConfig phoenixConfig;

    public void test() {
        conn = GetConnection();
        try {
            stat = conn.createStatement();
            String sql = "";
            sql = " select 1 from heart_test limit 1";
            rs = stat.executeQuery(sql);
            while (rs.next()) {
              String name = "ma";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stat.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    private Connection GetConnection() {
        Connection con = null;
        String driver =phoenixConfig.getDriverClassName();
        // String url = "jdbc:phoenix:192.168.206.21:2181";

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (con == null) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("jdbc:phoenix:thin:url=http://")
                        .append(phoenixConfig.getUrl())
                        .append(";username=").append(phoenixConfig.getUsername())
                        .append(";appKey=").append(phoenixConfig.getPassword())
                        .append(";serialization=JSON");
                con = DriverManager.getConnection(sb.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return con;
    }
}