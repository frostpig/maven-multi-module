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
package com.lss.echo.protocol.encode;

import com.lss.echo.protocol.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;

import java.util.List;
import java.util.Map;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/7/31.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class NettyMessageEncoder extends MessageToMessageEncoder<NettyMessage> {
     MarshallingEncoder  marshallingEncoder;

    public NettyMessageEncoder(MarshallingEncoder marshallingEncoder) {
        this.marshallingEncoder = marshallingEncoder;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, List<Object> out) throws Exception {
        if (msg ==null || msg.getHeader() == null){
            throw new Exception("the encode msg is null");
        }
        ByteBuf sendBuf = Unpooled.buffer();
        sendBuf.writeInt(msg.getHeader().getCrcCode());
        sendBuf.writeInt(msg.getHeader().getLength());
        sendBuf.writeInt(msg.getHeader().getAttachment().size());
        sendBuf.writeLong(msg.getHeader().getSessionId());
        sendBuf.writeByte(msg.getHeader().getType());
        sendBuf.writeByte(msg.getHeader().getPriority());

        String key = null;
        byte[] keyArray = null;
        Object value = null;
        for (Map.Entry<String,Object> param:msg.getHeader().getAttachment().entrySet()){
            key = param.getKey();
            keyArray = key.getBytes();
            sendBuf.writeInt(keyArray.length);
            sendBuf.writeBytes(keyArray);
            value = param.getValue();
/*
            marshallingEncoder.encode(ctx,value,sendBuf);
*/

        }
        keyArray =null;
        key = null;
        value= null;
        if (msg.getBody() != null){
/*
            marshallingEncoder.encode(ctx,msg.getBody(),sendBuf);
*/
        }else {
            sendBuf.writeInt(0);
            sendBuf.setInt(4,sendBuf.readableBytes());
        }
    }
}