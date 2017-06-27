package com.huwei.distribute.transaction;

import com.alibaba.fastjson.JSON;
import com.huwei.ApplicationUtil;
import com.huwei.api.BaseApi;
import com.huwei.constant.ProcesStrategy;
import com.huwei.constant.RedisKey;
import com.huwei.constant.TransactionStatus;
import com.huwei.jedis.JedisHelper;
import com.huwei.model.MessageModel;
import com.huwei.model.TransactionInfoModel;
import com.huwei.model.TransactionStatusModel;
import com.huwei.service.BaseService;
import com.huwei.threadLocal.MessageRepeatCountThreadLocal;
import com.huwei.util.BeanScanner;
import com.huwei.util.MethodContainer;
import redis.clients.jedis.Jedis;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huwei on 2017/6/2.
 */
public class MessageManager {

    public void execute() throws Exception {
        Jedis jedis = JedisHelper.getInstance().getJedis();
        while (true) {
            String result = jedis.rpop(RedisKey.Message);
            if (result == null) {
                break;
            }
            System.out.println(result);
            MessageModel messageModel = JSON.parseObject(result, MessageModel.class);
            if(messageModel.getProcessStrategy()== ProcesStrategy.ReTry) {
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
                } catch (Exception e) {
//                    messageModel.setTimestamp(System.currentTimeMillis());
//                    messageModel.setRepeatTimes(messageModel.getRepeatTimes() + 1);
//                    String modelJson = JSON.toJSONString(messageModel);
//                    JedisHelper.getInstance().lpush(RedisKey.RepeatMessage, modelJson);
//                    throw e;
                }
            }else if(messageModel.getProcessStrategy()==ProcesStrategy.Rollback){
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
    }
}
