package com.huwei.model;

import java.io.Serializable;

/**
 * Created by huwei on 2017/6/19.
 */
public class TransactionInfoModel implements Serializable {
    private String transactionName;
    private Long transactionNo;
    private int processStragery;

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

    public int getProcessStragery() {
        return processStragery;
    }

    public void setProcessStragery(int processStragery) {
        this.processStragery = processStragery;
    }
}
