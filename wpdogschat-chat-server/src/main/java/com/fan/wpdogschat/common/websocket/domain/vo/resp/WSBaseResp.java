package com.fan.wpdogschat.common.websocket.domain.vo.resp;

import lombok.Data;

@Data
public class WSBaseResp<T> {
    /**
     * @see com.fan.wpdogschat.common.websocket.domain.enums.WSRespTypeEnum
     */
    private Integer type;
    private T data;
}
