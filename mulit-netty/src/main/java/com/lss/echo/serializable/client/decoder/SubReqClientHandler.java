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
package com.lss.echo.serializable.client.decoder;

import com.lss.echo.serializable.domain.SubscribeReq;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Function: 利用nettty的ObjectDecoder编码器、ObjectEncoder解码器实现对普通POJO对象的序列化
 * <p>
 * Created by shuangshuangl on 2019/7/26.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class SubReqClientHandler extends ChannelInboundHandlerAdapter {

    public SubReqClientHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            ctx.write(subReq(i));
        }
        ctx.flush();
    }

    private SubscribeReq subReq(int i) {
        SubscribeReq subscribeReq = new SubscribeReq();
        subscribeReq.setAddress("重庆");
        subscribeReq.setPhoneName("123434");
        subscribeReq.setProductName("wol");
        subscribeReq.setUserName("Lilinfeng");
        subscribeReq.setSubReqID(i);
        return subscribeReq;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive server response :[" + msg + "]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}