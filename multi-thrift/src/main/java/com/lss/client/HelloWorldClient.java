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
package com.lss.client;

import com.lss.server.HelloWorld;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/12/5.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class HelloWorldClient {
    public static void main(String[] args) {
        System.out.println("客户端启动");
        TTransport tTransport = null;
        tTransport =  new TSocket("localhost",8080,30000);
        //TProtocol 负责将数据封装成message或者从message结构中解析出数据。
        //它将一个有类型的数据转为为字节流以交给TTransport进行传输，或者从TTransport中读取一定长度
        //的字节数据转化为特定类型的数据。
        TProtocol protocol = new TBinaryProtocol(tTransport);
        HelloWorld.Client client = new HelloWorld.Client(protocol);
        try {
            tTransport.open();
            String result =  client.sendString("Hello World");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (null != tTransport){
                tTransport.close();
            }
        }
    }
}