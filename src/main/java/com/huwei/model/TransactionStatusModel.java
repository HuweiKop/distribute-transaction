package com.huwei.model;

import java.io.Serializable;

/**
 * Created by huwei on 2017/6/19.
 */
public class TransactionStatusModel implements Serializable {

    private String serviceName;
    private Integer serviceStatus;

    private String rollbackKey;
    private Object[] rollbackParames;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(Integer serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getRollbackKey() {
        return rollbackKey;
    }

    public void setRollbackKey(String rollbackKey) {
        this.rollbackKey = rollbackKey;
    }

    public Object[] getRollbackParames() {
        return rollbackParames;
    }

    public void setRollbackParames(Object[] rollbackParames) {
        this.rollbackParames = rollbackParames;
    }
}
