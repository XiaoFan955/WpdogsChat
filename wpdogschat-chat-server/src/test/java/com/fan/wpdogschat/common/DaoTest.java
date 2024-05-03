package com.fan.wpdogschat.common;

import com.fan.wpdogschat.common.user.dao.UserDao;
import com.fan.wpdogschat.common.user.domain.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DaoTest {
    //需要添加spring环境才能注入
    @Autowired
    private UserDao userDao;
    @Test
    public void test(){
        User byId = userDao.getById(1);
    }
}
