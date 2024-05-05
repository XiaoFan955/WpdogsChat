package com.fan.wpdogschat.common.user.dao;

import com.fan.wpdogschat.common.user.domain.entity.ItemConfig;
import com.fan.wpdogschat.common.user.mapper.ItemConfigMapper;
import com.fan.wpdogschat.common.user.service.IItemConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 功能物品配置表 服务实现类
 * </p>
 *
 * @author fan
 * @since 2024-05-04
 */
@Service
public class ItemConfigDao extends ServiceImpl<ItemConfigMapper, ItemConfig>{
    public List<ItemConfig> getByType(Integer itemType) {
        return lambdaQuery()
                .eq(ItemConfig::getType, itemType)
                .list();
    }
}
