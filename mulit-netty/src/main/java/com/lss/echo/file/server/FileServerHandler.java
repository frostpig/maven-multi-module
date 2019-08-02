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
package com.lss.echo.file.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/7/30.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class FileServerHandler extends SimpleChannelInboundHandler<String> {
    private static final String CR = System.getProperty("line.separator");

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        File file = new File(msg);
        if (file.exists()){
            if (!file.isFile()){
                ctx.writeAndFlush("is not a file:"+file+CR);
                return;
            }

            ctx.write(file + " "+ file.length() + CR);
            RandomAccessFile accessFile = new RandomAccessFile(msg,"r");
            FileRegion region = new DefaultFileRegion(accessFile.getChannel(),0,accessFile.length());
            ctx.write(region);
            ctx.writeAndFlush(CR);
            accessFile.close();
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}