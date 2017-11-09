package com.huwei.annotation;

import java.lang.annotation.*;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 16:59 2017/10/27
 * @Modified By
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SqlRecord {

    String value() default "";
}
