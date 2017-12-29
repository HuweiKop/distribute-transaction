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

    public static void initTransaction(String transactionId) {
        map.put(transactionId, new ArrayList<>());
    }

    public static List<SqlModel> get(String transactionId) {
        List<SqlModel> list = map.get(transactionId);
        if (list == null) {
            list = new ArrayList<>();
            map.put(transactionId, list);
        }
        return list;
    }

    public static void add(String transactionId, SqlModel sql) {
        List<SqlModel> list = map.get(transactionId);
        list.add(sql);
    }

    public static void remove(String transactionId) {
        map.remove(transactionId);
    }
}
