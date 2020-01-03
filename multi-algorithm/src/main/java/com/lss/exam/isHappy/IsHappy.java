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
package com.lss.exam.isHappy;

import java.util.HashSet;
import java.util.Set;

/**
 * Function: 快乐数
 * <p>
 * 编写一个算法来判断一个数是不是“快乐数”。
 * <p>
 * 一个“快乐数”定义为：对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和，
 * 然后重复这个过程直到这个数变为 1，也可能是无限循环但始终变不到 1。如果可以变为 1，那么这个数就是快乐数。
 * <p>
 * 示例: 
 * <p>
 * 输入: 19
 * 输出: true
 * 解释:
 * 1^2 + 9^2 = 82
 * 8^2 + 2^2 = 68
 * 6^2 + 8^2 = 100
 * 1^2 + 0^2 + 0^2 = 1
 * <p>
 * 相关标签 哈希表
 * Created by shuangshuangl on 2019/12/30.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class IsHappy {
    public static void main(String[] args) {
        System.out.println(isHappy_slow_fast(19));
    }

    /**
     * 方法一：哈希表存储计算结果，出现相等判循环
     *
     * @param n
     * @return
     */
    public static boolean isHappy_hash(int n) {
        if (n == 0) {
            return false;
        }

        Set<Integer> set = new HashSet<Integer>();
        int result = 0;

        while (result != 1) {
            while (n != 0) {

                result += (int) Math.pow(n % 10, 2);
                n = n / 10;
            }
            if (result == 1) {
                return true;
            }

            n = result;
            //数字开始循环，则说明不是快乐数，退出，防止无线循环
            if (set.contains(result)) {
                return false;
            } else {
                set.add(result);
            }
            result = 0;
        }

        return true;
    }

    /**
     * 方法二：快慢指针判循环
     *
     * @param n
     * @return
     */
    public static boolean isHappy_slow_fast(int n) {
        if (n <= 0) {
            return false;
        }
        int fast = n ;
        int slow = n;
        do{
            fast = change(fast);
            fast = change(fast);
            slow = change(slow);
            if (fast == 1 || slow == 1){
                return true;
            }
        } while (fast != slow);
        return false;

    }

    public static int change(int n) {
        int result = 0;
        while (n != 0) {
            result += (int) Math.pow(n % 10, 2);
            n = n / 10;
        }
        return result;
    }


}