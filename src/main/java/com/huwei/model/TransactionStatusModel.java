package com.huwei.model;

import java.io.Serializable;

/**
 * Created by huwei on 2017/6/19.
 */
public class TransactionStatusModel implements Serializable {

    private String serviceName;
    private Integer serviceStatus;

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
}
