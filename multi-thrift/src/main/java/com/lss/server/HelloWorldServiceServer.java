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
package com.lss.server;

import com.lss.serverimpl.HelloWorldServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * Function: 服务端实现代码，把HelloWorldServiceServerImpl作为一个具体的处理器传递给Thrift服务器
 * <p>
 * Created by shuangshuangl on 2019/12/5.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class HelloWorldServiceServer {
    public static void main(String[] args) {
        //关联处理器
        TProcessor tProcessor = new HelloWorld.Processor<HelloWorld.Iface>(new HelloWorldServiceImpl());

        try {
            TServerSocket serverSocket = new TServerSocket(8080);
            TServer.Args tArg = new TServer.Args(serverSocket);
            tArg.processor(tProcessor);
            tArg.protocolFactory(new TBinaryProtocol.Factory());
            TServer server = new TSimpleServer(tArg);
            server.serve();

        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
}