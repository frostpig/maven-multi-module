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

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/7/25.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class TimerServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("receive channel");

        try {

            ByteBuf byteBuf = (ByteBuf) msg;
            System.out.println(msg);
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            System.out.println(bytes);
            String body = new String(bytes,"UTF-8");
            System.out.println("The time server is receive order:"+body);
            String currentime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString():"BAD ORDER";
            System.out.println(currentime);
            ByteBuf response = Unpooled.copiedBuffer(currentime.getBytes());
            //异步发送应答消息给客户端
            ctx.write(response);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }


    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //作用是将消息发送队列中的内容写入到SocketChannel中发送给对方。
        //从性能角度考虑吗，为了防止频繁的唤醒selector进行消息发送，Netty的write方法并不直接将消息写入SocketChannel中，
        //调用write只是把待发送的消息发送到发送缓存数组中，再通过调用Flush,将发送缓冲区中的消息全部写入socketChannel中。
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}