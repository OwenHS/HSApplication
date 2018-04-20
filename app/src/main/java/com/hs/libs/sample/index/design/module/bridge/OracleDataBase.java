package com.hs.libs.sample.index.design.module.bridge;

/**
 * Created by huangshuo on 17/10/27.
 */

public class OracleDataBase extends AbstractDatabase {
    @Override
    public String getDatabaseData() {
        return "获取了Oracle的数据";
    }
}
