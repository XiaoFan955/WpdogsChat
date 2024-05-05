package com.fan.wpdogschat.common.user.service.cache;

import com.fan.wpdogschat.common.user.dao.ItemConfigDao;
import com.fan.wpdogschat.common.user.domain.entity.ItemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ItemCache {

    @Autowired
    private ItemConfigDao itemConfigDao;

    /**
     * 获取缓存
     * @param itemType
     * @return
     */
    @Cacheable(cacheNames = "item",key = "'itemsByType:'+#itemType")
    public List<ItemConfig> getByType(Integer itemType) {
        return itemConfigDao.getByType(itemType);
    }

    /**
     * 清空缓存
     * @param itemType
     */
    @CacheEvict(cacheNames = "item",key = "'itemsByType:'+#itemType")
    public void evictByType(Integer itemType) {
    }
}
