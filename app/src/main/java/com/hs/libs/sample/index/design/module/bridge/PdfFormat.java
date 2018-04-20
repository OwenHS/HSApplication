package com.hs.libs.sample.index.design.module.bridge;

/**
 * Created by huangshuo on 17/10/27.
 */

public class PdfFormat extends AbstractFormat {

    public PdfFormat(AbstractDatabase database) {
        super(database);
    }

    @Override
    public String translate() {
        String a =  database.getDatabaseData();
        return a + "已经转化成pdf了";
    }
}
