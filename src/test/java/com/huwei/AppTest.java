package com.huwei;

import com.huwei.api.Api1;
import com.huwei.api.ITestApi;
import com.huwei.model.UserDTO;
import com.huwei.service.Service1;
import com.huwei.service.Service2;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * Created by huwei on 2017/6/14.
 */
public class AppTest extends BaseTest {

    @Autowired
    Service1 service1;
    @Autowired
    Service2 service2;
    @Autowired
    Api1 api1;

    @Autowired
    ITestApi testApi;

    @Test
    public void test() {
        UserDTO user = new UserDTO();
        testApi.insertUser(user, UUID.randomUUID().toString());
    }
}
