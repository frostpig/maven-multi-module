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
package com.lss.demo.queue;

import java.util.HashMap;
import java.util.Map;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/6/4.
 * Copyright (c) 2019,shuangshuangl@jumei.com All Rights Reserved.
 */
public class InfluxOverviewDTO {
    private Map<String,String> tags = new HashMap<String, String>();
    private Map<String,Object> fields = new HashMap<String, Object>();
    private long time;

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}