package com.huwei.aspect;

import com.huwei.threadLocal.TransactionInfoThreadLocal;
import com.huwei.annotation.TransactionRecord;
import com.huwei.model.TransactionInfoModel;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * Created by huwei on 2017/6/19.
 */
@Aspect
@Component
public class TransactionRecordAspect {
    //标注该方法体为后置通知，当目标方法执行成功后执行该方法体
    @Before("within(@org.springframework.stereotype.Component *) && @annotation(tr)")
    public void recordTransaction(JoinPoint jp, TransactionRecord tr) throws Throwable {
        System.out.println("TransactionRecord before.......");
        String txName = tr.value();
        Long txNo = System.currentTimeMillis();
        System.out.println(txName);
        System.out.println(txNo);
        TransactionInfoModel model = new TransactionInfoModel();
        model.setTransactionName(txName);
        model.setTransactionNo(txNo);
        model.setProcessStragery(tr.processStrategy());
        TransactionInfoThreadLocal.set(model);
    }
}
