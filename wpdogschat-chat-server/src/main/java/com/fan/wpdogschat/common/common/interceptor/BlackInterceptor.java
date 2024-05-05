package com.fan.wpdogschat.common.common.interceptor;

import cn.hutool.core.collection.CollectionUtil;
import com.fan.wpdogschat.common.common.domain.dto.RequestInfo;
import com.fan.wpdogschat.common.common.exception.HttpErrorEnum;
import com.fan.wpdogschat.common.common.utils.RequestHolder;
import com.fan.wpdogschat.common.user.service.cache.UserCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Component
public class BlackInterceptor implements HandlerInterceptor {
    @Autowired
    private UserCache userCache;

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Map<Integer, Set<String>> blackMap = userCache.getBlackMap();
//        RequestInfo requestInfo = RequestHolder.get();
//        if (inBlackList(requestInfo.getUid(), blackMap.get(BlackTypeEnum.UID.getType()))) {
//            HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
//            return false;
//        }
//        if (inBlackList(requestInfo.getIp(), blackMap.get(BlackTypeEnum.IP.getType()))) {
//            HttpErrorEnum.ACCESS_DENIED.sendHttpError(response);
//            return false;
//        }
//        return true;
//    }

    private boolean inBlackList(Object target, Set<String> set) {
        if (Objects.isNull(target) || CollectionUtil.isEmpty(set)) {
            return false;
        }
        return set.contains(target.toString());
    }
}
