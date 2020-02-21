package com.hzy;

import com.hzy.pojo.User;
import com.hzy.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Shiro03SpringbootApplicationTests {

    @Autowired
    private UserService userService;
    @Test
    void contextLoads() {
        User user = userService.queryUserByName("root");
        System.out.println(user);
    }

}
