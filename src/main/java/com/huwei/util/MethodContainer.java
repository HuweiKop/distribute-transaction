package com.huwei.util;

import java.lang.reflect.Method;

/**
 * Created by huwei on 2017/6/23.
 */
public class MethodContainer {

    public static Method getRollbackMethod(String key){
        return BeanScanner.getInstance().getMethod(key);
    }
}
