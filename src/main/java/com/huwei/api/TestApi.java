package com.huwei.api;

import com.huwei.annotation.MessageRecord;
import com.huwei.annotation.TransactionRecord;
import com.huwei.constant.ProcesStrategy;
import com.huwei.service.Service1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huwei on 2017/6/16.
 */
@Component("testApi")
public class TestApi extends BaseApi {

    @Autowired
    private Api1 api1;
    @Autowired
    private Api2 api2;

    @Autowired
    private Service1 service1;

    @MessageRecord()
    @TransactionRecord(value = "txTestApi",processStrategy= ProcesStrategy.Rollback)
    public void execute(long id, String msg) throws Exception {
        api1.execute(id,msg);
        api2.execute(id,msg);
    }
}
