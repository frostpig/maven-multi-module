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

import java.io.*;

/**
 * Function: Please Descrip This Class.
 * <p>
 * Created by shuangshuangl on 2019/4/2.
 * Copyright (c) 2018,shuangshuangl@jumei.com All Rights Reserved.
 */
public class MyClassLoader extends ClassLoader {
    private String name;
    private String path;

    public MyClassLoader(String name, String path) {
        super();
        this.name = name;
        this.path = path;
    }

    public MyClassLoader(ClassLoader parent, String name, String path) {
        super(parent);
        this.name = name;
        this.path = path;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] b = readFileToByteArray(name);
        return this.defineClass(name,b,0,b.length);
    }

    private byte[] readFileToByteArray(String name) {
        InputStream is = null;
        byte[] rtnData = null;
        //转换
        name = name.replaceAll("\\.", "/");
        //拼接
        String filePath = this.path + name + ".class";
        File file = new File(filePath);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            is = new FileInputStream(file);
            int tmp = 0;
            if ((tmp = is.read()) != -1) {
                os.write(tmp);
            }
            rtnData = os.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return rtnData;
    }

    public static void main(String[] args) throws Exception {
        MyClassLoader loader1 = new MyClassLoader("loader1","D:/tmp/a/");
        MyClassLoader loader2 = new MyClassLoader(loader1,"loader2","D:/tmp/b/");
        MyClassLoader loader3 = new MyClassLoader(null,"loader3","D:/tmp/c/");
        Class c = loader1.loadClass("com.dn.Demo");
        c.newInstance();
    }

}