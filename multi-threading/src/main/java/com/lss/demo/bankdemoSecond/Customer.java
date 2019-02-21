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
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/2/21.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class Customer implements Runnable{
    double balance; //账户余额
    public void run() {
        //存3次，每次存1000
        for (int i = 0; i < 3; i++) {
            deposit();
        }
    }

    public synchronized void deposit(){ //存钱
        balance += 1000;
        notify();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "余额：" + balance);
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}