package com.huwei.aspect;

import com.huwei.TransactionInfoThreadLocal;
import com.huwei.annotation.MessageRecord;
import com.huwei.annotation.TransactionRecord;
import com.huwei.api.BaseApi;
import com.huwei.distribute.transaction.MessageRecorder;
import com.huwei.model.TransactionInfoModel;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by huwei on 2017/6/19.
 */
@Aspect
@Component
public class TransactionRecordAspect {
    //标注该方法体为后置通知，当目标方法执行成功后执行该方法体
    @Before("within(@org.springframework.stereotype.Component *) && @annotation(rl)")
    public void recordTransaction(JoinPoint jp, TransactionRecord rl) throws Throwable {
        System.out.println("TransactionRecord before.......");
        String txName = rl.value();
        Long txNo = System.currentTimeMillis();
        System.out.println(txName);
        System.out.println(txNo);
        TransactionInfoModel model = new TransactionInfoModel();
        model.setTransactionName(txName);
        model.setTransactionNo(txNo);
        TransactionInfoThreadLocal.set(model);
    }
}
