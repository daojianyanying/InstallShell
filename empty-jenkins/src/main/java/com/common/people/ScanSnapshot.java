package com.common.people;

import hudson.model.TaskListener;

import java.io.*;

public class ScanSnapshot {

    public static void checkPom(String pomPath, TaskListener listener) {
        listener.getLogger().println("开始check快照版本");
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(new FileInputStream(pomPath));
            BufferedReader bufferedReader = new BufferedReader( inputStreamReader);
            // 按行读取字符串
            String str;
            int num = 0;
            while ((str = bufferedReader.readLine()) != null) {
                num++;
                if(str.contains("snapshot") || str.contains("Snapshot") || str.contains("SNAPSHOT")){
                    listener.error("\"存在snapshot版本:\" + str + \";报错文件行数:\" + num + \";报错文件名\" + pomPath");
                    throw new RuntimeException("存在snapshot版本:" + str + ";报错文件行数:" + num + ";报错文件名" + pomPath);
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            listener.getLogger().println("未找到pom.xml文件");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
