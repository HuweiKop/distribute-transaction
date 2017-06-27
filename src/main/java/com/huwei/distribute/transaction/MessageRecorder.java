package com.huwei.distribute.transaction;

import com.alibaba.fastjson.JSON;
import com.huwei.threadLocal.MessageRecordThreadLocal;
import com.huwei.threadLocal.MessageRepeatCountThreadLocal;
import com.huwei.threadLocal.TransactionInfoThreadLocal;
import com.huwei.constant.ProcesStrategy;
import com.huwei.constant.RedisKey;
import com.huwei.jedis.JedisHelper;
import com.huwei.model.MessageModel;
import com.huwei.model.TransactionInfoModel;
import com.huwei.model.TransactionStatusModel;

/**
 * Created by huwei on 2017/6/14.
 */
public class MessageRecorder {

    public static boolean recordMessage(String className, String methodName, String serviceName,
                                        Class<?>[] paramTypes, Object[] params, Exception ex){
        MessageModel model = new MessageModel();
        model.setClassName(className);
        model.setMethodName(methodName);
        model.setServiceName(serviceName);
        model.setParames(params);
        model.setParameterTypes(paramTypes);
        model.setException(ex.getMessage());
        model.setRepeatTimes(0);
        model.setTimestamp(System.currentTimeMillis());

        TransactionInfoModel transactionInfo = TransactionInfoThreadLocal.get();
        if(transactionInfo==null) {
            return false;
        }
        model.setTransactionName(transactionInfo.getTransactionName());
        model.setTransactionNo(transactionInfo.getTransactionNo());
        model.setProcessStrategy(transactionInfo.getProcessStragery());

        String messageKey = model.getTransactionNo()+"-"+model.getClassName()+"-"
                +model.getMethodName();
        Integer repeatCount = MessageRepeatCountThreadLocal.getRepeatCount(messageKey);
        System.out.println("repeat count ================"+repeatCount);
        boolean repeat = false;
        if(repeatCount!=null){
            repeat = true;
        }

        if(!repeat){
            String json = JSON.toJSONString(model);
            JedisHelper.getInstance().lpush(RedisKey.Message, json);
            return true;
        }

        return false;
    }

    public static void recordTransactionStatus(String serviceName, int transactionStatus, String className,
                                               String rollbackName, Object[] parames){
        TransactionInfoModel infoModel = TransactionInfoThreadLocal.get();
        if(infoModel==null){
            return;
        }
        TransactionStatusModel statusModel = new TransactionStatusModel();
        statusModel.setServiceName(serviceName);
        statusModel.setServiceStatus(transactionStatus);

        if(infoModel.getProcessStragery()== ProcesStrategy.Rollback){
            statusModel.setRollbackKey(className+"::"+rollbackName);
            statusModel.setRollbackParames(parames);
        }

        String key = infoModel.getTransactionName()+"::"+infoModel.getTransactionNo();
        String json = JSON.toJSONString(statusModel);
        System.out.println("recordTransactionStatus======================");
        System.out.println(json);
        JedisHelper.getInstance().lpush(key, json);
    }
}
