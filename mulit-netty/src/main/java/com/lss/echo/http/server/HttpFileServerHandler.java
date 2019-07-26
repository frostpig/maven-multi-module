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

import com.sun.activation.registries.MimeTypeFile;
import com.sun.xml.internal.bind.v2.runtime.Location;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.apache.http.entity.ContentType;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.PortableInterceptor.LOCATION_FORWARD;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.util.regex.Pattern;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/7/26.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private String url;
    private static  final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"]");

    public HttpFileServerHandler(String url) {
        this.url = url;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        if (!msg.getDecoderResult().isSuccess()) {
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    private static void sendError(ChannelHandlerContext context, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status,
                Unpooled.copiedBuffer("Failure" + status, CharsetUtil.UTF_8));
        response.headers().set(, "text/plain; charser=utf-8");
        context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void setContenTypeHeader(HttpResponse httpResponse, File file){
        MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
        httpResponse.headers().set(,mimetypesFileTypeMap.getContentType(file.getPath()));

    }

    private static  void sendRedirect(ChannelHandlerContext context,String newUrl){
        FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,FOUND);
        fullHttpResponse.headers().set(LOCATION,newUrl);
        context.writeAndFlush(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
    }

    private String sanitizeUri(String uri){

    }

}