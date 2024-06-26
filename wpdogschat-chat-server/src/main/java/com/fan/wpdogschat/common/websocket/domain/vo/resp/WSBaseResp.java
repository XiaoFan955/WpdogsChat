package com.fan.wpdogschat.common.websocket.domain.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WSBaseResp<T> {
    /**
     * @see com.fan.wpdogschat.common.websocket.domain.enums.WSRespTypeEnum
     */
    private Integer type;
    private T data;
}
