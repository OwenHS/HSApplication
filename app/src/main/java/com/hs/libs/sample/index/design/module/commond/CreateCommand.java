package com.hs.libs.sample.index.design.module.commond;

/**
 * Created by huangshuo on 17/10/27.
 */

public class CreateCommand extends AbstractCommond {
    @Override
    public void execute() {
        boardScreen.create();
    }
}
