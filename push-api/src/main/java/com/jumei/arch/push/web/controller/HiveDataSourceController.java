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
package com.jumei.arch.push.web.controller;

import com.jumei.arch.push.web.service.HiveService;
import com.jumei.arch.push.web.service.PhoenixService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/3/1.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
@RestController
public class HiveDataSourceController {

    @Resource
    private HiveService hiveService;
    @Resource
    private PhoenixService phoenixService;

    @RequestMapping(value = "/getData")
    public List<String> getData() {
        return hiveService.getData();
    }
/*
    @RequestMapping(value = "/get")
    public void get() {
        phoenixService.test();
    }*/

}