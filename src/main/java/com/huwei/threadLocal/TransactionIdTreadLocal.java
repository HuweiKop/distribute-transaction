package com.huwei.threadLocal;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 18:25 2017/11/14
 * @Modified By
 */
public class TransactionIdTreadLocal {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void set(String transactionId){
        threadLocal.set(transactionId);
    }

    public static String get(){
        return threadLocal.get();
    }
}
