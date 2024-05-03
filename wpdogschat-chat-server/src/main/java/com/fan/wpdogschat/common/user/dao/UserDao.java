package com.fan.wpdogschat.common.user.dao;

import com.fan.wpdogschat.common.user.domain.entity.User;
import com.fan.wpdogschat.common.user.mapper.UserMapper;
import com.fan.wpdogschat.common.user.service.IUserService;
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

}
