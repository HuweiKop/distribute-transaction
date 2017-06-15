package com.huwei;

import com.huwei.constant.RedisKey;
import com.huwei.distribute.transaction.MessageManager;
import com.huwei.jedis.JedisHelper;
import com.huwei.service.Service1;
import redis.clients.jedis.Jedis;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        System.out.println( "Hello World!" );
        Service1 service1 = new Service1();
        service1.execute(1,"xxx");

        MessageManager manager = new MessageManager();
        manager.execute();

        Jedis jedis = JedisHelper.getInstance().getJedis();
        while (true) {
            String result = jedis.rpop(RedisKey.RepeatMessage);
            if(result==null){
                break;
            }
            System.out.println("end:"+result);
        }
    }
}
