package com.fan.wpdogschat.common.websocket.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.fan.wpdogschat.common.websocket.domain.dto.WSChannelExtraDTO;
import com.fan.wpdogschat.common.websocket.domain.enums.WSRespTypeEnum;
import com.fan.wpdogschat.common.websocket.domain.vo.req.WSBaseReq;
import com.fan.wpdogschat.common.websocket.domain.vo.resp.WSBaseResp;
import com.fan.wpdogschat.common.websocket.domain.vo.resp.WSLoginUrl;
import com.fan.wpdogschat.common.websocket.service.WebSocketService;
import com.fan.wpdogschat.common.websocket.service.adapter.WebSocketAdapter;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.SneakyThrows;
import me.chanjar.weixin.common.service.WxService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理websocket逻辑
 */
@Service
public class WebSocketServiceImpl implements WebSocketService {

    @Autowired
    private WxMpService wxMpService;

    /**
     * ONLINE_WS_MAP 管理所有用户的连接（登录态/游客）
     */
    private static final ConcurrentHashMap<Channel, WSChannelExtraDTO> ONLINE_WS_MAP = new ConcurrentHashMap<>();

    /**
     * DURATION WAIT_LOGIN_MAP中连接的过期时间
     */
    public static final Duration DURATION = Duration.ofHours(1);
    /**
     * WAIT_LOGIN_MAP 管理所有等待连接，并设置过期时间，避免OOM
     */
    private static final Cache<Integer,Channel> WAIT_LOGIN_MAP = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(DURATION)
            .build();

    /**
     * 保存用户连接，认证时再记录id
     * @param channel 用户连接
     */
    @Override
    public void connect(Channel channel) {
        ONLINE_WS_MAP.put(channel,new WSChannelExtraDTO());
    }

    /**
     * 处理扫码登陆逻辑
     * @param channel
     */
    @SneakyThrows
    @Override
    public void handleLoginReq(Channel channel) {
        // 生成随机码
        Integer code = generateLoginCode(channel);
        // 找微信申请带参二维码
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(code, (int) DURATION.getSeconds());
        // 把码推送给前端
        sendMsg(channel, WebSocketAdapter.buildResp(wxMpQrCodeTicket));
    }

    /**
     * 推送消息给前端
     * @param channel
     * @param resp
     */
    private void sendMsg(Channel channel, WSBaseResp<?> resp) {
        channel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(resp)));
    }

    /**
     * 生成随机码，并将channel和随机码绑定
     * @param channel
     * @return
     */
    private Integer generateLoginCode(Channel channel) {
        Integer code;
        do{
            code= RandomUtil.randomInt(Integer.MAX_VALUE);
            //putIfAbsent插入key存在时会返回key对应的对象，否则返回空，返回空的时候说明可以成功插入
        }while(Objects.nonNull(WAIT_LOGIN_MAP.asMap().putIfAbsent(code,channel)));
        return code;
    }
}
