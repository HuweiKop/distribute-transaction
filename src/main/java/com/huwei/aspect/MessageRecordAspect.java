package com.huwei.aspect;

import com.huwei.threadLocal.TransactionInfoThreadLocal;
import com.huwei.annotation.MessageRecord;
import com.huwei.constant.ProcesStrategy;
import com.huwei.constant.TransactionStatus;
import com.huwei.distribute.transaction.MessageRecorder;
import com.huwei.model.TransactionInfoModel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by huwei on 2017/6/14.
 */
@Aspect
@Component
//@Order(2)
public class MessageRecordAspect {
    //标注该方法体为后置通知，当目标方法执行成功后执行该方法体
    @Around("within(@org.springframework.stereotype.Component *) && @annotation(mr)")
    public Object recordMessage(ProceedingJoinPoint jp, MessageRecord mr) throws Throwable {
//        Object[] parames = jp.getArgs();//获取目标方法体参数
//        String params = parseParames(parames); //解析目标方法体的参数
//        String className = jp.getTarget().getClass().getName();//获取目标类名
//        Class c = jp.getTarget().getClass();
//        Service serviceAnt = (Service) c.getAnnotation(Service.class);
//        if(serviceAnt==null){
//            throw new RuntimeException("该类需要指明 service name");
//        }
//        String serviceName = serviceAnt.value();
////        className = className.substring(className.indexOf("com"));
////        String signature = jp.getSignature().toString();//获取目标方法签名
////        String methodName = signature.substring(signature.lastIndexOf(".")+1, signature.indexOf("("));
//        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
//        Method method = methodSignature.getMethod();
//        String serviceName = mr.serviceName();
//        String modelName = getModelName(className); //根据类名获取所属的模块
//        System.out.println(methodName);
//        System.out.println(signature);
//        for(Object obj:parames){
//            System.out.println(obj);
//        }

        Class c = jp.getTarget().getClass();
        Component serviceAnt = (Component) c.getAnnotation(Component.class);
        if (serviceAnt == null) {
            throw new RuntimeException("该类需要指明 service name");
        }
        String serviceName = serviceAnt.value();
        Object[] parames = jp.getArgs();//获取目标方法体参数
        String className = jp.getTarget().getClass().getName();//获取目标类名
        System.out.println("aspect ................" + serviceName);
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();
//        BaseApi service = (BaseApi) jp.getThis();
        try {
            Object result = jp.proceed();
            MessageRecorder.recordTransactionStatus(serviceName,TransactionStatus.sucess,className,mr.rollbackServiceName(),parames);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();

            MessageRecorder.recordTransactionStatus(serviceName,TransactionStatus.error,className,mr.rollbackServiceName(),parames);
            boolean result = MessageRecorder.recordMessage(className, method.getName(), serviceName,
                    method.getParameterTypes(), parames, ex);
            if(!result){
                throw ex;
            }

            TransactionInfoModel transactionInfo = TransactionInfoThreadLocal.get();
            if(transactionInfo==null || transactionInfo.getProcessStragery()== ProcesStrategy.Rollback){
                throw ex;
            }
        }
        return null;
    }
}
