package com.huwei.annotation;

import java.lang.annotation.*;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 15:22 2017/11/8
 * @Modified By
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServerAction {

    String value() default "";
}
