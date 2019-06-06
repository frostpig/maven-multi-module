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
package com.jumei.arch.push.web.service.impl;

import com.jumei.arch.push.web.service.MessageService;
import com.jumei.arch.push.web.util.kafka.KafkaProducer;
import org.springframework.stereotype.Service;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/2/27.
 * Copyright (c) 2019,shuangshuangl@jumei.com All Rights Reserved.
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Override
    public void sendMsgToKafka(String msg) {
        KafkaProducer.send(msg);
    }

}