/*
 * Copyright (C) 2018 jumei; Inc.
 *
 * Licensed under the Apache License; Version 2.0 (the License); you may
 * not use this file ecept in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http//www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing; software
 * distributed under the License is distributed on an AS IS BASIS; WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND; either epress or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package flink.domain;

/**
 * Function Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/7/18.
 * Copyright (c) 2018;shuangshuangl@jumei.com All Rights Reserved.
 */
public class Message {
    public String uid;  //用户id
    public String brand; //手机品牌
    public String module;  //手机型号
    public String os;
    public String version;
    public String client_ip;
    public String video_url;
    public String server_ip;
    public String http_status;
    public long dns_time;
    public long establish_time;
    public long first_play;
    public long caton_time;
    public long caton_counter;
    public long video_duration;
    public long play_duration;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getServer_ip() {
        return server_ip;
    }

    public void setServer_ip(String server_ip) {
        this.server_ip = server_ip;
    }

    public String getHttp_status() {
        return http_status;
    }

    public void setHttp_status(String http_status) {
        this.http_status = http_status;
    }

    public long getDns_time() {
        return dns_time;
    }

    public void setDns_time(long dns_time) {
        this.dns_time = dns_time;
    }

    public long getEstablish_time() {
        return establish_time;
    }

    public void setEstablish_time(long establish_time) {
        this.establish_time = establish_time;
    }

    public long getFirst_play() {
        return first_play;
    }

    public void setFirst_play(long first_play) {
        this.first_play = first_play;
    }

    public long getCaton_time() {
        return caton_time;
    }

    public void setCaton_time(long caton_time) {
        this.caton_time = caton_time;
    }

    public long getCaton_counter() {
        return caton_counter;
    }

    public void setCaton_counter(long caton_counter) {
        this.caton_counter = caton_counter;
    }

    public long getVideo_duration() {
        return video_duration;
    }

    public void setVideo_duration(long video_duration) {
        this.video_duration = video_duration;
    }

    public long getPlay_duration() {
        return play_duration;
    }

    public void setPlay_duration(long play_duration) {
        this.play_duration = play_duration;
    }
}