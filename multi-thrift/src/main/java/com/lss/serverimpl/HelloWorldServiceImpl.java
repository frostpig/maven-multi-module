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
package com.lss.serverimpl;

import com.lss.server.HelloWorld;
import org.apache.thrift.TException;

/**
 * Function: 服务端实现
 * <p>
 * Created by shuangshuangl on 2019/12/5.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class HelloWorldServiceImpl implements HelloWorld.Iface {
    public String sendString(String para) throws TException {
        System.out.println("接收到客户端传来的参数：" + para);
        String result = "服务端成功收到消息";
        return result;
    }


}