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
package com.lss.echo.nio.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/7/24.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class MultipiexerTimeServer implements Runnable{
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private volatile boolean stop;


    /**
     * 初始化多路复用器，绑定监听端口。
     * 如果资源初始化失败，则退出。例如端口占用
     * @param port
     */
    public MultipiexerTimeServer(int port) {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false); //serverSocketChannel设置为异步非阻塞。
            serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); //将channel注册到selector,并监听OP_ACCEPT操作位
            System.out.println("the time server is start in port:" +port);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void stop (){
        this.stop = true;
    }
    public void run() {
        while (!stop){
            try {
                selector.select(1000); //selector 每隔1s被唤醒一次
                Set<SelectionKey> selectionKeySet = new HashSet<SelectionKey>(selector.selectedKeys());
                Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()){
                    key = iterator.next();
                    iterator.remove();
                    handleInput(key);
                    if (key != null){
                        key.cancel();
                        if (key.channel() != null){
                            key.channel().close();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //多路复用器关闭以后，所有注册在上面的channel和pipe等资源会自动去注册，所以不需要重复释放资源。
        if (selector != null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public  void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()){
            //处理新接入的请求
            if (key.isAcceptable()){
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);
                sc.register(selector,SelectionKey.OP_READ);

            }
            //上述步骤结束，就相当于完成TCP三次握手。


            if (key.isReadable()){
                //读取数据
                SocketChannel socketChannel = (SocketChannel) key.channel();
                ByteBuffer buffer =  ByteBuffer.allocate(1024);
                //由于前面已经将socketChannel设置为了异步非阻塞，因此他的read方法也是异步非阻塞的，
                //返回值 >0 :有数据，对字节进行编码处理
                //返回值 == 0 :没有数据，属于正常场景，忽略
                //返回值 == -1：链路已经关闭，需要关闭socketchannel,是否资源。
                int readBytes = socketChannel.read(buffer);
                if (readBytes > 0){
                    //flip是将缓冲区当前的limit设置为position,position设置为0，用于后续对缓冲区的读取操作。
                    buffer.flip();
                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);
                    String body = new String(bytes,"UTF-8");
                    System.out.println("the time server receive order:"+ body);
                    String currentime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString():"BAD ORDER";
                    dowrite(socketChannel,currentime);
                }else if (readBytes < 0){
                    //对端链路关闭
                    key.cancel();
                    socketChannel.close();
                }else
                    ;
            }
        }

    }

    private void dowrite(SocketChannel channel,String response) throws IOException {
        if (response != null &&response.trim().length()>0){
            byte[] bytes = response.getBytes();
            ByteBuffer writebuffer = ByteBuffer.allocate(bytes.length);
            writebuffer.put(bytes);
            writebuffer.flip();
            channel.write(writebuffer);

        }

    }
}