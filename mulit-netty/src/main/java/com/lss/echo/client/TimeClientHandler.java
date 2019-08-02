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
package com.lss.echo.client;

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
    private  final ByteBuf buf;

    public TimeClientHandler() {
        byte[] bytes =  "QUERY TIME ORDER".getBytes();
        buf = Unpooled.buffer(bytes.length);
        buf.writeBytes(bytes);

    }


    /**
     * 当客户端和服务端的tcp连接建立成功以后，会调用channelActive，发送查询时间的指令给服务器
     * 调用writeAndFlush将请求消息发送给服务器。
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(buf);
    }

    /**
     * 当服务端收到请求后，返回应答消息，这是客户端调用channelRead,读取并打印消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        try {
            ByteBuf byteBuf = (ByteBuf) msg;
            byte[] bytes = new byte[byteBuf.readableBytes()];
            //将当前ByteBuf的数据读取到byte[]数组中。
            byteBuf.readBytes(bytes);
            String body = new String(bytes,"UTF-8");
            ctx.write(body);
            System.out.println("Now is :" +body);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 发生异常时，打印异常日志，释放客户端资源
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
     }
}