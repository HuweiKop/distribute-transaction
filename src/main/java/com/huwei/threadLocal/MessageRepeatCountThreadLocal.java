package com.huwei.threadLocal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by huwei on 2017/6/26.
 */
public class MessageRepeatCountThreadLocal {

    /**
     * key 为 transactionNo + className + methodName + parameterTypes
     */
    private static ThreadLocal<Map<String, Integer>> threadLocal = new ThreadLocal<Map<String, Integer>>(){
        public Map<String, Integer> initialValue() {
            return new HashMap<>();
        }
    };

    public static Integer getRepeatCount(String key){
        Map map = threadLocal.get();
        if(map==null){
            return null;
        }
        return (Integer) map.get(key);
    }

    public static boolean setRepeatCount(String key, Integer count){
        Map map = threadLocal.get();
        if(map==null){
            return false;
        }
        map.put(key,count);
        return true;
    }
}
