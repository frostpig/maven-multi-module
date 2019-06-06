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
package com.jumei.arch.push.web.util.hive;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Function: 将hive数据库的配置 bean注入到DataSource
 * <p>
 * Created by shuangshuangl on 2019/3/1.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
@Configuration
public class HiveJdbcConfig {
    @Autowired
    private HiveConfig hiveConfig;

    @Bean(name = "hiveJdbcDataSource")
    @Qualifier("hiveJdbcDataSource")
    public DataSource dataSource(){
        DataSource dataSource = new DataSource();
        dataSource.setUrl(hiveConfig.getUrl());
        dataSource.setUsername(hiveConfig.getUsername());
        dataSource.setPassword(hiveConfig.getPassword());
        dataSource.setDriverClassName(hiveConfig.getDriveClassName());
        return dataSource;
    }

    @Bean(name = "hiveJdbcTemplate")
    public JdbcTemplate hiveJdbcTemplate(@Qualifier("hiveJdbcDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}