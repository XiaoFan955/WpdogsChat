package com.fan.wpdogschat.common.websocket.domain.vo.req;

import lombok.Data;

@Data
public class WSBaseReq {
    /**
     * @see com.fan.wpdogschat.common.websocket.domain.enums.WSReqTypeEnum
     */
    private Integer type;
    private String data;
}
