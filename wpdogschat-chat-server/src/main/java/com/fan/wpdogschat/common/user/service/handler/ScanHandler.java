package com.fan.wpdogschat.common.user.service.handler;

import com.fan.wpdogschat.common.user.service.WXMsgService;
import com.fan.wpdogschat.common.user.service.adapter.TextBuilder;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Value;

import java.net.URLEncoder;
import java.util.Map;

/**
 * 已关注用户扫码
 */
@Component
public class ScanHandler extends AbstractHandler {


    @Autowired
    @Lazy
    private WXMsgService wxMsgService;

    /**
     * @param wxMpXmlMessage 包含扫码进来的用户信息
     */
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map,
                                    WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        return wxMsgService.scan(wxMpXmlMessage);
    }

}
