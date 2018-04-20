package com.hs.libs.sample.index.design.module.facade;

import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by huangshuo on 17/10/19.
 */

public class FileWrite {

    public void write(String encryptStr, String fileNameDes) {
        Log.d("owen", "保存密文，写入文件。");
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(fileNameDes);
//                byte[] str = Encoding.Default.GetBytes(encryptStr);
//                fs.Write(str,0,str.Length);
//                fs.Flush();
//                fs.Close();
        } catch (FileNotFoundException e) {
            Log.d("owen", "文不存在！");
        }
    }

}