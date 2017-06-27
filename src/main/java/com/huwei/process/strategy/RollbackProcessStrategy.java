package com.huwei.process.strategy;

import com.alibaba.fastjson.JSON;
import com.huwei.ApplicationUtil;
import com.huwei.constant.RedisKey;
import com.huwei.constant.TransactionStatus;
import com.huwei.jedis.JedisHelper;
import com.huwei.model.MessageModel;
import com.huwei.model.TransactionStatusModel;
import com.huwei.util.MethodContainer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huwei on 2017/6/27.
 */
public class RollbackProcessStrategy extends BaseProcessStrategy {

    @Override
    public void processing(MessageModel messageModel) throws InvocationTargetException, IllegalAccessException {
        String transactionKey = messageModel.getTransactionName()+"::"+messageModel.getTransactionNo();
        List<String> transactionStatusJson = JedisHelper.getInstance().getList(transactionKey);
        System.out.println(transactionStatusJson);
        List<String> newTransactionStatusJson = new ArrayList<>();
        for(String json:transactionStatusJson){
            TransactionStatusModel transactionStatusModel = JSON.parseObject(json,TransactionStatusModel.class);
            if(transactionStatusModel.getServiceStatus()== TransactionStatus.sucess){
                Method method = MethodContainer.getRollbackMethod(transactionStatusModel.getRollbackKey());
                if(method!=null){
                    Object service = ApplicationUtil.getBean(transactionStatusModel.getServiceName());
                    try {
                        method.invoke(service, transactionStatusModel.getRollbackParames());

                        transactionStatusModel.setServiceStatus(TransactionStatus.error);
                    }catch (Exception ex){
                        for(int i=newTransactionStatusJson.size();i<transactionStatusJson.size();i++){
                            newTransactionStatusJson.add(transactionStatusJson.get(i));
                        }
                        //重新设置事务执行状态
                        JedisHelper.getInstance().setList(transactionKey,newTransactionStatusJson);
                        messageModel.setRepeatTimes(messageModel.getRepeatTimes()+1);
                        JedisHelper.getInstance().lpush(RedisKey.RepeatMessage, JSON.toJSONString(messageModel));
                        throw ex;
                    }
                }
            }
            String newJson = JSON.toJSONString(transactionStatusModel);
            newTransactionStatusJson.add(newJson);
        }

        //重新设置事务执行状态
        JedisHelper.getInstance().setList(transactionKey,newTransactionStatusJson);
    }
}
