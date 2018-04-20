package com.hs.libs.sample.index.design.module.facade;

/**
 * Created by huangshuo on 17/10/19.
 */

public class EncryptFacade {

    CipherMachine machine;
    FileRead fileRead;
    FileWrite fileWrite;

    public EncryptFacade() {
        machine = new CipherMachine();
        fileRead = new FileRead();
        fileWrite = new FileWrite();
    }

    //调用其他对象的业务方法
    public void fileEncrypt(String fileNameSrc, String fileNameDes) {
        String plainStr = fileRead.read(fileNameSrc);
        String encryptStr = machine.Encrypt(plainStr);
        fileWrite.write(encryptStr, fileNameDes);
    }

}
