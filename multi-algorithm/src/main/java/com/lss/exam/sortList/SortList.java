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
package com.lss.exam.sortList;

import com.lss.exam.common.ListNode;

/**
 * Function: 排序链表
 * 在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序。
 *
 * 示例 1:
 *
 * 输入: 4->2->1->3
 * 输出: 1->2->3->4
 * 示例 2:
 *
 * 输入: -1->5->3->4->0
 * 输出: -1->0->3->4->5
 *
 * <p>
 * Created by shuangshuangl on 2019/12/31.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class SortList {
    public static void main(String[] args) {
        String date = "2019-10-31T20:58:09.831Z";

    }
    public ListNode sortList(ListNode head) {
        if (head == null){
            return  head;
        }
        ListNode tmp = head;
        int min = head.val;
        while (tmp != null){
            tmp = tmp.next;
            int num = tmp.val;
            min = num;
            if (num < min){
                min = num;
            }
            tmp.val = min;
            head = tmp;
        }
return head;
    }
}