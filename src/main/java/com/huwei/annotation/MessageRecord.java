package com.huwei.annotation;

import com.huwei.type.ProcesStrategy;

import java.lang.annotation.*;

/**
 * Created by huwei on 2017/6/14.
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MessageRecord {
    String value() default "";

    int processStrategy() default ProcesStrategy.ReTry;
}
