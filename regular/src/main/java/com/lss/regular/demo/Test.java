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
package com.lss.regular.demo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/3/15.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class Test {
    private static String regular = "(\\s*(\\w[a-zA-Z_0-9\\-]+)\\s*=\\s*)?\"(.*)\"\\s*";
    private static String str = "1=\"p9.api.jmstatic.com\"";
    public static void main(String[] args) {
        Matcher matcher = Pattern.compile(regular).matcher(str);
        matcher.reset();
        while (matcher.find()){
            String line = matcher.group().trim();
            System.out.println(line);
        }

    }
}