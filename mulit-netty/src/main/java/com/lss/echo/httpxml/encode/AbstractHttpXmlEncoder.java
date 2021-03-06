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
package com.lss.echo.httpxml.encode;

import com.sun.xml.internal.ws.api.BindingIDFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import javafx.beans.binding.IntegerBinding;

import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/7/29.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class AbstractHttpXmlEncoder<H>  {
    private IntegerBinding factory = null;
    private StringWriter stringWriter  = null;
    final static String CHARSET_NAME = "UTF-8";
    final static Charset UTF_8 = Charset.forName(CHARSET_NAME);

}