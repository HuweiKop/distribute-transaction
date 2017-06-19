package com.huwei.distribute.transaction;

import com.alibaba.fastjson.JSON;
import com.huwei.constant.RedisKey;
import com.huwei.jedis.JedisHelper;
import com.huwei.model.MessageModel;
import com.huwei.service.BaseService;

import java.lang.reflect.Method;

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
        String json = JSON.toJSONString(model);
        if(!repeat){
            JedisHelper.getInstance().lpush(RedisKey.Message, json);
        }

        return true;
    }
}
