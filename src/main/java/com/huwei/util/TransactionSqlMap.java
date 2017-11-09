package com.huwei.util;

import com.huwei.mybatis.plugin.SqlModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 18:00 2017/11/9
 * @Modified By
 */
public class TransactionSqlMap {
    private static Map<String, List<SqlModel>> map = new ConcurrentHashMap<>();

    public static void initTransaction(String transactionId){
        map.put(transactionId, new ArrayList<>());
    }

    public static List<SqlModel> getTransactionSql(String transactionId){
        return map.get(transactionId);
    }

    public static void addSqlToTransaction(String transactionId, SqlModel sql){
        List<SqlModel> list = map.get(transactionId);
        list.add(sql);
    }
}
