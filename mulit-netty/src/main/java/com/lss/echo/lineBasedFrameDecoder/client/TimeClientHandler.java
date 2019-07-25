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
package com.lss.echo.lineBasedFrameDecoder.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/7/25.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {
    private byte[] bytes;
    private int counter;

    public TimeClientHandler() {
        bytes = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
    }


    /**
     * 当客户端和服务端的tcp连接建立成功以后，会调用channelActive，发送查询时间的指令给服务器
     * 调用writeAndFlush将请求消息发送给服务器。
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message ;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(bytes.length);
            message.writeBytes(bytes);
            ctx.writeAndFlush(message);

        }
    }

    /**
     * 当服务端收到请求后，返回应答消息，这是客户端调用channelRead,读取并打印消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        try {
            String body = (String) msg;
            System.out.println("Now is :" + body + ";counter is :" + ++counter);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 发生异常时，打印异常日志，释放客户端资源
     * 发生异常时，打印异常日志，释放客户端资源
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}