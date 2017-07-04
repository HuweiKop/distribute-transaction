package com.huwei.distribute.transaction;

import com.alibaba.fastjson.JSON;
import com.huwei.ApplicationUtil;
import com.huwei.constant.ProcesStrategy;
import com.huwei.constant.RedisKey;
import com.huwei.constant.TransactionStatus;
import com.huwei.jedis.JedisHelper;
import com.huwei.model.MessageModel;
import com.huwei.model.TransactionStatusModel;
import com.huwei.process.strategy.BaseProcessStrategy;
import com.huwei.process.strategy.ProcessStrategyFactory;
import com.huwei.process.strategy.RetryProcessStrategy;
import com.huwei.process.strategy.RollbackProcessStrategy;
import com.huwei.threadLocal.MessageRepeatCountThreadLocal;
import redis.clients.jedis.Jedis;

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
            BaseProcessStrategy strategy = ProcessStrategyFactory.getProcessStrategy(messageModel.getProcessStrategy());
            if(strategy==null){
                System.out.println("没有注册该 处理策略:"+messageModel.getProcessStrategy());
                break;
            }
            strategy.processing(messageModel);
        }
    }
}
