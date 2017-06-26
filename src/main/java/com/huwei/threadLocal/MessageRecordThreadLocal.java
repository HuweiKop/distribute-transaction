package com.huwei.threadLocal;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by huwei on 2017/6/16.
 */
public class MessageRecordThreadLocal {
    private static ThreadLocal<Set<String>> threadLocal = new ThreadLocal<Set<String>>(){
        public Set<String> initialValue() {
            return new HashSet<>();
        }
    };

    public static boolean isExist(String msg){
        Set<String> set = threadLocal.get();
        return set.contains(msg);
    }

    public static void set(String msg){
        Set<String> set = threadLocal.get();
        set.add(msg);
    }

    public static boolean isExistMessageRecord(){
        return isExist("messageRecord");
    }

    public static void setMessageRecord(){
        set("messageRecord");
    }
}
