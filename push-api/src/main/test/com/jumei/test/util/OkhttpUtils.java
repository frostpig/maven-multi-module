package com.jumei.test.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 
 * Function:okhttp3 工具包<br>
 *
 * @Date :2016年9月28日<br>
 * @Author :timothy.yue12@gmail.com<br>
 * @Copyright Copyright (c) 2016, JuMei All Rights Reserved.<br>
 */
public class OkhttpUtils {

    public static final String ContentTypeHeader = "Content-Type";
    public static final String DefaultMime = "application/octet-stream";
    public static final String JsonMime = "application/json";
    public static final String FormMime = "application/x-www-form-urlencoded";
    private final OkHttpClient httpClient;

    private static class OkhttpUtilsHolder {
        private static OkhttpUtils INSTANCE = new OkhttpUtils();
    }

    public static OkhttpUtils getInstance() {
        return OkhttpUtilsHolder.INSTANCE;
    }

    private OkhttpUtils() {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(64);
        dispatcher.setMaxRequestsPerHost(16);
        ConnectionPool connectionPool = new ConnectionPool(32, 5, TimeUnit.MINUTES);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.dispatcher(dispatcher);
        builder.connectionPool(connectionPool);
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(0, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        httpClient = builder.build();
    }

    public okhttp3.Response post(String url, RequestBody body) throws JumeiHttpException {
        Request.Builder requestBuilder = new Request.Builder().url(url).post(body);
        return send(requestBuilder);
    }

    public okhttp3.Response send(final Request.Builder requestBuilder) throws JumeiHttpException {

        okhttp3.Response res = null;
        try {
            res = httpClient.newCall(requestBuilder.build()).execute();
        } catch (IOException e) {
            e.printStackTrace();
            throw new JumeiHttpException(e);
        }
        return res;
    }

    public Response get(String url) throws JumeiHttpException {
        Request.Builder requestBuilder = new Request.Builder().get().url(url);
        return send(requestBuilder);
    }

    public static void main(String[] args) throws Exception {
        OkhttpUtils okhttpUtils = OkhttpUtils.getInstance();
        Response response = okhttpUtils
                .get("http://obf1sunu9.com0.z0.glb.qiniucdn.com/dev_test/jingtian.jpg");
        byte[] bytes = response.body().bytes();
        FileOutputStream fos = new FileOutputStream(new File("E://xyz1.jpg"));
        fos.write(bytes);
        fos.flush();
        fos.close();
    }
}
