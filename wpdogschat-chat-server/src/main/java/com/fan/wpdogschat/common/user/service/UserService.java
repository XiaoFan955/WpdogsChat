package com.fan.wpdogschat.common.user.service;

import com.fan.wpdogschat.common.user.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author fan
 * @since 2024-05-03
 */
public interface UserService{

    Long register(User insertUser);
}
