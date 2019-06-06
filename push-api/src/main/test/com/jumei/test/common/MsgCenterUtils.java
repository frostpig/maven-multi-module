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
package com.jumei.test.common;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/2/27.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class MsgCenterUtils {

        public static final String PROTOCOL_KEY = "protocol";
        public static final String PROTOCOL_VALUE = "json";
        public static final String CLASS_KEY = "class";
        public static final String CLASS_VALUE = "Broadcast";
        public static final String METHOD_KEY = "method";
        public static final String METHOD_VALUE = "Send";
        public static final String PARAMS = "params";
        public static final String USER = "user";
        public static final String SIGN = "sign";

        //消息类型
        public static final String KS3 = "ks3";
        public static final String AUTO_CDN = "cdn";
        public static final String MANUAL_CDN = "cdn";
        public static final String CDN_PREFETCH = "prefetch";

        // 基础信息
        public static String msgCenterAccessId =  "fileSys";
        public static String msgCenterSecretKey = "123456";
        public static String msgCenterVisitUrl =  "http://rpc.event.jumeicd.com/?protocol=json";

        // 允许发送的消息类型
        public static String msgCenterKS3ClassKey = "acscomet_pushid";
        public static String msgCenterCdnClassKey;
        public static String msgCenterCdnPrefetchKey;


}