package com.fan.wpdogschat.common.websocket.service;

import io.netty.channel.Channel;

public interface WebSocketService {
    void connect(Channel channel);

    void handleLoginReq(Channel channel);
}
