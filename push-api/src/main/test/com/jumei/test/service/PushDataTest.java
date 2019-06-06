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
package com.jumei.test.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jumei.test.util.MD5;
import com.jumei.test.util.OkhttpUtils;
import com.jumei.test.common.MsgCenterUtils;
import okhttp3.FormBody;
import okhttp3.Response;

import java.util.*;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/2/27.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class PushDataTest {
    private static OkhttpUtils okhttpUtils =OkhttpUtils.getInstance();


    public static void main(String[] args) {

       String data = "{\"time\":1551126026,\"type\":\"event_pushid\",\"info\":\"{\\\"AppID\\\":\\\"shuabao\\\",\\\"AppVersion\\\":\\\"1.310\\\",\\\"Brand\\\":\\\"OPPO\\\",\\\"Channel\\\":\\\"GTPush\\\",\\\"DevID\\\":\\\"4568f7b9a8d1a44e\\\",\\\"DevType\\\":\\\"phone\\\",\\\"Model\\\":\\\"OPPO R7s\\\",\\\"OSVersion\\\":\\\"4.4.4\\\",\\\"Platform\\\":\\\"android\\\",\\\"PushID\\\":\\\"698931b952cc80f5bdd32951001327c1\\\",\\\"Source\\\":\\\"androiddefault\\\",\\\"Type\\\":\\\"cold_boot\\\",\\\"UID\\\":\\\"sb_416139248\\\"}\"}";
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                push2MsgCenter(data,"ks3");
            }
        },1000,60000);
    }

    protected static void push2MsgCenter(String data, String msgType) {

        long start = System.currentTimeMillis();
        List<Object> content = new ArrayList<>(2);

        if (MsgCenterUtils.KS3.equals(msgType)) {
            content.add(MsgCenterUtils.msgCenterKS3ClassKey);
        } else if (MsgCenterUtils.AUTO_CDN.equals(msgType) || MsgCenterUtils.MANUAL_CDN.equals(msgType)) {
            content.add(MsgCenterUtils.msgCenterCdnClassKey);
        } else if (MsgCenterUtils.CDN_PREFETCH.equals(msgType)) {
            content.add(MsgCenterUtils.msgCenterCdnPrefetchKey);
        } else {
            System.out.println("not support msg type: " + msgType);
            return;
        }
        content.add(data);

        Map<String, Object> params = new HashMap<>();
        params.put(MsgCenterUtils.PROTOCOL_KEY, MsgCenterUtils.PROTOCOL_VALUE);
        params.put(MsgCenterUtils.CLASS_KEY, MsgCenterUtils.CLASS_VALUE);
        params.put(MsgCenterUtils.METHOD_KEY, MsgCenterUtils.METHOD_VALUE);
        params.put(MsgCenterUtils.PARAMS, content);
        params.put(MsgCenterUtils.USER, MsgCenterUtils.msgCenterAccessId);
        params.put(MsgCenterUtils.SIGN, MD5.encode(MsgCenterUtils.msgCenterAccessId + MsgCenterUtils.msgCenterSecretKey
                + MsgCenterUtils.CLASS_VALUE + MsgCenterUtils.METHOD_VALUE));
        try {
            FormBody.Builder builder = new FormBody.Builder();
            builder.add("data", JSON.toJSONString(params));
            FormBody body = builder.build();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < body.size(); i++) {
                sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
            }
            System.out.println("body"+ sb.toString());
            Response res = okhttpUtils.post(MsgCenterUtils.msgCenterVisitUrl, body);
            JSONObject object = JSON.parseObject(new String(res.body().bytes(), "UTF-8"));
            System.out.println(object.getBooleanValue("return"));
            if (object.getBooleanValue("return") == false) {
                System.out.println(
                        "push data to msg center error, data:" + data + "; error: " + object.getString("Exception"));
            } else {
                System.out.println("push data to msg center success, msgType: " + msgType + ", value: " + data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("push data to msg center error, data:" + data + "; error: " + e);
        }
        System.out.println(("push data to msg center elapsed time:" + (System.currentTimeMillis() - start)));
    }


}