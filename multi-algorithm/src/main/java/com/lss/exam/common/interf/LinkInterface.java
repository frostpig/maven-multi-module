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
package com.lss.exam.common.interf;


import com.lss.exam.common.Node;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/12/30.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public interface LinkInterface {
    public Node get(int p);

    public void insert(int p, Object data);

    public  void delete(int p);

    public  void clean();

    public int size();
}