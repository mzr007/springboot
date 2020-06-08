package com.example.springboot.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class Tools {
    public static String exePython(String filePath, Object... args) {
        String result = "";
        try {
            File file = new File(filePath);
            if (!file.exists()) {//判断文件目录的存在
                File fileDir = new File(file.getParent());
                fileDir.mkdirs();
            }

            StringBuffer parameter = new StringBuffer("python ");
            parameter.append(filePath);
            parameter.append(" ");
            if (args instanceof Object[]) {
                for (Object arg : args) {
                    parameter.append(arg);
                    parameter.append(" ");
                }
            }

            Process pr = Runtime.getRuntime().exec(parameter.toString());
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    pr.getInputStream(), Charset.forName("UTF-8")));
            String line;
            StringBuffer sbf = new StringBuffer();
            while ((line = in.readLine()) != null) {
                sbf.append(line);
            }
            result = sbf.toString();
        } catch (IOException e) {
            System.out.println("执行python文件失败。");
        }
        return result;
    }



    public static void main(String[] args) throws Exception {

    }
}
