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
package com.lss.echo.http.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Function: Http文件服务器 启动类   待测试
 * 文件服务器使用http协议对外提供服务，当客户端通过浏览器访问文件服务器时，对访问路径进行检查，
 * 检查失败时返回HTTP 403错误，该页无法访问，如果校验通过，以链接的方式打开当前文件目录，每个目录或者
 * 文件都是个超链接，可以递归访问。
 * 如果是目录，可以递归访问它下面的子目录或者文件，如果是文件，且可读，则可以在浏览器端直接打开，
 * 或者通过【目标另存为】下载该文件。
 * <p>
 * Created by shuangshuangl on 2019/7/26.
 */
public class HttpFileServer {
    private static final String DEFAULT_URL = "/src/main/java/com/lss/echo/http/server";

    public static void main(String[] args) {
        int port = 8080;
        String url = DEFAULT_URL;
        try {
            if (args.length > 0) {
                port = Integer.valueOf(args[0]);
            }

            if (args.length > 1) {
                url = args[1];
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        new HttpFileServer().run(port, url);
    }

    public void run(final int port, final String url) {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                            ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                            ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                            ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                            ch.pipeline().addLast("fileServerHandler", new HttpFileServerHandler(url));
                        }
                    });
            ChannelFuture f = b.bind("127.0.0.1", port).sync();
            System.out.println("网址是http://127.0.0.1:" + port + url);
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}