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
package com.lss.echo.httpxml;

import com.lss.echo.httpxml.domain.Address;
import com.lss.echo.httpxml.domain.Customer;
import com.lss.echo.httpxml.domain.Shipping;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/7/29.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class Order {
    private long orderNumer;
    private Customer customer;
    private Shipping shipping;
    private Address address;
    private Float  total;

    public long getOrderNumer() {
        return orderNumer;
    }

    public void setOrderNumer(long orderNumer) {
        this.orderNumer = orderNumer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}