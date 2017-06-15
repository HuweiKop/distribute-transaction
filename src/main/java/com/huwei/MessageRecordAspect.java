package com.huwei;

import com.huwei.annotation.MessageRecord;
import com.huwei.distribute.transaction.MessageRecorder;
import com.huwei.service.BaseService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * Created by huwei on 2017/6/14.
 */
@Aspect
@Component
public class MessageRecordAspect {
    //标注该方法体为后置通知，当目标方法执行成功后执行该方法体
    @Around("within(@org.springframework.stereotype.Service *) && @annotation(rl)")
    public Object  recordMessage(ProceedingJoinPoint jp, MessageRecord rl) throws Exception {
        Object[] parames = jp.getArgs();//获取目标方法体参数
//        String params = parseParames(parames); //解析目标方法体的参数
        String className = jp.getTarget().getClass().getName();//获取目标类名
        Class c = jp.getTarget().getClass();
        Service serviceAnt = (Service) c.getAnnotation(Service.class);
        if(serviceAnt==null){
            throw new RuntimeException("该类需要指明 service name");
        }
        String serviceName = serviceAnt.value();
//        className = className.substring(className.indexOf("com"));
//        String signature = jp.getSignature().toString();//获取目标方法签名
//        String methodName = signature.substring(signature.lastIndexOf(".")+1, signature.indexOf("("));
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();
//        String serviceName = rl.serviceName();
//        String modelName = getModelName(className); //根据类名获取所属的模块
//        System.out.println(methodName);
//        System.out.println(signature);
//        for(Object obj:parames){
//            System.out.println(obj);
//        }

        try {
            return jp.proceed();
        } catch (Exception ex){
            System.out.println("aspect ................");
            BaseService service = (BaseService)jp.getThis();
            System.out.println(service.isRepeat());
            MessageRecorder.recordMessage(className, method.getName(),serviceName,method.getParameterTypes(), parames, ex,service.isRepeat());
            throw new Exception(ex);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new Exception(throwable);
        }
    }
}
