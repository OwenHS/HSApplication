package com.hs.hshttplib.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by owen on 16-2-18.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface CallBack {
}
