package com.huwei;

import com.huwei.annotation.MessageRecord;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by huwei on 2017/6/15.
 */
//@Aspect
//@Component
public class TestAspect {
    //标注该方法体为后置通知，当目标方法执行成功后执行该方法体
    @Around("within(@org.springframework.stereotype.Service *) && @annotation(rl)")
    public Object  recordMessage(ProceedingJoinPoint jp, MessageRecord rl) throws Throwable {
        try {
            System.out.println("test..................");
            return jp.proceed();
        } catch (Exception e) {
            System.out.println("test.....catch.............");
            System.out.println(jp.getTarget().getClass().getName());
            e.printStackTrace();
            throw new Exception(e);
        }
    }
}
