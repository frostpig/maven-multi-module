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
package com.lss.echo.websocket;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.util.Date;

/**
 * Function:Netty websocket时间服务器开发
 * 测试成功
 * <p>
 * Created by shuangshuangl on 2019/7/30.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler {
    private WebSocketServerHandshaker handshaker;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            //传统的http接入
        if (msg instanceof FullHttpRequest){
           handleHttpRequest(ctx, (FullHttpRequest) msg);
        }else if (msg instanceof WebSocketFrame){
            handleWebsocketFrame(ctx, (WebSocketFrame) msg);
        }


    }

    private void handleHttpRequest(ChannelHandlerContext ctx,FullHttpRequest fullHttpRequest){
        //如果Http解码失败，返回异常
        if (!fullHttpRequest.getDecoderResult().isSuccess()
                || !"websocket".equals(fullHttpRequest.headers().get("Upgrade"))){
            sendHttpResponse(ctx,fullHttpRequest,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        //构造握手响应返回，本机测试
        WebSocketServerHandshakerFactory webSocketServerHandshakerFactory = new WebSocketServerHandshakerFactory("ws://localhost:8080/websocket",null,false);
        handshaker = webSocketServerHandshakerFactory.newHandshaker(fullHttpRequest);
        if (handshaker == null){
            webSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        }else {
            handshaker.handshake(ctx.channel(),fullHttpRequest);
        }
    }

    private void handleWebsocketFrame(ChannelHandlerContext context, WebSocketFrame webSocketFrame){
        //判断是否是关闭链路的指令
        if (webSocketFrame instanceof CloseWebSocketFrame){

            handshaker.close(context.channel(),((CloseWebSocketFrame) webSocketFrame).retain());
            return;
        }

        //判断是否是Ping消息
        if (webSocketFrame instanceof PingWebSocketFrame){
            context.channel().write(new PingWebSocketFrame(webSocketFrame.content()));
            return;
        }

        //本例子只支持文本，不支持二进制
        if (!(webSocketFrame instanceof TextWebSocketFrame)){
             throw  new UnsupportedOperationException(String.format("s% frame types not supported",webSocketFrame.getClass().getName()));
        }

        //返回应答消息
        String request = ((TextWebSocketFrame) webSocketFrame).text();
        context.channel().write(new TextWebSocketFrame(request + ",欢迎使用Netty WebSocket服务，现在时刻:"+new Date().toString()));
    }

    private void sendHttpResponse(ChannelHandlerContext context, FullHttpRequest fullHttpRequest, FullHttpResponse fullHttpResponse){
        //返回应答给客户端
        if (fullHttpResponse.getStatus().code() != 200){
            ByteBuf buf = Unpooled.copiedBuffer(fullHttpResponse.getStatus().toString(), CharsetUtil.UTF_8);
            fullHttpResponse.content().writeBytes(buf);
            buf.release();
        }

        //如果是非keep-alive,则关闭连接
        ChannelFuture f = context.channel().writeAndFlush(fullHttpResponse);
        if (! HttpHeaders.isKeepAlive(fullHttpRequest) ||fullHttpResponse.getStatus().code() != 200){
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private void setContentLength(FullHttpResponse fullHttpResponse,int length){

    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
       ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       ctx.close();
    }

}