package com.fan.wpdogschat.common.common.aspect;

import com.fan.wpdogschat.common.common.annotation.RedissonLock;
import com.fan.wpdogschat.common.common.service.LockService;
import com.fan.wpdogschat.common.common.utils.SpElUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Component
@Aspect
@Order(0)//确保比事务注解先执行，分布式锁在事务外
public class RedissonLockAspect {
    @Autowired
    private LockService lockService;

    @Around("@annotation(redissonLock)")
    public Object around(ProceedingJoinPoint joinPoint, RedissonLock redissonLock) throws Throwable {
        /**
         * 通过joinPoint获取方法对象
         * 解析redissonLock注解中的el表达式prefixKey，获取方法全限定名
         * 解析redissonLock注解中的el表达式key，获取对应的值
         * 前两个值组合成为加锁的key
         */
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String prefix = StringUtils.isBlank(redissonLock.prefixKey()) ? SpElUtils.getMethodKey(method) : redissonLock.prefixKey();
        String key = SpElUtils.parseSpEl(method, joinPoint.getArgs(), redissonLock.key());
        return lockService.executeWithLock(prefix + ":" + key, redissonLock.waitTime(), redissonLock.unit(), joinPoint::proceed);
    }
}
