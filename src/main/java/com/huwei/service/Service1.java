package com.huwei.service;

import com.huwei.annotation.MessageRecord;
import com.huwei.dao.IUserDao;
import com.huwei.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huwei on 2017/6/6.
 */
@Service("service1")
public class Service1 extends BaseService {

    @Autowired
    IUserDao userDao;

    @Autowired
    Service2 service2;

//    @MessageRecord
    @Transactional
    public void execute(long id, String msg) throws Exception {
        User user = new User();
        user.setUsername(msg);
        userDao.insert(user);
        System.out.println("service 1 execute........");
        System.out.println("service 1 插入成功。。。。");
//        service2.execute(id,msg);
//        throw new RuntimeException("service 1 exception........");
    }

    @Transactional
    public void update(Integer id, String username){
        User user = new User();
        user.setUsername(username);
        user.setId(id);
        userDao.update(user);
    }

    @Transactional
    public void delUser(long id){
        System.out.println("事务补偿 id "+id);
//        userDao.delete(id);
    }
}
