package com.hs.libs.sample.index.design.module.commond;

/**
 * 命令基类
 * Created by huangshuo on 17/10/27.
 */

public abstract class AbstractCommond {

    public BoardScreen boardScreen;

    public AbstractCommond(){
        boardScreen = new BoardScreen();
    }

    /**
     * 命令执行类
     */
    public abstract void execute();
}
