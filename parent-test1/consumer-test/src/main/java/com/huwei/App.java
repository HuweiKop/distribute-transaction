package com.huwei;

import com.huwei.api.ITestApi;
import com.huwei.model.UserDTO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.UUID;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//        System.out.println( "Hello World!" );
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("dubbo_consumer.xml");
//        ITestApi testApi = (ITestApi) ctx.getBean("testApi");
//        UserDTO user = new UserDTO();
//        testApi.insertUser(user, UUID.randomUUID().toString());

        SuperClass clz = new SubClass();
        //你觉得这里输出什么?
        System.out.println(clz.getName());
    }
}

class SubClass extends SuperClass
{
    public String name = "SubClass";

    public String getName(){
        return name;
    }
}

class SuperClass
{
    public String name = "SuperClass";

    public String getName(){
        return name;
    }
}