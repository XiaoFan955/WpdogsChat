package com.fan.wpdogschat.common.common.exception;

import cn.hutool.http.ContentType;
import com.fan.wpdogschat.common.common.domain.vo.resp.ApiResult;
import com.fan.wpdogschat.common.common.utils.JsonUtils;
import com.google.common.base.Charsets;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 配置HTTP错误情况，并向前端返回错误
 */
@AllArgsConstructor
public enum HttpErrorEnum {
    ACCESS_DENIED(401, "登录失效请重新登录");

    private Integer httpCode;
    private String desc;

    public void sendHttpError(HttpServletResponse response) throws IOException {
        response.setStatus(httpCode);
        response.setContentType(ContentType.JSON.toString(Charsets.UTF_8));
        response.getWriter().write(JsonUtils.toStr(ApiResult.fail(httpCode,desc)));
    }
}
