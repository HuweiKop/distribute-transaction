package com.huwei.service;

import com.huwei.annotation.MessageRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huwei on 2017/6/6.
 */
@Service("service1")
public class Service1 extends BaseService {

    @Autowired
    Service2 service2;

    @MessageRecord
    @Transactional(rollbackFor=Exception.class)
    public void execute(long id, String msg) throws Exception {
        System.out.println("service 1 execute........");
        System.out.println("id:"+id+" msg:"+msg);
        service2.execute(id,msg);
    }
}
