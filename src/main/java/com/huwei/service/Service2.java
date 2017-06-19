package com.huwei.service;

import com.huwei.annotation.MessageRecord;
import com.huwei.dao.IUserDao;
import com.huwei.distribute.transaction.MessageRecorder;
import com.huwei.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huwei on 2017/6/6.
 */
@Service("service2")
public class Service2 extends BaseService {

    @Autowired
    IUserDao userDao;

//    @MessageRecord
    @Transactional
    public void execute(long id, String msg) throws Exception {
        System.out.println("service 2 execute........");
        User user = new User();
        user.setUsername(msg);
        userDao.insert(user);
        System.out.println("service 2 插入成功。。。。");
        throw new RuntimeException("service 2 exception........");
    }
}
