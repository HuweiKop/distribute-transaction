package com.huwei.api;

import com.huwei.annotation.MessageRecord;
import com.huwei.service.Service1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huwei on 2017/6/19.
 */
@Component("api1")
public class Api1 extends BaseApi {

    @Autowired
    private Service1 service1;

    @MessageRecord
    public void execute(long id, String msg) throws Exception {
        service1.execute(id,msg);
    }

    public void rollback(long id){
        service1.delUser(id);
    }
}