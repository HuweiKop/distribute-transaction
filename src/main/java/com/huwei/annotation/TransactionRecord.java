package com.huwei.annotation;

import java.lang.annotation.*;

/**
 * Created by huwei on 2017/6/19.
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TransactionRecord {
    String value() default "";

//    long transactionNo();
}
