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
package com.lss.echo.serializable.server.decoder;

import com.lss.echo.serializable.domain.SubscribeReq;
import com.lss.echo.serializable.domain.SubscribeResp;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Function: 利用nettty的ObjectDecoder编码器、ObjectEncoder解码器实现对普通POJO对象的序列化
 * <p>
 * Created by shuangshuangl on 2019/7/26.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class SubReqServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReq req = (SubscribeReq) msg;
        if ("Lilinfeng".equals(req.getUserName())){
            System.out.println("server accept client subscribe req :[" + req.toString() +"]");
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }

    private SubscribeResp resp(int subReqId){
        SubscribeResp resp = new SubscribeResp();
        resp.setSubReqId(subReqId);
        resp.setRespCode(0);
        resp.setDesc("Netty book order succeed,3 days later,sent to the designated address");
        return resp;
    }
}