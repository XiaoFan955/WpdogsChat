package com.fan.wpdogschat.common.user.service.handler;

import com.fan.wpdogschat.common.user.service.adapter.TextBuilder;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Value;

import java.net.URLEncoder;
import java.util.Map;

/**
 * 已关注用户扫码
 */
@Component
public class ScanHandler extends AbstractHandler {

    @Value("${wx.mp.callback}")
    private String callback;
    private static final String URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

    /**
     *
     * @param wxMpXmlMessage 包含扫码进来的用户信息
     * @param map
     * @param wxMpService
     * @param wxSessionManager
     * @return 返回为测试号中发给用户的信息
     * @throws WxErrorException
     */
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map,
                                    WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        String code = wxMpXmlMessage.getEventKey();
        String openId = wxMpXmlMessage.getFromUser();
        // 扫码事件处理，给用户发送一个点击登录的链接
        String authorizeUrl = String.format(URL,wxMpService.getWxMpConfigStorage().getAppId(), URLEncoder.encode(callback+"/wx/portal/public/callBack"));
        return TextBuilder.build("请点击: <a href=\""+ authorizeUrl +"\">登陆</a> ",wxMpXmlMessage);


    }

}
