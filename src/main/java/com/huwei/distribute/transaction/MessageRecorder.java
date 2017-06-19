package com.huwei.distribute.transaction;

import com.alibaba.fastjson.JSON;
import com.huwei.TransactionInfoThreadLocal;
import com.huwei.constant.RedisKey;
import com.huwei.constant.TransactionStatus;
import com.huwei.jedis.JedisHelper;
import com.huwei.model.MessageModel;
import com.huwei.model.TransactionInfoModel;
import com.huwei.model.TransactionStatusModel;

/**
 * Created by huwei on 2017/6/14.
 */
public class MessageRecorder {

    public static boolean recordMessage(String className, String methodName, String serviceName,
                                        Class<?>[] paramTypes, Object[] params, Exception ex, boolean repeat,
                                        int processStrategy){
        MessageModel model = new MessageModel();
        model.setClassName(className);
        model.setMethodName(methodName);
        model.setServiceName(serviceName);
        model.setParames(params);
        model.setParameterTypes(paramTypes);
        model.setException(ex.getMessage());
        model.setRepeatTimes(0);
        model.setTimestamp(System.currentTimeMillis());
        model.setProcessStrategy(processStrategy);

        TransactionInfoModel transactionInfo = TransactionInfoThreadLocal.get();
        if(transactionInfo!=null) {
            model.setTransactionName(transactionInfo.getTransactionName());
            model.setTransactionNo(transactionInfo.getTransactionNo());
        }

        String json = JSON.toJSONString(model);
        if(!repeat){
            JedisHelper.getInstance().lpush(RedisKey.Message, json);
        }

        return true;
    }

    public static void recordTransactionStatus(String serviceName, int transactionStatus){
        TransactionStatusModel statusModel = new TransactionStatusModel();
        statusModel.setServiceName(serviceName);
        statusModel.setServiceStatus(transactionStatus);
        TransactionInfoModel infoModel = TransactionInfoThreadLocal.get();
        String key = infoModel.getTransactionName()+"::"+infoModel.getTransactionNo();
        String json = JSON.toJSONString(statusModel);
        System.out.println("recordTransactionStatus======================");
        System.out.println(json);
        JedisHelper.getInstance().lpush(key, json);
    }
}
