package com.hs.hshttplib.annotations;

/**
 * Created by owen on 16-2-19.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * PostJson
 * Created by owen on 16-2-3.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PostJson {
    String value() default "";
}