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

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/7/8.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class HttpUtils {

    public static final int TIMEOUT = 600;//这个要弄长点


    public static final String CONTENT_TYPE_NAME = "Content-Type";

    public static final String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";

    public static final String CONTENT_TYPE_XML = "text/xml;charset=UTF-8";

    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded;charset=UTF-8";

    public static final String ACCEPT_NAME = "Accept";
    public static final String ACCEPT = "application/json;charset=UTF-8";

    /**
     * get请求
     *
     * @return
     */
    public static String doGet(String url) {
        try {
            HttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());

                return strResult;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * post请求(用于key-value格式的参数)
     *
     * @param url
     * @return
     */
    public static String doPost(String url, Map paramMap) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse httpResponse = null;
        String result = "";
        try {
            // 创建httpClient实例
            httpClient = HttpClients.createDefault();
            // 创建httpPost远程连接实例
            HttpPost httpPost = new HttpPost(url);
            // 配置请求参数实例
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 设置连接主机服务超时时间
                    .setConnectionRequestTimeout(35000)// 设置连接请求超时时间
                    .setSocketTimeout(60000)// 设置读取数据连接超时时间
                    .build();
            // 为httpPost实例设置配置
            httpPost.setConfig(requestConfig);
            // 设置请求头
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            // 封装post请求参数
            if (null != paramMap && paramMap.size() > 0) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                // 通过map集成entrySet方法获取entity
                Set<Map.Entry<String, Object>> entrySet = paramMap.entrySet();
                // 循环遍历，获取迭代器
                Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> mapEntry = iterator.next();
                    nvps.add(new BasicNameValuePair(mapEntry.getKey(), mapEntry.getValue().toString()));
                }

                // 为httpPost设置封装好的请求参数
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

            }

            // httpClient对象执行post请求,并返回响应参数对象
            httpResponse = httpClient.execute(httpPost);
            // 从响应对象中获取响应内容
            HttpEntity entity = httpResponse.getEntity();
            System.out.println("状态码为 " + httpResponse.getStatusLine().getStatusCode());
            result = EntityUtils.toString(entity);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("状态码为 " + httpResponse.getStatusLine().getStatusCode());
        } finally {
            // 关闭资源
            if (null != httpResponse) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * post请求（用于请求json格式的参数）
     *
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, String params) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);// 创建httpPost
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(params, charSet);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;

        try {

            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String jsonString = EntityUtils.toString(responseEntity);
                return jsonString;
            } else {
                System.out.println("请求返回:" + state + "(" + url + ")");
            }
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        String buff = "OWL监控报警：业务系统 连接池,慢查询数量为142条,高于60条/分钟";
        String pattern_error = "(为)([\\d,]*)(条)";
        Pattern r = Pattern.compile(pattern_error);
        Matcher m = r.matcher(buff);
        Integer number = null;
        if (m.find( )) {
         /*   Integer  number1 = Integer.parseInt(m.group(0));
            Integer number2 = Integer.parseInt(m.group(1));
            Integer number3 = Integer.parseInt(m.group(2));*/
            System.out.println("number:" + m.group(0));
            System.out.println("number:" + m.group(1));
            System.out.println("number:" + m.group(2));
            System.out.println("number:" + m.group(3));
             number = Integer.valueOf(m.group(2).replace(",",""));

            System.out.println("错误数量为" + number);

        } else {
            System.out.println("NO MATCH");
        }

       String pro = "shuabo-web";
        char[] c=pro.toCharArray();
        StringBuilder data = new StringBuilder();
        for(int i=0;i<c.length;i++){
            data.append(c[i]+",");
        }
        System.out.println(data);
        long before = sdf.parse("22:00:00").getTime();
        System.out.println(before);
        long after = sdf.parse("08:00:00").getTime();
        System.out.println(after);
        System.out.println(sdf.parse("24:00:00").getTime() );
        System.out.println(sdf.parse("00:00:00").getTime() );
        long currentime = sdf.parse("05:35:00").getTime();
        System.out.println(currentime);

        if ((currentime < before && currentime > sdf.parse("00:00:00").getTime()) ||
                (currentime > sdf.parse("00:00:00").getTime() && currentime < after)) {
            System.out.println("0sdf");
        }

    }

}