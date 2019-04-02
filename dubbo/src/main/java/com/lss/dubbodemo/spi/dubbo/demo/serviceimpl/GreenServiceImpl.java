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
package com.lss.dubbodemo.spi.dubbo.demo.serviceimpl;

import com.lss.dubbodemo.spi.dubbo.demo.DubboService;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/4/2.
 */
public class GreenServiceImpl implements DubboService {
    @Override
    public void sayHello(String name) {
        System.out.println("this is greenService");
    }
}