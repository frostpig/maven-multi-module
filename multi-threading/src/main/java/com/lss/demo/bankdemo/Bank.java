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

/**
 * Function: 银行网点.
 * <p>
 * Created by shuangshuangl on 2019/2/20.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class Bank {
    private  volatile  static  Bank bank;
    private  int totalDeposit = 0;
    private Queue<Customer> queue;
    //单例
    public static  Bank getInstance(){
        if (bank == null){
            synchronized (Bank.class){
                bank = new Bank();
            }
        }
        return bank;
    }

    private Bank() {
    }

    public static Bank getBank() {
        return bank;
    }

    public static void setBank(Bank bank) {
        Bank.bank = bank;
    }

    public int getTotalDeposit() {
        return totalDeposit;
    }

    public void setTotalDeposit(int totalDeposit) {
        this.totalDeposit = totalDeposit;
    }

    public Queue<Customer> getQueue() {
        return queue;
    }

    public void setQueue(Queue<Customer> queue) {
        this.queue = queue;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "totalDeposit=" + totalDeposit +
                ", queue=" + queue +
                '}';
    }
}