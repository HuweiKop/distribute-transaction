package com.huwei.process.strategy;

import com.alibaba.fastjson.JSON;
import com.huwei.ApplicationUtil;
import com.huwei.constant.RedisKey;
import com.huwei.constant.TransactionStatus;
import com.huwei.jedis.JedisHelper;
import com.huwei.model.MessageModel;
import com.huwei.model.TransactionStatusModel;
import com.huwei.threadLocal.MessageRepeatCountThreadLocal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huwei on 2017/6/27.
 */
public class RetryProcessStrategy extends BaseProcessStrategy {
    @Override
    public void processing(MessageModel messageModel) throws InvocationTargetException, IllegalAccessException {
        try {
            Class c = Class.forName(messageModel.getClassName());
            Method method = c.getMethod(messageModel.getMethodName(), messageModel.getParameterTypes());
            Object[] params = messageModel.getParames();
            Object service = ApplicationUtil.getBean(messageModel.getServiceName());
//                    service.repeat();
            String messageKey = messageModel.getTransactionNo()+"-"+messageModel.getClassName()+"-"
                    +messageModel.getMethodName();
            MessageRepeatCountThreadLocal.setRepeatCount(messageKey,messageModel.getRepeatTimes());
            method.invoke(service, params);

            //重新设置事务执行状态
            String transactionKey = messageModel.getTransactionName()+"::"+messageModel.getTransactionNo();
            List<String> transactionStatusJson = JedisHelper.getInstance().getList(transactionKey);
            List<String> newTransactionStatusJson = new ArrayList<>();
            for(String json:transactionStatusJson){
                TransactionStatusModel transactionStatusModel = JSON.parseObject(json,TransactionStatusModel.class);
                if(transactionStatusModel.getServiceName().equals(messageModel.getServiceName())){
                    transactionStatusModel.setServiceStatus(TransactionStatus.sucess);
                }
                String newJson = JSON.toJSONString(transactionStatusModel);
                newTransactionStatusJson.add(newJson);
            }
            JedisHelper.getInstance().setList(transactionKey,newTransactionStatusJson);
        } catch (Exception e) {
            messageModel.setTimestamp(System.currentTimeMillis());
            messageModel.setRepeatTimes(messageModel.getRepeatTimes() + 1);
            String modelJson = JSON.toJSONString(messageModel);
            JedisHelper.getInstance().lpush(RedisKey.RepeatMessage, modelJson);
//                    throw e;
        }
    }
}
