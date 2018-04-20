package com.hs.libs.sample.index.design.module.facade;

import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by huangshuo on 17/10/19.
 */

public class FileRead {

    public String read(String fileNameSrc) {
        Log.d("owen", "读取文件，获取明文：");
        FileInputStream fs = null;
        StringBuilder sb = new StringBuilder();
        try {
            fs = new FileInputStream(fileNameSrc);
            int data;
            while ((data = fs.read()) != -1) {
                sb = sb.append((char) data);
            }
            fs.close();
            Log.d("owen", sb.toString());
        } catch (FileNotFoundException e) {
            Log.d("owen", "文件不存在！");
        } catch (IOException e) {
            Log.d("owen", "文件操作错误！");
        }
        return sb.toString();
    }
}
