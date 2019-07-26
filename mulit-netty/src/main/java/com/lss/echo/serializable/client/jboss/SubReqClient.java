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
package com.lss.echo.serializable.client.jboss;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Function: 利用nettty的ObjectDecoder编码器、ObjectEncoder解码器实现对普通POJO对象的序列化
 * <p>
 * Created by shuangshuangl on 2019/7/26.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class SubReqClient {
    public static void main(String[] args) {
        int port = 8080;
        try {
            if (args != null && args.length>0){
                port = Integer.valueOf(args[0]);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        new SubReqClient().connect("127.0.0.1",port);

    }

    public void connect(String host,int port){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new SubReqClientHandler());
                        }


                    });
            ChannelFuture f = b.connect(host,port).sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }
}