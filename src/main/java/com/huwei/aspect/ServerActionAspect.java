package com.huwei.aspect;

import com.alibaba.fastjson.JSON;
import com.huwei.annotation.ServerAction;
import com.huwei.annotation.TransactionId;
import com.huwei.constant.RedisKey;
import com.huwei.constant.TransactionStatus;
import com.huwei.jedis.JedisHelper;
import com.huwei.model.ServerActionModel;
import com.huwei.util.TransactionSqlMap;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 16:55 2017/10/27
 * @Modified By
 */
@Aspect
@Component
public class ServerActionAspect {

    //标注该方法体为后置通知，当目标方法执行成功后执行该方法体
    @Around("within(@org.springframework.stereotype.Component *) && @annotation(mr)")
    public Object recordMessage(ProceedingJoinPoint jp, ServerAction mr) throws Throwable {

        Class c = jp.getTarget().getClass();
        Component serviceAnt = (Component) c.getAnnotation(Component.class);
        if (serviceAnt == null) {
            throw new RuntimeException("该类需要指明 service name");
        }
        String serviceName = serviceAnt.value();
        Object[] args = jp.getArgs();//获取目标方法体参数
        String className = jp.getTarget().getClass().getName();//获取目标类名
        System.out.println("aspect ................" + serviceName);
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();

        ServerActionModel serverActionModel = new ServerActionModel();
        serverActionModel.setServiceName(serviceName);
        serverActionModel.setClassName(className);
        serverActionModel.setMethodName(method.getName());
        serverActionModel.setParames(args);
        serverActionModel.setParameterTypes(method.getParameterTypes());

        Parameter[] params = method.getParameters();
        for(int i=0;i<params.length;i++){
            if(params[i].isAnnotationPresent(TransactionId.class)){
                serverActionModel.setTransactionId(args[i].toString());
            }
        }
        if(StringUtils.isEmpty(serverActionModel.getTransactionId())){
            throw new RuntimeException("该方法需要参数transaction id");
        }

        TransactionSqlMap.initTransaction(serverActionModel.getTransactionId());

        try {
            Object result = jp.proceed();
            serverActionModel.setTransactionStatus(TransactionStatus.sucess);
            serverActionModel.setSqlAction(TransactionSqlMap.get(serverActionModel.getTransactionId()));
            TransactionSqlMap.remove(serverActionModel.getTransactionId());
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();

            serverActionModel.setTransactionStatus(TransactionStatus.error);
            serverActionModel.setException(ex.getMessage());
        }finally {
            String json = JSON.toJSONString(serverActionModel);
            JedisHelper.getInstance().lpush(RedisKey.TransactionRecord, json);
        }
        return null;
    }
}
