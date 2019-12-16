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
package com.lss.exam;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Function:
 * <p>
 * Created by shuangshuangl on 2019/12/2.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class Solution {
    public static void main(String[] args) {
/*
        System.out.println(reverse_3_digit(120));
*/
        reverseInteger(-2345);
    }

    /**
     * 三位数反转
     *
     * @param number:A 3-digit number.
     * @return Reversed number.
     */
    public static int reverse_3_digit(int number) {
        int num1 = number / 100;
        int num2 = (number - 100 * num1) / 10;
        int num3 = number - 100 * num1 - 10 * num2;
        StringBuilder stringBuilder = new StringBuilder();

        if (num3 != 0) {

            stringBuilder.append(num3);
            stringBuilder.append(num2);
        } else if (num2 != 0) {
            stringBuilder.append(num2);
        }

        if (num1 != 0) {
            stringBuilder.append(num1);
        }

        Integer data = Integer.parseInt(stringBuilder.toString());

        return data;
    }


    /**
     * 整数反转
     * 假设我们的环境只能存储得下 32 位的有符号整数
     * 则其数值范围为 [−2^31,  2^31 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。
     *
     * @param number
     * @return
     */

    public static int reverseInteger(int number) {
        String symbol = "-";
        StringBuilder stringBuilder = new StringBuilder();
        String[] str;
        int num = 0, newNumber = 0;
        if (String.valueOf(number).contains("-")) {
            str = String.valueOf(number).split("-");
            num = str[1].length();
            newNumber = Integer.parseInt(str[1]);
            System.out.println("这是" + num + "位数");
            stringBuilder.append(symbol);
        } else {
            num = String.valueOf(number).length();
            newNumber = number;
        }

        int[] data = new int[num];
        for (int i = 0; i < num; i++) {
            data[i] = newNumber % 10;
            newNumber /= 10;
            stringBuilder.append(data[i]);
        }

        Integer result = Integer.parseInt(stringBuilder.toString());
        if (-Math.pow(2, 23) < result && result < Math.pow(2, 23)) {
            return result;
        } else {
            return 0;
        }
    }
}