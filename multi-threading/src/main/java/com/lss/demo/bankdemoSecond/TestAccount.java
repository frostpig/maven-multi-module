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
package com.lss.demo.bankdemoSecond;

/**
 * Function: 有两个储户向银行中同一个账户存款，一次1000，各存3次，
 *           使用notify()和wait()线程通信实现交替存款.
 *
 *           deposit()方法如果不使用synchronized ，则报错java.lang.IllegalMonitorStateException
 *          通过以下方法之一，线程可以成为此对象监视器的所有者：
 *          1、通过执行此对象的同步（synchronized）实例方法
 *          2、通过执行在此对象上进行同步的synchronized 语句的正文
 *          3、对于class类型的对象，可以通过执行该类的同步静态方法。
 *
 *           总结：线程操作的wait(),notify(),notifyAll()方法只能在同步控制方法或控制块儿内调用。
 * <p>
 * Created by shuangshuangl on 2019/2/21.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class TestAccount {
    public static void main(String[] args) {
        Customer customer = new Customer();
        Thread thread1 = new Thread(customer);
        Thread thread2 = new Thread(customer);
        thread1.setName("a");
        thread2.setName("b");
        thread1.start();
        thread2.start();
    }
}