/*
 * Copyright (C) 2019 jumei, Inc.
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
package com.jumei.arch.push.web.controller;

import com.jumei.arch.push.web.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * Function: 控制器.
 * <p>
 * Created by shuangshuangl on 2019/2/27.
 * Copyright (c) 2019,shuangshuangl@jumei.com All Rights Reserved.
 */
@RestController
public class MessageController {
    private static final Logger logger = LoggerFactory
            .getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;
    private static final String APPID = "shuabao";

    /**
     * 获取消息中心的数据解析封装发送到kafka
     * 数据有消息中心主动推送到/msg接口，接收成功以后返回code=1
     * @param message
     * @return
     */
    @RequestMapping(value = "/msg")
    public Map<String, Object> index(@RequestParam String message) {
       try {
           logger.error("data/n");
           int index = message.indexOf(APPID);
           if (index != -1) {
               messageService.sendMsgToKafka(message);
           }

       }catch (Exception e){
           logger.error(e.getMessage(),e);
       }
        return getResponseData();
    }

    private Map<String, Object> getResponseData() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 1);
        return map;
    }


}