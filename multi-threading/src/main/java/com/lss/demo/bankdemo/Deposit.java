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
package com.lss.demo.bankdemo;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Function: 存款主线程
 * <p>
 * Created by shuangshuangl on 2019/2/20.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class Deposit {

    public static void main(String[] args) {
       /* int count = 0;
        for (int i = 0; i < 10; i++) {
            count += i;
            if (i == 9){
                System.out.println(count);
            }
        }*/
        long start = System.currentTimeMillis();
        Queue<Customer> q = new ConcurrentLinkedQueue<Customer>();
        for (int i = 0; i < 10; i++) {
            q.add(new Customer("客户" + i,i));
        }

        Bank.getInstance().setQueue(q);
        Counter[] counters = new Counter[10];
        Thread[] threads = new Thread[10];
        for (int i = 0; i < counters.length; i++) {
            counters[i] = new Counter("counter" + i);
            threads[i] = new Thread(counters[i]);
            threads[i].setName("Thread" + i);
            threads[i].start();
        }

        while (true){
            if (q.isEmpty()){
                boolean flag = true;
                for (int i = 0; i < counters.length; i++) {
                    if (!"offWork".equals(counters[i].getState())){
                        flag = false;
                    }
                }

                if (flag){
                    break;
                }
            }
        }

        System.out.println("所有队伍已排完，并且柜员处理完所有的客户存款，总共存钱：" + Bank.getInstance().getTotalDeposit() );
        System.out.println("总用时" + (System.currentTimeMillis() - start));
    }
}