package com.hs.libs.sample.index.design.module.bridge;

/**
 * Created by huangshuo on 17/10/27.
 */

public class TxtFormat extends AbstractFormat {

    public TxtFormat(AbstractDatabase database) {
        super(database);
    }

    @Override
    public String translate() {
       String a =  database.getDatabaseData();
        return a + "已经转化成txt了";
    }
}
