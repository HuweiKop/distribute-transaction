package com.huwei;

import com.huwei.api.ITestApi;
import com.huwei.model.UserDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * Created by huwei on 2017/6/14.
 */
public class AppTest extends BaseTest {

    @Autowired
    ITestApi testApi;

    @Test
    public void test() {
        UserDTO user = new UserDTO();
        testApi.insertUser(user, UUID.randomUUID().toString());
    }
}
