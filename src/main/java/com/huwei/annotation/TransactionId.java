package com.huwei.annotation;

import java.lang.annotation.*;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 15:26 2017/11/8
 * @Modified By
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TransactionId {
}
