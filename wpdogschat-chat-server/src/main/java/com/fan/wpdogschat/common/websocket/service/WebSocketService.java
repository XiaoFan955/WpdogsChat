package com.fan.wpdogschat.common.websocket.service;

import com.fan.wpdogschat.common.websocket.domain.vo.resp.WSBaseResp;
import io.netty.channel.Channel;

public interface WebSocketService {
    void connect(Channel channel);

    void handleLoginReq(Channel channel);

    void offLine(Channel channel);

    void scanLoginSuccess(Integer code, Long id);

    void waitAuthorize(Integer code);

    void authorize(Channel channel, String token);

    void sendMsgToAll(WSBaseResp<?> msg);
}
