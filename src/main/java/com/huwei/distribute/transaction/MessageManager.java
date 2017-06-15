package com.huwei.distribute.transaction;

import com.alibaba.fastjson.JSON;
import com.huwei.ApplicationUtil;
import com.huwei.constant.RedisKey;
import com.huwei.jedis.JedisHelper;
import com.huwei.model.MessageModel;
import com.huwei.service.BaseService;
import redis.clients.jedis.Jedis;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by huwei on 2017/6/2.
 */
public class MessageManager {

    public void execute() throws Exception {
        Jedis jedis = JedisHelper.getInstance().getJedis();
        while (true){
            String result = jedis.rpop(RedisKey.Message);
            if(result==null){
                break;
            }
            System.out.println(result);
            try {
                MessageModel messageModel = JSON.parseObject(result,MessageModel.class);
                Class c = Class.forName(messageModel.getClassName());
                Method method = c.getMethod(messageModel.getMethodName(),messageModel.getParameterTypes());
                Object[] params = messageModel.getParames();
                BaseService service = (BaseService)ApplicationUtil.getBean(messageModel.getServiceName());
                service.repeat();
                method.invoke(service,params);
            } catch (Exception e) {
                JedisHelper.getInstance().lpush(RedisKey.RepeatMessage, result);
                throw new Exception(e);
            }
        }
    }
}