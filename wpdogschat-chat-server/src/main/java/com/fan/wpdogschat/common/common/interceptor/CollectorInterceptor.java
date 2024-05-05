package com.fan.wpdogschat.common.common.interceptor;

import cn.hutool.extra.servlet.ServletUtil;
import com.fan.wpdogschat.common.common.domain.dto.RequestInfo;
import com.fan.wpdogschat.common.common.utils.RequestHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


@Component
public class CollectorInterceptor implements HandlerInterceptor {
    /**
     * 收集用户信息，全局使用
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long uid = Optional.ofNullable(request.getAttribute(TokenInterceptor.UID)).map(Object::toString).map(Long::parseLong).orElse(null);
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setIp(ServletUtil.getClientIP(request));
        requestInfo.setUid(uid);
        RequestHolder.set(requestInfo);
        return true;
    }

    /**
     * 结束时移除，避免OOM
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        RequestHolder.remove();
    }
}
