package com.huwei.threadLocal;

import com.huwei.model.TransactionInfoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 16:40 2017/10/27
 * @Modified By
 */
public class SqlThreadLocal {
    private static ThreadLocal<List<String>> threadLocal = new ThreadLocal<>();

    public static void set(List<String> sqlList){
        threadLocal.set(sqlList);
    }

    public static List<String> get(){
        List<String> list = threadLocal.get();
        if(list==null){
            list = new ArrayList<>();
        }
        return list;
    }
}
