package com.hs.hshttplib.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 文 件 名：Execute
 * 描    述：用于执行业务
 * 创 建 人: OWEN_HUANG
 * 创建日期: 2017/6/26 09:40
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Execute {
    /**
     * 写入全类名，用于执行
     */
    String value() default "";
}