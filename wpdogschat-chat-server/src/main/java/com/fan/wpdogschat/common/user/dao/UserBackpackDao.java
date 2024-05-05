package com.fan.wpdogschat.common.user.dao;

import com.fan.wpdogschat.common.common.domain.enums.YesOrNoEnum;
import com.fan.wpdogschat.common.user.domain.entity.UserBackpack;
import com.fan.wpdogschat.common.user.mapper.UserBackpackMapper;
import com.fan.wpdogschat.common.user.service.IUserBackpackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户背包表 服务实现类
 * </p>
 *
 * @author fan
 * @since 2024-05-04
 */
@Service
public class UserBackpackDao extends ServiceImpl<UserBackpackMapper, UserBackpack>{
    public Integer getCountByValidItemId(Long uid, Long itemId) {
        return lambdaQuery()
                .eq(UserBackpack::getUid, uid)
                .eq(UserBackpack::getItemId, itemId)
                .eq(UserBackpack::getStatus, YesOrNoEnum.NO.getStatus())
                .count();
    }

    /**
     * 获取最老的一个可用的物品
     * @param uid
     * @param itemId
     * @return
     */
    public UserBackpack getFirstValidItem(Long uid, Long itemId) {
        return lambdaQuery()
                .eq(UserBackpack::getUid, uid)
                .eq(UserBackpack::getItemId, itemId)
                .eq(UserBackpack::getStatus, YesOrNoEnum.NO.getStatus())
                .orderByAsc(UserBackpack::getId)
                .last("limit 1")
                .one();
    }

    /**
     * 使用物品，数据库级乐观更新
     * @param item
     * @return
     */
    public boolean useItem(UserBackpack item) {
        return lambdaUpdate()
                .eq(UserBackpack::getId, item.getId())
                .eq(UserBackpack::getStatus, YesOrNoEnum.NO.getStatus())
                .set(UserBackpack::getStatus, YesOrNoEnum.YES.getStatus())
                .update();
    }

    /**
     * 获取用户在itemId中的所有物品
     * @param uid
     * @param itemId
     * @return
     */
    public List<UserBackpack> getByItemIds(Long uid, List<Long> itemId) {
        return lambdaQuery()
                .eq(UserBackpack::getUid, uid)
                .eq(UserBackpack::getStatus, YesOrNoEnum.NO.getStatus())
                .in(UserBackpack::getItemId, itemId)
                .list();
    }

    public UserBackpack getByIdempotent(String idempotent) {
        return lambdaQuery()
                .eq(UserBackpack::getIdempotent, idempotent)
                .one();
    }
}
