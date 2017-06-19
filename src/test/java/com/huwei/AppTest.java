package com.huwei;

import com.huwei.annotation.MessageRecord;
import com.huwei.api.TestApi;
import com.huwei.constant.RedisKey;
import com.huwei.distribute.transaction.MessageManager;
import com.huwei.jedis.JedisHelper;
import com.huwei.service.Service1;
import com.huwei.service.Service2;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * Created by huwei on 2017/6/14.
 */
public class AppTest extends BaseTest {

    @Autowired
    Service1 service1;
    @Autowired
    Service2 service2;
    @Autowired
    TestApi testApi;

    @Test
    public void test() {

        try {
            testApi.execute(1,"xxx");
        } catch (Exception e) {
            System.out.println("eeeeeeeeeeeeeeeeeeeee");
            e.printStackTrace();
        }

//        try {
//            MessageManager manager = new MessageManager();
//            manager.execute();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        Jedis jedis = JedisHelper.getInstance().getJedis();
//        while (true) {
//            String result = jedis.rpop(RedisKey.RepeatMessage);
//            if(result==null){
//                break;
//            }
//            System.out.println("end:"+result);
//        }
    }
}
