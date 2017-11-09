package com.huwei.model;

import com.huwei.mybatis.plugin.SqlModel;

import java.util.List;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 17:39 2017/11/9
 * @Modified By
 */
public class ServerActionModel {
    private String serviceName;
    private String className;
    private String methodName;
    private Object[] parames;
    private Class<?>[] parameterTypes;
    private int transactionStatus;
    private String exception;

    private String transactionId;
    private List<SqlModel> sqlAction;

    public int getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(int transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public List<SqlModel> getSqlAction() {
        return sqlAction;
    }

    public void setSqlAction(List<SqlModel> sqlAction) {
        this.sqlAction = sqlAction;
    }
}
