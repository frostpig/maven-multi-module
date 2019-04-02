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
package spi.jdk.demo.main;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import spi.jdk.demo.DubboService;

import java.util.Iterator;
import java.util.ServiceLoader;


/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/4/2.
 */
public class ServiceMain {
    public static void main(String[] args) {
  /*     DubboService dubboService = ExtensionLoader.getExtensionLoader(DubboService.class).getAdaptiveExtension();*/
        //获取DubbService接口的实现类，和META-INF/services/spi.jdk.demo.DubboServie文件的配置有关，配置了几个就会获取到几个
        ServiceLoader<DubboService> spiloader = ServiceLoader.load(DubboService.class);
        Iterator<DubboService> iteratorspi = spiloader.iterator();
        while (iteratorspi.hasNext()){
            DubboService dubboService = iteratorspi.next();
            dubboService.sayHello();
        }
    }
}