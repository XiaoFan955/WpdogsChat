package com.fan.wpdogschat.common.websocket.service.adapter;

import com.fan.wpdogschat.common.websocket.domain.enums.WSRespTypeEnum;
import com.fan.wpdogschat.common.websocket.domain.vo.resp.WSBaseResp;
import com.fan.wpdogschat.common.websocket.domain.vo.resp.WSLoginUrl;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

public class WebSocketAdapter {
    public static WSBaseResp<?> buildResp(WxMpQrCodeTicket wxMpQrCodeTicket) {
        WSBaseResp<WSLoginUrl> resp = new WSBaseResp<>();
        resp.setType(WSRespTypeEnum.LOGIN_URL.getType());
        resp.setData(new WSLoginUrl(wxMpQrCodeTicket.getUrl()));
        return resp;
    }
}
