package com.huwei.api;

import com.huwei.annotation.MessageRecord;
import com.huwei.service.Service1;
import com.huwei.service.Service2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huwei on 2017/6/19.
 */
@Component("api2")
public class Api2 extends BaseApi {


    @Autowired
    private Service2 service2;

    @MessageRecord
    public void execute(long id, String msg) throws Exception {
        service2.execute(id,msg);
    }
}
