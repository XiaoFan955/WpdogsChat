package com.fan.wpdogschat.common.user.dao;

import com.fan.wpdogschat.common.common.domain.enums.YesOrNoEnum;
import com.fan.wpdogschat.common.user.domain.entity.User;
import com.fan.wpdogschat.common.user.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author fan
 * @since 2024-05-03
 */
@Service
public class UserDao extends ServiceImpl<UserMapper, User> {

    public User getByOpenId(String openId) {
        return lambdaQuery()
                .eq(User::getOpenId, openId)
                .one();
    }

    public User getByName(String name) {
        return lambdaQuery().eq(User::getName, name).one();
    }

    public void modifyName(Long uid, String name) {
        lambdaUpdate()
                .eq(User::getId, uid)
                .set(User::getName, name)
                .update();
    }

    public void wearingBadge(Long uid, Long itemId) {
        lambdaUpdate()
                .eq(User::getId, uid)
                .set(User::getItemId, itemId)
                .update();
    }

    public void invalidUid(Long id) {
        lambdaUpdate()
                .eq(User::getId, id)
                .set(User::getStatus, YesOrNoEnum.YES.getStatus())
                .update();
    }
}
