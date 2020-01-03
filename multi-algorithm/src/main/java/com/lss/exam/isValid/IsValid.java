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
package com.lss.exam.isValid;

import java.util.Stack;

/**
 * Function: 有效的括号
 * <p>
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 * <p>
 * 有效字符串需满足：
 * <p>
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 注意空字符串可被认为是有效字符串。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "()"
 * 输出: true
 * 示例 2:
 * <p>
 * 输入: "()[]{}"
 * 输出: true
 * 示例 3:
 * <p>
 * 输入: "(]"
 * 输出: false
 * 示例 4:
 * <p>
 * 输入: "([)]"
 * 输出: false
 * 示例 5:
 * <p>
 * 输入: "{[]}"
 * 输出: true
 * <p>
 * Created by shuangshuangl on 2019/12/25.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class IsValid {
    public static void main(String[] args) {
        String str = "([)]";
        System.out.println(isValid(str));
        String ascField = "";
        String[] asc = ascField.split(",");
        for (String name : asc){
            System.out.println(name);
        }
    }

    public static boolean isValid(String s) {
        if (s.startsWith("}") || s.startsWith("]") || s.startsWith(")")) {
            return false;
        }
        if (s.endsWith("{") || s.endsWith("[") || s.endsWith("(")) {
            return false;
        }

        Stack stack = new Stack();
        for (int i = 0; i < s.length(); i++) {
            String source = String.valueOf(s.charAt(i));
            if (stack.empty()) {
                stack.push(source);
            }else {
                String peek = String.valueOf(stack.peek());
                if (compare(peek,source)){
                    stack.pop();
                }else{
                    stack.push(source);
                }
            }
        }

        if (stack.empty()){
            return  true;
        }

        return false;

    }

    public static boolean compare(String start, String end) {
        if ((start.equals("{") && end.equals("}")) ||
                (start.equals("[") && end.equals("]")) ||
                (start.equals("(") && end.equals(")")) ||
                (start.equals("}") && end.equals("{")) ||
                (start.equals("]") && end.equals("[")) ||
                (start.equals(")") && end.equals("("))){
            return true;
        }else {
            return false;
        }

    }
}