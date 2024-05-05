package com.fan.wpdogschat.common.common.utils;

import com.fan.wpdogschat.common.common.domain.dto.RequestInfo;

/**
 * 保存请求的上下文，存在ThreadLocal中，类似ThreadLocalUtil
 */
public class RequestHolder {
    private static final ThreadLocal<RequestInfo> threadLocal = new ThreadLocal<RequestInfo>();

    public static void set(RequestInfo requestInfo) {
        threadLocal.set(requestInfo);
    }

    public static RequestInfo get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
