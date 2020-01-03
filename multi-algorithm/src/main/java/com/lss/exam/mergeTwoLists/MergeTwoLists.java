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
package com.lss.exam.mergeTwoLists;

import java.util.ArrayList;
import java.util.Collections;

import java.util.List;

/**
 * Function:  合并两个有序链表
 * <p>
 * 将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 
 * <p>
 * 示例：
 * <p>
 * 输入：1->2->4, 1->3->4
 * 输出：1->1->2->3->4->4
 * <p>
 * <p>
 * 按照题目描述和样例，是将两个链表合并成新的有序链表，但是实际在平台运行的时候，给出的预期结果和题目要求不一致，我这里是按照题目要求做的。
 * 1、判断两个链表是否都为空，是就返回空链表，这里由于节点没有显式的无参构造方法，所以直接返回了l1和l2中的一个
 * 2、将两个链表的内容全部放到一个list集合
 * 3、利用collections的排序功能，将list降序(此方法排序可能时间复杂度和空间利用率都比较大)
 * 4、利用链表的头插法，将list的内容重新插入到新的链表，然后输出的结果就是升序
 * <p>
 * 平台测试：耗时6ms,占用内存36MB
 * <p>
 * Created by shuangshuangl on 2019/12/30.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class MergeTwoLists {
    public static void main(String[] args) {
        ListNode param_1 = new ListNode(1);
        param_1.next = new ListNode(2);
        param_1.next.next = new ListNode(4);

        ListNode param_2 = new ListNode(1);
        param_2.next = new ListNode(3);
        param_2.next.next = new ListNode(4);
        ListNode ret = new MergeTwoLists().mergeTwoLists(param_1, param_2);

    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) {
            return l1;
        }

        List<Integer> list = new ArrayList();
        ListNode tmp;
        if (l1 != null) {
            list.add(l1.val);
            tmp = l1.next;
            while (tmp != null) {
                list.add(tmp.val);
                tmp = tmp.next;
            }
        }

        if (l2 != null) {
            list.add(l2.val);
            tmp = l2.next;
            while (tmp != null) {
                list.add(tmp.val);
                tmp = tmp.next;
            }
        }

        Collections.sort(list, Collections.<Integer>reverseOrder());

        ListNode listNode = null;

        for (int i = 0; i < list.size(); i++) {
            listNode = headInsert(list.get(i), listNode);
        }

        return listNode;
    }


    //头节点插入法
    public static ListNode headInsert(int data, ListNode head) {
        ListNode node = new ListNode(data);
        node.next = head;
        head = node;
        return head;
    }

    //节点
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
        }

    }
}