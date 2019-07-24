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
package com.lss.echo.nio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/7/24.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class TimeClientHandle implements Runnable {
    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean stop;

    public TimeClientHandle(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void run() {
        try {
            doconnect();
            while (!stop){
                selector.select(1000);
                Set<SelectionKey> selectionKeys = new HashSet<SelectionKey>(selector.keys());
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key = null;
                while(iterator.hasNext()){
                    key = iterator.next();
                    iterator.remove();
                    handInput(key);
                    if (key !=null ){
                        key.cancel();

                        if (key.channel() != null){
                            key.channel().close();
                        }
                    }
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        if (selector != null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            //如果是连接状态，说明服务器已经返回ack应答消息
            if (key.isConnectable()) {
                if (socketChannel.finishConnect()) {
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    dowrite(socketChannel);

                } else {
                    System.exit(1);
                    System.out.println("连接失败，退出");
                }
            }

            if (key.isReadable()) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int readBytes = socketChannel.read(byteBuffer);
                if (readBytes > 0) {
                    byteBuffer.flip();
                    byte[] bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("Now is :" + body);
                    this.stop = true;
                } else if (readBytes < 0) {
                    key.cancel();
                    socketChannel.close();
                } else {
                    ;
                }
            }
        }

    }

    private void doconnect() {
        try {

            //如果连接成功，发送请求消息，读应答。
            if (socketChannel.connect(new InetSocketAddress(host, port))) {
                socketChannel.register(selector, SelectionKey.OP_READ);
                dowrite(socketChannel);

            }else { //如果没有连接成功，则说明服务没有返回Tcp握手应答，但这并不代表连接失败，我们需要将channel注册到selector,
                //当服务器返回syn-ack消息后，selector就能轮询到这个channel处于就绪状态。
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dowrite(SocketChannel socketChannel) throws IOException {
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(req.length);
        byteBuffer.put(req);
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        if (!byteBuffer.hasRemaining()) {
            System.out.println("Send  order 2 server succeed");
        }
    }

}