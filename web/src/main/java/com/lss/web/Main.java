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
package com.lss.web;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/10/29.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class Main {
    public static void main(String[] args) {
        HashMap<String,String> hashMap = new HashMap<String, String>();
        Hashtable<String,String> hashtable = new Hashtable<String, String>();
        ConcurrentHashMap<String,String> concurrentHashMap = new ConcurrentHashMap<String, String>();
        Map<String,String> map = new HashMap<String, String>();
        StringBuilder stringBuilder = new StringBuilder();
        int integer = Integer.parseInt(stringBuilder.toString());
        System.out.println();

    }


}