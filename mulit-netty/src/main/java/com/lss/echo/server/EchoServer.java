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
package com.lss.echo.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


import java.net.InetSocketAddress;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/6/6.
 * Copyright (c) 2019,shuangshuangl@jumei.com All Rights Reserved.
 */
public class EchoServer {
    private  final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1){
            System.err.println("Usage: " + EchoServer.class.getSimpleName() + "<port>");
            return;
        }

        int port = Integer.parseInt(args[0]);
        new EchoServer(8180).start();
    }

    private  void start() throws  Exception{
       NioEventLoopGroup group = new NioEventLoopGroup();
       try{
           ServerBootstrap b = new ServerBootstrap();
           b.group(group)
                   .channel(NioServerSocketChannel.class)
                   .localAddress(new InetSocketAddress(port))
                   .childHandler(new ChannelInitializer<SocketChannel>() {
                       protected void initChannel(SocketChannel ch) throws Exception {
                           ch.pipeline().addLast(new EchoServerHandler());
                       }
                   });

           ChannelFuture channelFuture = b.bind(8180).sync();
           System.out.println(EchoServer.class.getName() + " started and listen on " + channelFuture.channel().localAddress());
           channelFuture.channel().closeFuture().sync();
       }finally {
           group.shutdownGracefully().sync();
       }

    }

}