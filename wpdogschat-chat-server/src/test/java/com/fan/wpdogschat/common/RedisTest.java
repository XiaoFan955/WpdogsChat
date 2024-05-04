package com.fan.wpdogschat.common;

import com.fan.wpdogschat.common.common.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {
    @Test
    public void redisTest(){
        RedisUtils.set("name","xiaofan");
        String name = (String) RedisUtils.get("name");
        System.out.println(name);
    }
}
