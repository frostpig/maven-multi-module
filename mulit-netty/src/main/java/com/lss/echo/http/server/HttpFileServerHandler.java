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

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;
import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static org.apache.http.HttpHeaders.*;

/**
 * Function: 文件服务器  待测试
 * <p>
 *  * 文件服务器使用http协议对外提供服务，当客户端通过浏览器访问文件服务器时，对访问路径进行检查，
 *  * 检查失败时返回HTTP 403错误，该页无法访问，如果校验通过，以链接的方式打开当前文件目录，每个目录或者
 *  * 文件都是个超链接，可以递归访问。
 *  * 如果是目录，可以递归访问它下面的子目录或者文件，如果是文件，且可读，则可以在浏览器端直接打开，
 *  * 或者通过【目标另存为】下载该文件。
 * Created by shuangshuangl on 2019/7/26.
 *
 */
public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private String url;
    private static final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"]");
    private static final Pattern ALLOWED_FILE_NAME = Pattern.compile("[A-Za-z0-9][-_Aa-z0-9\\.]*");


    public HttpFileServerHandler(String url) {
        this.url = url;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {

        //对HTTP请求消息的解码结果进行判断，如果解码失败，返回400
        if (!msg.getDecoderResult().isSuccess()) {
            sendError(ctx, BAD_REQUEST);
            return;
        }

        //对Http请求行中的方法进行判断，如果不是GET请求，则返回405
        if (msg.getMethod() != HttpMethod.GET) {
            sendError(ctx, METHOD_NOT_ALLOWED);
            return;
        }

        final String uri = msg.getUri();
        //对uri展开分析
        final String path = sanitizeUri(uri);
        if (path == null) {
            sendError(ctx, FORBIDDEN);
            return;
        }

        File file = new File(path);
        //如果文件不存在或者是系统隐藏文件，则返回404
        if (file.isHidden() || !file.canRead()) {
            sendError(ctx, NOT_FOUND);
            return;
        }

        //如果文件是目录,则方法目录的连接给客户端浏览器
        if (file.isDirectory()) {
            if (uri.startsWith("/")) {
                sendList(ctx, file);
            } else {
                sendRedirect(ctx, uri + '/');
            }
            return;
        }

        if (!file.isFile()) {
            sendError(ctx, FORBIDDEN);
            return;
        }

        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException e) {
            sendError(ctx, NOT_FOUND);
            return;
        }

        //获取文件长度
        long fileLength = randomAccessFile.length();
        HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, OK);
        setContentLength(response, fileLength);
        setContenTypeHeader(response, file);
        if (HttpHeaders.isKeepAlive(msg)) {
            //在应答消息头中设置connection为keep-alive
            response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }

        ctx.write(response);
        ChannelFuture sendFuture;
        //ChunkedFile对象直接将文件写入到发送缓冲区中
        sendFuture = ctx.write(new ChunkedFile(randomAccessFile, 0, fileLength, 8192), ctx.newProgressivePromise());
        sendFuture.addListener(new ChannelProgressiveFutureListener() {
            @Override
            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
                if (total < 0) {
                    System.err.println("Transfer progress : " + progress + "/" + total);
                }

            }

            @Override
            public void operationComplete(ChannelProgressiveFuture future) throws Exception {
                System.out.println("transfer complete");
            }
        });

        //如果使用chunked编码，最后需要发送一个编码结束的空消息体，并将之前发送缓冲区的内容刷新到socketchannel
        ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        if (!HttpHeaders.isKeepAlive(msg)) {
             //如果是非keep-alive的，则服务端主动关闭连接
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    private static void sendError(ChannelHandlerContext context, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status,
                Unpooled.copiedBuffer("Failure" + status, CharsetUtil.UTF_8));
        response.headers().set(CONTENT_TYPE, "text/explain; charset=utf-8");
        context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private static void setContenTypeHeader(HttpResponse httpResponse, File file) {
        MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
        httpResponse.headers().set(CONTENT_TYPE, mimetypesFileTypeMap.getContentType(file.getPath()));

    }

    private static void setContentLength(HttpResponse httpResponse, long fileLength) {

    }

    private static void sendRedirect(ChannelHandlerContext context, String newUrl) {
        FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, FOUND);
        fullHttpResponse.headers().set(LOCATION, newUrl);
        context.writeAndFlush(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
    }

    private String sanitizeUri(String uri) {
        try {
            //对uri进行解码
            uri = URLDecoder.decode(uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            try {
                uri = URLDecoder.decode(uri, "ISO-8859-1");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
        }
        //对uri进行合法性判断，如果uri与允许访问的uri一致或者是其子目录（文件），则校验通过，否则返回空。

        if (!uri.startsWith(url)) {
            return null;
        }

        if (!uri.startsWith("/")) {
            return null;
        }

        //将硬编码的文件路径分隔符替换成本地操作系统的文件路径分隔符。
        uri = uri.replace('/', File.separatorChar);

        //对新的uri进行二次合法性校验。
        if (uri.contains(File.separator + ".")
                || uri.contains("." + File.separator)
                || uri.startsWith(".")
                || uri.endsWith(".")
                || INSECURE_URI.matcher(uri).matches()) {
            return null;

        }

        //拼接  当前允许程序的文件目录+uri构造绝对路径返回
        return System.getProperty("uri.dir") + File.separator + uri;
    }

    //文件链接响应给客户端
    private static void sendList(ChannelHandlerContext context, File dir) {
        //创建成功的httpp响应消息
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, OK);

        //构建消息头
        response.headers().set(CONTENT_TYPE, "text/html;charset=UTF-8");

        //构造响应消息体
        StringBuilder builder = new StringBuilder();
        String dirPath = dir.getPath();

        builder.append("<!DOCTYPE html>");
        builder.append("<html><head><title>");
        builder.append(dirPath);
        builder.append("目录");
        builder.append("</title></head><body>\r\n");
        builder.append("<h3>");
        builder.append(dirPath).append(" 目录: ");
        builder.append("</h3>\r\n");
        builder.append("<ul>");
        builder.append("<li>链接:<a href=\"../\">..</a></li>\r\n");

        //用于展示根目录下的所有文件和文件夹，同时使用超链接来标识。
        for (File f : dir.listFiles()) {

            if (f.isHidden() || !f.canRead()) {
                continue;
            }

            String name = f.getName();
            if (!ALLOWED_FILE_NAME.matcher(name).matches()) {
                continue;
            }

            builder.append("<li>链接：<a herf=\"");
            builder.append(name);
            builder.append("\">");
            builder.append(name);
            builder.append("</a></li>\r\n");
        }
        builder.append("</ul></body></html>\r\n");

        //分配对应消息的缓冲对象
        ByteBuf byteBuf = Unpooled.copiedBuffer(builder, CharsetUtil.UTF_8);
        response.content().writeBytes(byteBuf);
        byteBuf.release();
        //将响应消息发送到缓冲区，并刷新到socketchannel中
        context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

    }

}
