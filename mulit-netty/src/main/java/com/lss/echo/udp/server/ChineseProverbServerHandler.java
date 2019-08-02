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
package com.lss.echo.udp.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/7/30.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class ChineseProverbServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private static final String[] DICTIONARY = {"只要功夫深，铁棒磨成针","旧时王谢堂前燕,飞入寻常百姓家",
            "洛阳亲友如想问,一片冰心在玉壶","一寸光阴一寸金,寸金难买寸光阴","老骥伏枥,志在千里,烈士暮年,壮心不已"};

    private String nextQuote(){

        //由于此功能存在多线程并发的操作，所以使用了线程安全随机类ThreadLocalRandom
        int quote= ThreadLocalRandom.current().nextInt(DICTIONARY.length);
        return DICTIONARY[quote];
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        //因为netty对UDP进行了封装，因此接收到的是DatagramPacket对象。

        //利用ByteBuf的toString(Charset)将msg转换为字符串。
       String req = msg.content().toString(CharsetUtil.UTF_8);

        System.out.println(req);

        //对请求消息进行合法性判断
        if ("谚语字典查询".equals(req)){
            ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("谚语查询结果：" + nextQuote(),CharsetUtil.UTF_8),msg.sender()));
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }
}

