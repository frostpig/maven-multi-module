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
package com.lss.design.strategy;

import com.lss.design.strategy.impl.OperationAdd;
import com.lss.design.strategy.impl.OperationMultiply;
import com.lss.design.strategy.impl.OperationSubstract;

/**
 * Function: 策略模式
 *  优点：
 *  1、算法可以自由切换。
 *  2、避免使用多重条件判断。
 *  3、扩展性良好。
 *
 *  缺点：
 *   1、策略类会增多。
 *   2、所有策略类都需要对外暴露。
 *
 *  注意：如果一个系统的策略多于四个，就需要考虑使用混合模式，解决策略类膨胀的问题。
 * <p>
 * Created by shuangshuangl on 2019/5/30.
 * Copyright (c) 2019,shuangshuangl@jumei.com All Rights Reserved.
 */
public class StrategyPatternDemo {
    public static void main(String[] args) {
        Context context = new Context(new OperationAdd());
        System.out.println("10 + 5 = " + context.executeStrategy(10, 5));

        context = new Context(new OperationSubstract());
        System.out.println("10 - 5 = " + context.executeStrategy(10, 5));

        context = new Context(new OperationMultiply());
        System.out.println("10 * 5 = " + context.executeStrategy(10, 5));

    }
}