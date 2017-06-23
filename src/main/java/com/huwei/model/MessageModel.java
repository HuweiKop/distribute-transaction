package com.huwei.model;

import java.io.Serializable;

/**
 * Created by huwei on 2017/6/2.
 */
public class MessageModel implements Serializable {
    private String serviceName;
    private String className;
    private String methodName;
    private Object[] parames;
    private Class<?>[] parameterTypes;
    private String exception;
    private Integer repeatTimes;
    private Integer processStrategy;
    private Long timestamp;

    private String transactionName;
    private Long transactionNo;


    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParames() {
        return parames;
    }

    public void setParames(Object[] parames) {
        this.parames = parames;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public Integer getRepeatTimes() {
        return repeatTimes;
    }

    public void setRepeatTimes(Integer repeatTimes) {
        this.repeatTimes = repeatTimes;
    }

    public Integer getProcessStrategy() {
        return processStrategy;
    }

    public void setProcessStrategy(Integer processStrategy) {
        this.processStrategy = processStrategy;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public Long getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(Long transactionNo) {
        this.transactionNo = transactionNo;
    }
}
