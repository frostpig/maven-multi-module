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
package com.lss.exam.common.impl;

import com.lss.exam.common.Node;
import com.lss.exam.common.interf.LinkInterface;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/12/30.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class SingleLinked implements LinkInterface {
    private Node head;
    private  int length;

    public SingleLinked() {
        head = null;
        length = 0;
    }

    //头节点插入法
    public void headInsert(Object data){
        Node node = new Node();
        node.data = data;
        node.next = head;
        head = node;
        length ++;
    }


    public Node get(int p) {
        return null;
    }

    public void insert(int p, Object data) {

    }

    public void delete(int p) {

    }

    public void clean() {

    }

    public int size() {
        return 0;
    }
}