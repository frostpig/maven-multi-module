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
package com.lss.exam.palindrome;

/**
 * Function: 回文
 * <p>
 * Created by shuangshuangl on 2019/12/16.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 *

 */
public class palindrome {
    public static void main(String[] args) {
        int num=2147483647;
        System.out.println(isPalindrome(num));
    }

    /** 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
     * 思路：变量high用来存储最高位，变量low用来存储最低位
     *       比较high和low的大小，每次相等就是回文数，出现一次不相等就不是回文数
     *
     *  23458 % 10 = 8
     *  23458 % 10000  =
     * @param x
     * @return
     */
    public static  boolean isPalindrome(int x) {
        if(x < 0)
            return false;
        int cur = 0;
        int newNum = x;


        while(newNum != 0) {
            cur = cur * 10 + newNum % 10;  //前一个数 * 10 + 当前余数
            newNum /= 10; // 新数
        }
        return  true;
    }
}