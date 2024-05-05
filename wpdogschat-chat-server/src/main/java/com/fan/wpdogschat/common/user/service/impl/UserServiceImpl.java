package com.fan.wpdogschat.common.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fan.wpdogschat.common.common.annotation.RedissonLock;
import com.fan.wpdogschat.common.common.event.UserBlackEvent;
import com.fan.wpdogschat.common.common.event.UserRegisterEvent;
import com.fan.wpdogschat.common.common.utils.AssertUtil;
import com.fan.wpdogschat.common.user.dao.ItemConfigDao;
import com.fan.wpdogschat.common.user.dao.UserBackpackDao;
import com.fan.wpdogschat.common.user.dao.UserDao;
import com.fan.wpdogschat.common.user.domain.entity.ItemConfig;
import com.fan.wpdogschat.common.user.domain.entity.User;
import com.fan.wpdogschat.common.user.domain.entity.UserBackpack;
import com.fan.wpdogschat.common.user.domain.enums.BlackTypeEnum;
import com.fan.wpdogschat.common.user.domain.enums.ItemEnum;
import com.fan.wpdogschat.common.user.domain.enums.ItemTypeEnum;
import com.fan.wpdogschat.common.user.domain.vo.req.BlackReq;
import com.fan.wpdogschat.common.user.domain.vo.resp.BadgeResp;
import com.fan.wpdogschat.common.user.domain.vo.resp.UserInfoResp;
import com.fan.wpdogschat.common.user.service.UserService;
import com.fan.wpdogschat.common.user.service.adapter.UserAdapter;
import com.fan.wpdogschat.common.user.service.cache.ItemCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserBackpackDao userBackpackDao;
    @Autowired
    private ItemCache itemCache;
    @Autowired
    private ItemConfigDao itemConfigDao;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
//    @Autowired
//    private BlackDao blackDao;


    /**
     * 注册
     * @param insert
     * @return
     */
    @Override
    @Transactional
    public Long register(User insert) {
        userDao.save(insert);
        //发送物品
        //发布用户注册的事件
        applicationEventPublisher.publishEvent(new UserRegisterEvent(this, insert));
        return insert.getId();
    }

    /**
     * 获取用户信息
     * @param uid
     * @return
     */
    @Override
    public UserInfoResp getUserInfo(Long uid) {
        User user = userDao.getById(uid);
        Integer modifyNameCount = userBackpackDao.getCountByValidItemId(uid, ItemEnum.MODIFY_NAME_CARD.getId());
        return UserAdapter.buildUserInfo(user, modifyNameCount);
    }

    /**
     * 修改名字
     * @param uid
     * @param name
     */
    @Override
    @Transactional(rollbackFor = Exception.class)//事务，对所有异常生效
    @RedissonLock(key = "#uid")
    public void modifyName(Long uid, String name) {
        User oldUser = userDao.getByName(name);
        AssertUtil.isEmpty(oldUser, "名字已存在");
        UserBackpack modifyNameItem = userBackpackDao.getFirstValidItem(uid, ItemEnum.MODIFY_NAME_CARD.getId());
        AssertUtil.isNotEmpty(modifyNameItem, "改名卡不足");
        //使用改名卡
        boolean success = userBackpackDao.useItem(modifyNameItem);
        if (success) {
            //改名
            userDao.modifyName(uid, name);
        }
    }

    /**
     * 获取徽章列表
     * @param uid
     * @return
     */
    @Override
    public List<BadgeResp> badges(Long uid) {
        //从缓存，根据类型查询所有徽章
        List<ItemConfig> itemConfigs = itemCache.getByType(ItemTypeEnum.BADGE.getType());
        //查询用户拥有徽章，查用户背包
        List<UserBackpack> backpacks = userBackpackDao.getByItemIds(uid, itemConfigs.stream().map(ItemConfig::getId).collect(Collectors.toList()));
        //查询用户佩戴的徽章
        User user = userDao.getById(uid);
        return UserAdapter.buildBadgeResp(itemConfigs, backpacks, user);
    }

    /**
     * 佩戴徽章
     * @param uid
     * @param itemId
     */
    @Override
    public void wearingBadge(Long uid, Long itemId) {
        //确保有徽章
        UserBackpack firstValidItem = userBackpackDao.getFirstValidItem(uid, itemId);
        AssertUtil.isNotEmpty(firstValidItem, "您还没有这个徽章，快去获得吧");
        //确保这个物品是徽章
        ItemConfig itemConfig = itemConfigDao.getById(firstValidItem.getItemId());
        AssertUtil.equal(itemConfig.getType(), ItemTypeEnum.BADGE.getType(), "只有徽章才能佩戴");
        userDao.wearingBadge(uid, itemId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void black(BlackReq req) {
//        Long uid = req.getUid();
//        Black user = new Black();
//        user.setType(BlackTypeEnum.UID.getType());
//        user.setTarget(uid.toString());
//        blackDao.save(user);
//        User byId = userDao.getById(uid);
//        blackIp(Optional.ofNullable(byId.getIpInfo()).map(IpInfo::getCreateIp).orElse(null));
//        blackIp(Optional.ofNullable(byId.getIpInfo()).map(IpInfo::getUpdateIp).orElse(null));
//        applicationEventPublisher.publishEvent(new UserBlackEvent(this, byId));
    }

    private void blackIp(String ip) {
//        if (StringUtils.isBlank(ip)) {
//            return;
//        }
//        try {
//            Black insert = new Black();
//            insert.setType(BlackTypeEnum.IP.getType());
//            insert.setTarget(ip);
//            blackDao.save(insert);
//        } catch (Exception e) {
//
//        }

    }
}
