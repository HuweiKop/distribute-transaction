package com.huwei;

import com.huwei.model.TransactionInfoModel;

/**
 * Created by huwei on 2017/6/19.
 */
public class TransactionInfoThreadLocal {
    private static ThreadLocal<TransactionInfoModel> threadLocal = new ThreadLocal<>();

    public static void set(TransactionInfoModel model){
        threadLocal.set(model);
    }

    public static TransactionInfoModel get(){
        return threadLocal.get();
    }
}
