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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/7/5.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class Main {
    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static void readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;


        try {
            FileWriter fw = new FileWriter(new File("D:/tmp/uri_6.2.txt"));
            //写入中文字符时会出现乱码
            BufferedWriter bw = new BufferedWriter(fw);

            FileWriter fw1 = new FileWriter(new File("D:/tmp/uri_size_lower8.txt"));
            //写入中文字符时会出现乱码
            BufferedWriter bw1 = new BufferedWriter(fw1);

            FileWriter fw2 = new FileWriter(new File("D:/tmp/uri_size_more8.txt"));
            //写入中文字符时会出现乱码
            BufferedWriter bw2 = new BufferedWriter(fw2);


            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                JSONObject jsonObject = JSON.parseObject(tempString);
                System.out.println("line " + line + ":" +jsonObject.toJSONString());
                String id = jsonObject.getString("id");
                String urlPost = "http://upload.jmvideo.jumei.com/meta";
                String uri;
                String result;
                Map map = new HashMap();
                if (StringUtils.isNotBlank(jsonObject.getString("video_url_hw_trans"))) {
                    uri = jsonObject.getString("video_url_hw_trans").replace("http://jmvideo.jumei.com/", "");
                } else {
                    uri = jsonObject.getString("video_url").replace("http://jmvideo.jumei.com/", "");
                }

                map.put("id", uri);
                String response = HttpUtils.doPost(urlPost, map);
                System.out.println("response ="+response);
                JSONObject jsonObject1 = JSON.parseObject(response);
                System.out.println(uri);
                System.out.println("line " + line + ": "+jsonObject1.toJSONString());
                System.out.println("line " + line + ": " +uri+":"+ jsonObject1.toJSONString());
                Integer fileSize = (Integer) jsonObject1.get("fileSize");
                if (fileSize < 8 * 1024 * 1024) {
                    result = uri + "," + fileSize;
                    bw1.write(result + "\t\n");
                } else if (jsonObject.getString("video_url_hw_trans") == null && fileSize >= 8 * 1024 * 1024) {
                    result = uri + "," + fileSize;
                    bw2.write(result + "\t\n");
                }/*
                result = id + "," + uri;
                bw.write(result + "\t\n");*/
                line++;
                /*Thread.sleep(500);*/

            }
/*
            Thread.sleep(1000 * 5);
*/
            bw1.close();
            bw2.close();
            fw1.close();
            fw2.close();
            bw.close();
            fw.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();

                } catch (IOException e1) {
                }
            }
        }
    }

    public static void main(String[] args) {
        readFileByLines("D:/tmp/6.2.res.txt");
       /* String url = "http://upload.jmvideo.jumei.com/meta";
        String param = "ZGVmYXVsdFVzZXI_E/MTU1ODk0NTY1OTI0Mw_E_E/MjA5NzE1Mg_E_E/ZmlsZQ_E_E_0_default.mp4";
        Map map = new HashMap();
        map.put("id", param);
        String result = HttpUtils.doPost(url, map);
        JSONObject jsonObject1 = JSON.parseObject(result);
        Integer fileSize = (Integer) jsonObject1.get("fileSize");
        System.out.println(result + " ===" + fileSize);
        if (fileSize > 8 * 1024 * 1024) {
            System.out.println("文件大小大于8M" + fileSize / 1024 / 1024 + "M");
        } else {
            System.out.println("文件大小小于8M" + fileSize / 1024 / 1024 + "M");
        }*/

     /*   String data = "{\"video_time\":\"15400\",\"activity_pic\":\"\",\"product_types\":\"\",\"upload_source\":\"0\",\"description\":\"不太想动笔，就简单的画个眼睛吧\",\"product_major_pic_w\":\"\",\"c_i\":\"606\",\"type\":\"0\",\"seq_num\":\"0\",\"video_w\":\"544\",\"video_url_hw_trans\":\"http://jmvideo.jumei.com/ZGVmYXVsdFVzZXI_E/MTU1OTAzMTI3MjU3MA_E_E/MTA0ODU3Ng_E_E/ZmlsZQ_E_E_0_default.mp4\",\"app_recommend_channel\":\"1\",\"video_url\":\"http://jmvideo.jumei.com/ZGVmYXVsdFVzZXI_E/MTU1OTAzMTI3MjU3MA_E_E/MTA0ODU3Ng_E_E/ZmlsZQ_E_E_default.mp4\",\"last_edit_time\":\"1559472227\",\"audit\":\"1\",\"real_play_count\":\"2006779\",\"video_h\":\"976\",\"from_post\":\"3\",\"post_type\":\"3\",\"id\":\"SMALL_VIDEO_100257497\",\"product_major_pic\":\"\",\"mocked_praise_count\":\"301\",\"activity_subtitle\":\"\",\"activity_text\":\"\",\"major_pic\":\"c2e12abb-fb44-4317-b6da-3587e304ffe1\",\"create_time\":\"1559031274\",\"activity_url\":\"\",\"sync\":\"1\",\"praise_bubble_count\":\"110723\",\"share_count\":\"0\",\"real_show_praise_count\":\"30504\",\"comment_bubble_count\":\"906\",\"user_id\":\"396483537\",\"product_major_pic_h\":\"\",\"music_id\":\"MUSIC_ID_1168953\",\"praise_count\":\"301\",\"real_show_comment_count\":\"329\",\"status\":\"1\"}";
        JSONObject jsonObject1 = JSON.parseObject(data);
        String id = jsonObject1.getString("id");

        System.out.println("" +id+":"+ jsonObject1.toJSONString());*/

    }

    public void writeFile() throws IOException {

        FileWriter fw = new FileWriter(new File("E:/phsftp/evdokey/evdokey_201103221556.txt"));
        //写入中文字符时会出现乱码
        BufferedWriter bw = new BufferedWriter(fw);
        //BufferedWriter  bw=new BufferedWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("E:/phsftp/evdokey/evdokey_201103221556.txt")), "UTF-8")));


        bw.close();
        fw.close();
    }


    /**
     * A方法追加文件：使用RandomAccessFile
     */
    public static void appendMethodA(String fileName, String content) {
        try {
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            //将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
            randomFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}