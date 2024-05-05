package com.fan.wpdogschat.common.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 全局异常枚举类型
 */
@AllArgsConstructor
@Getter
public enum CommonErrorEnum implements ErrorEnum {
    /**
     * 这里由于AssertUtil使用java.text.MessageFormat来格式化参数，占位符要求是从0开始的索引
     */
    BUSINESS_ERROR(0, "{0}"),
    SYSTEM_ERROR(-1, "系统出小差了，请稍后再试~"),
    PARAM_INVALID(-2, "参数校验失败"),
    LOCK_LIMIT(-3, "请求太频繁了，请稍后再试~"),
    ;

    private final Integer code;
    private final String msg;

    @Override
    public Integer getErrorCode() {
        return code;
    }

    @Override
    public String getErrorMsg() {
        return msg;
    }
}
