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

/**
 * Function: 银行柜员
 * <p>
 * Created by shuangshuangl on 2019/2/20.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class Counter implements Runnable{
    Bank bank = Bank.getInstance();
    private String name;
    private String state;
    private int count = 0;

    public Counter() {
        this.state = "available";
    }

    public Counter(String name) {
        this.name = name;
        this.state = "available";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void deposit(Customer customer){
        System.out.println(System.currentTimeMillis() +":【" + Thread.currentThread().getName() + "】开始给【" + customer.getName() + "】存钱"+ customer.getSavings());
        this.state = "busy";
        try {
            Thread.sleep((long)(Math.random()*100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (Counter.class){
            bank.setTotalDeposit(bank.getTotalDeposit() + customer.getSavings());
        }

        System.out.println( System.currentTimeMillis() + "【" + Thread.currentThread().getName() + "】给" + customer.getName() + "】存钱完毕");
        this.state = "available";


    }


    public void run() {
        exec();
    }

    private void exec(){
        while (Bank.getInstance().getQueue() != null && !Bank.getInstance().getQueue().isEmpty()){
            if ("available".equals(this.getState())){
                Customer c = Bank.getInstance().getQueue().poll();
                this.deposit(c);
            }

        }
        this.setState("offWork");
        System.out.println(Thread.currentThread().getName()+"下班....");
    }
}