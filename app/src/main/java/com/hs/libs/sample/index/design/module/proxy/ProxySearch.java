package com.hs.libs.sample.index.design.module.proxy;

/**
 * 查询代理类
 * Created by huangshuo on 17/10/18.
 */
public class ProxySearch extends AbstractSearch {

    Logger logger;
    AccessValidator accessValidator;

    private RealSearch realSearch = new RealSearch();

    @Override
    public String doSearch(String userId, String keyword) {
        /**
         * 本实例是保护代理和智能引用代理的应用实例，
         * 在代理类ProxySearcher中实现对真实主题类的权限控制和引用计数
         */
        if (validator(userId)) {
            String result = realSearch.doSearch(userId, keyword); //调用真实主题对象的查询方法
            log(userId); //记录查询日志
            return result; //返回查询结果
        }

        return null;
    }

    private boolean validator(String userId) {
        accessValidator = new AccessValidator();
        return accessValidator.validate(userId);
    }

    private void log(String userId) {
        logger = new Logger();
        logger.d(userId);
    }
}
