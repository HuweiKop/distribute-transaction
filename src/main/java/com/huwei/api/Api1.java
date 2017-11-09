package com.huwei.api;

import com.huwei.annotation.MessageRecord;
import com.huwei.annotation.RollbackService;
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

    @MessageRecord(rollbackServiceName = "rollback")
    public void execute(long id, String msg) throws Exception {
        service1.execute(id,msg);
    }

    public void update(int id, String username){
        service1.update(id,username);
    }

    @RollbackService("rollback")
    public void rollback(long id, String msg){
        service1.delUser(id);
    }
}
