package com.fan.wpdogschat.common.user.service.impl;

import com.fan.wpdogschat.common.common.annotation.RedissonLock;
import com.fan.wpdogschat.common.common.domain.enums.YesOrNoEnum;
import com.fan.wpdogschat.common.common.service.LockService;
import com.fan.wpdogschat.common.user.dao.UserBackpackDao;
import com.fan.wpdogschat.common.user.domain.entity.UserBackpack;
import com.fan.wpdogschat.common.user.domain.enums.IdempotentEnum;
import com.fan.wpdogschat.common.user.service.IUserBackpackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class UserBackpackServiceImpl implements IUserBackpackService {
    @Autowired
    private LockService lockService;
    @Autowired
    private UserBackpackDao userBackpackDao;
    @Autowired
    @Lazy
    private UserBackpackServiceImpl userBackpackService;//同类调用，自己注入自己

    /**
     * 发放物品
     * @param uid            用户id
     * @param itemId         物品id
     * @param idempotentEnum 幂等类型
     * @param businessId     幂等唯一标识
     */
    @Override
    public void acquireItem(Long uid, Long itemId, IdempotentEnum idempotentEnum, String businessId) {
        String idempotent = getIdempotent(itemId, idempotentEnum, businessId);
        userBackpackService.doAcquireItem(uid, itemId, idempotent);
    }

    /**
     * 发放物品逻辑
     * @param uid
     * @param itemId
     * @param idempotent
     */
    @RedissonLock(key = "#idempotent", waitTime = 5000)
    public void doAcquireItem(Long uid, Long itemId, String idempotent) {
        UserBackpack userBackpack = userBackpackDao.getByIdempotent(idempotent);
        if (Objects.nonNull(userBackpack)) {
            return;
        }
        //发放物品
        UserBackpack insert = UserBackpack.builder()
                .uid(uid)
                .itemId(itemId)
                .status(YesOrNoEnum.NO.getStatus())
                .idempotent(idempotent)
                .build();
        userBackpackDao.save(insert);
    }

    /**
     * 组装幂等键
     * @param itemId
     * @param idempotentEnum
     * @param businessId
     * @return
     */
    private String getIdempotent(Long itemId, IdempotentEnum idempotentEnum, String businessId) {
        return String.format("%d_%d_%s", itemId, idempotentEnum.getType(), businessId);
    }
}
