package com.huwei.api;

import com.huwei.model.UserDTO;

/**
 * @Author: HuWei
 * @Description:
 * @Date: Created in 19:20 2017/11/9
 * @Modified By
 */
public interface ITestApi {

    void insertUser(UserDTO user, String transactionId);
}
