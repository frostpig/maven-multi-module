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
package com.lss.exam.twoSum;

/**
 * Function:两数求和
 * /*
 *     给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，
 *     并返回他们的数组下标。你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 * [-3,4,3,90] 0
 *     示例:
 *         给定 nums = [2, 7, 11, 15], target = 9
 *         因为 nums[0] + nums[1] = 2 + 7 = 9
 *         所以返回 [0, 1]
 *
 *
 * Created by shuangshuangl on 2019/12/17.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class TwoSum {
    public static void main(String[] args) {
        int[] nums={3,3};
        int target = 6;
        int[] num = twoSum(nums,target);
        System.out.println(num[0] + num[1]);

    }


    /**
     * 暴力解决法
     *      使用两个循环
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum(int[] nums, int target) {
        int tmp[] = new int[2];
        for (int i = 0; i < nums.length; i++) {
            int second = target - nums[i];
            for (int j = 0; j < nums.length; j++) {
                if (nums[j] == second && j != i){
                    tmp[0] = i;
                    tmp[1] = j;
                    System.out.println(tmp[0] +" " +  tmp[1]);
                    return tmp;
                }

            }

        }
        return tmp;
    }
}