package com.huwei.annotation;

import java.lang.annotation.*;

/**
 * Created by huwei on 2017/6/23.
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RollbackService {
    String value() default "";
}
