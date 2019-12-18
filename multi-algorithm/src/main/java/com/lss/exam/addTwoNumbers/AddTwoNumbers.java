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
package com.lss.exam.addTwoNumbers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TooManyListenersException;

/**
 * Function: 两数相加
 * <p>
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，
 * 并且它们的每个节点只能存储 一位 数字。
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * <p>
 * 示例：
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 * Created by shuangshuangl on 2019/12/18.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class AddTwoNumbers {
    public static void main(String[] args) {
        ListNode listNode01 = new ListNode(2);
        listNode01.next = new ListNode(4);
        listNode01.next.next = new ListNode(3);

        ListNode listNode02 = new ListNode(5);
        listNode02.next = new ListNode(6);
        listNode02.next.next = new ListNode(4);
        addTwoNumbers(listNode01,listNode02);
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {


        int length1 = 0, length2 = 0;

        ListNode tmp = l1;
        while (tmp != null) {
            length1++;
            tmp = tmp.next;
        }

        tmp = l2;
        while (tmp != null) {
            length2++;
            tmp = tmp.next;
        }

        int[] num1 = new int[length1];
        int[] num2 = new int[length2];

        num1[length1-1] = l1.val;
        num2[length2-1] = l2.val;

        tmp = l1;
        while (tmp != null && tmp.next != null) {
            int index = --length1;
            tmp = tmp.next;
            num1[index - 1] = tmp.val;

        }
        tmp = l2;
        while (tmp != null && tmp.next != null) {
            int index = --length2;
            tmp = tmp.next;
            num2[index - 1] = tmp.val;


        }

        StringBuilder s1 = new StringBuilder();
        for (int i = 0; i < num1.length; i++) {
            s1.append(num1[i]);
        }
        StringBuilder s2 = new StringBuilder();
        for (int i = 0; i < num2.length; i++) {
            s2.append(num2[i]);
        }

        int sum1 = Integer.valueOf(s1.toString())+ Integer.valueOf(s2.toString());
        boolean flag = true;


        ListNode result = null;
        while (flag){
            tmp = result;
            if (sum1 /10 != 0 && result == null  ) {
                result = new ListNode(sum1 % 10);
            }else if (result.next == null){
                result = result.next;
                result.next = new ListNode(sum1 % 10);
            }else {
                flag = false;
            }
            sum1 = sum1 / 10;

        }


        return result;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}