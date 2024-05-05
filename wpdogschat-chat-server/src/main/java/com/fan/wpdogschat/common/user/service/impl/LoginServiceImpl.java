package com.fan.wpdogschat.common.user.service.impl;

import com.fan.wpdogschat.common.common.constant.RedisKey;
import com.fan.wpdogschat.common.common.utils.JwtUtils;
import com.fan.wpdogschat.common.common.utils.RedisUtils;
import com.fan.wpdogschat.common.user.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    public static final int TOKEN_EXPIRE_DAYS = 3;
    public static final int TOKEN_RENEWAL_DAYS = 1;
    @Autowired
    private JwtUtils jwtUtils;
    @Override
    public void renewalTokenIfNecessary(String token) {
        Long uid = getValidUid(token);
        String userTokenKey = getUserTokenKey(uid);
        Long expireDays = RedisUtils.getExpire(userTokenKey,TimeUnit.DAYS);
        if(expireDays==-2){//不存在的key
            return;
        }
        if(expireDays< TOKEN_RENEWAL_DAYS){//小于一天就续期
            RedisUtils.expire(getUserTokenKey(uid), TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);
        }
    }

    /**
     * 登陆创建token保存并返回
     * @param uid
     * @return
     */
    @Override
    public String login(Long uid) {
        String token = jwtUtils.createToken(uid);
        RedisUtils.set(getUserTokenKey(uid),token, TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);

        return token;
    }

    /**
     * 客户端发送token的校验
     * @param token
     * @return
     */
    @Override
    public Long getValidUid(String token) {
        Long uid=jwtUtils.getUidOrNull(token);
        if(Objects.isNull(uid)){
            return null;
        }
        String oldToken = RedisUtils.getStr(getUserTokenKey(uid));
        return Objects.equals(oldToken,token)?uid:null;
    }
    private String getUserTokenKey(Long uid){
        return RedisKey.getKey(RedisKey.USER_TOKEN_STRING,uid);
    }
}
