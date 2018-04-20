package com.hs.libs.sample.index.design.module.facade;

import android.util.Log;

/**
 * Created by huangshuo on 17/10/19.
 */

public class CipherMachine {
    public String Encrypt(String plainText)
    {
        Log.d("owen","数据加密，将明文转换为密文：");
        String es = "";
        char[] chars = plainText.toCharArray();
        for(char ch : chars)
        {
            String c = String.valueOf(ch % 7);
            es += c;
        }
        Log.d("owen",es);
        return es;
    }
}
