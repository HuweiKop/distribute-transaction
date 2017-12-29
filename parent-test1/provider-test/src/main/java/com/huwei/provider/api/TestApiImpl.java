package com.huwei.provider.api;

import com.huwei.annotation.ServerAction;
import com.huwei.annotation.TransactionId;
import com.huwei.api.ITestApi;
import com.huwei.model.UserDTO;
import com.huwei.provider.dao.IUserDao;
import com.huwei.provider.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 19:23 2017/11/9
 * @Modified By
 */
@Component("testApi")
public class TestApiImpl implements ITestApi {

    @Autowired
    IUserDao userDao;

    @ServerAction
    @Transactional
    @Override
    public void insertUser(UserDTO user, @TransactionId String transactionId) {
        userDao.insert(User.toUser(user));
    }
}
