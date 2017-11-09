package com.huwei.constant;

/**
 * Created by huwei on 2017/6/6.
 */
public class RedisKey {
    public static final String Message = "message::log";
    public static final String RepeatMessage = "message::log::repeat";

    public static final String RetryList = "service::retry";
    public static final String RollbackList = "service::rollback";

    public static final String TransactionRecord = "transaction:record";
}
