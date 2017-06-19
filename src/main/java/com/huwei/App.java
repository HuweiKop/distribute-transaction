package com.huwei;

import com.huwei.constant.RedisKey;
import com.huwei.distribute.transaction.MessageManager;
import com.huwei.jedis.JedisHelper;
import com.huwei.service.Service1;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        List<String> transactionStatusJson = JedisHelper.getInstance().getList("txTestApi::1497872051534");
        System.out.println(transactionStatusJson);
    }
}
