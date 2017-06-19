package com.huwei.distribute.transaction;

import com.alibaba.fastjson.JSON;
import com.huwei.ApplicationUtil;
import com.huwei.api.BaseApi;
import com.huwei.constant.ProcesStrategy;
import com.huwei.constant.RedisKey;
import com.huwei.jedis.JedisHelper;
import com.huwei.model.MessageModel;
import com.huwei.service.BaseService;
import redis.clients.jedis.Jedis;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
                    BaseApi service = (BaseApi) ApplicationUtil.getBean(messageModel.getServiceName());
                    service.repeat();
                    method.invoke(service, params);
                } catch (Exception e) {
                    messageModel.setTimestamp(System.currentTimeMillis());
                    messageModel.setRepeatTimes(messageModel.getRepeatTimes() + 1);
                    String modelJson = JSON.toJSONString(messageModel);
                    JedisHelper.getInstance().lpush(RedisKey.RepeatMessage, modelJson);
                    throw new Exception(e);
                }
            }else if(messageModel.getProcessStrategy()==ProcesStrategy.Rollback){
                String transactionKey = messageModel.getTransactionName()+"::"+messageModel.getTransactionNo();
                List<String> transactionStatusJson = JedisHelper.getInstance().getList(transactionKey);
                System.out.println(transactionStatusJson);
            }
        }
    }
}
