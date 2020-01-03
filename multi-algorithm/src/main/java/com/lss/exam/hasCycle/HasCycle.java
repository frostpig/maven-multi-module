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
package com.lss.exam.hasCycle;

import com.lss.exam.common.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * Function: 环形链表
 * 给定一个链表，判断链表中是否有环。
 *
 * 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。
 *图例：https://leetcode-cn.com/problems/linked-list-cycle/
 *  
 *
 * 示例 1：
 *
 * 输入：head = [3,2,0,-4], pos = 1
 * 输出：true
 * 解释：链表中有一个环，其尾部连接到第二个节点。
 *
 *
 * 示例 2：
 *
 * 输入：head = [1,2], pos = 0
 * 输出：true
 * 解释：链表中有一个环，其尾部连接到第一个节点。
 *
 *
 * 示例 3：
 *
 * 输入：head = [1], pos = -1
 * 输出：false
 * 解释：链表中没有环。
 *
 *
 *  
 *
 * 进阶：
 *
 * 你能用 O(1)（即，常量）内存解决此问题吗？
 *
 *
 * Created by shuangshuangl on 2019/12/30.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class HasCycle {

    /**
     * 哈希存储法
     * @param head
     * @return
     */
    public boolean hasCycle01(ListNode head) {
        if (head == null){
            return  false;
        }
        Set<ListNode> set = new HashSet<ListNode>();
        while (head != null){
            if (set.contains(head)){
                return true;
            }else {
                set.add(head);
            }
            head = head.next;
        }

        return false;
    }

    /**
     * 快慢跑法 双指针
     * @param head
     * @return
     */
    public boolean hasCycle02(ListNode head) {
        if (head == null){
            return  false;
        }

        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != fast){
            if (fast == null || fast.next == null){
                return false;
            }

            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }
}