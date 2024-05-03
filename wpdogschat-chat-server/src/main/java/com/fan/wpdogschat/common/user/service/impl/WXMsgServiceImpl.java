package com.fan.wpdogschat.common.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.fan.wpdogschat.common.user.dao.UserDao;
import com.fan.wpdogschat.common.user.domain.entity.User;
import com.fan.wpdogschat.common.user.service.UserService;
import com.fan.wpdogschat.common.user.service.WXMsgService;
import com.fan.wpdogschat.common.user.service.adapter.TextBuilder;
import com.fan.wpdogschat.common.user.service.adapter.UserAdapter;
import com.fan.wpdogschat.common.websocket.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


@Service
@Slf4j
public class WXMsgServiceImpl implements WXMsgService {

    /**
     * 保存openid和登陆code的关系
     */
    private static final ConcurrentHashMap<String,Integer>WAIT_AUTHORIZED_MAP = new ConcurrentHashMap<>();

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WebSocketService webSocketService;

    /**
     * callback 回调接口
     */
    @Value("${wx.mp.callback}")
    private String callback;
    /**
     * URL 授权链接
     */
    private static final String URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";


    @Override
    public WxMpXmlOutMessage scan(WxMpXmlMessage wxMpXmlMessage) {
        String openId = wxMpXmlMessage.getFromUser();
        Integer code = getEventKey(wxMpXmlMessage);
        if(Objects.isNull(code)){
            return null;
        }
        User user = userDao.getByOpenId(openId);
        boolean isRegistered = Objects.nonNull(user);
        boolean isAuthorized = isRegistered && StrUtil.isNotBlank(user.getAvatar());
        //用户已注册且已授权
        if(isRegistered&&isAuthorized){
            //todo 登陆成功，通过code找到channel，推送消息
            webSocketService.scanLoginSuccess(code,user.getId());
        }
        //用户未注册
        if(!isRegistered){
            User insertUser = UserAdapter.buildUserSave(openId);
            userService.register(insertUser);
        }
        // 返回前端登陆中的状态
        webSocketService.waitAuthorize(code);
        // 推送链接让用户点击授权，授权逻辑在callback回调里面
        WAIT_AUTHORIZED_MAP.put(openId,code);
        String authorizeUrl = String.format(URL,wxMpService.getWxMpConfigStorage().getAppId(), URLEncoder.encode(callback+"/wx/portal/public/callBack"));
        return TextBuilder.build("请点击: <a href=\""+ authorizeUrl +"\">登陆</a> ",wxMpXmlMessage);

    }

    @Override
    public void authorize(WxOAuth2UserInfo userInfo) {
        String openId = userInfo.getOpenid();
        User user = userDao.getByOpenId(openId);
        if(StrUtil.isBlank(user.getAvatar())){
            fillUserInfo(user.getId(),userInfo);
        }
        //通过code找到channel进行登陆
        Integer code = WAIT_AUTHORIZED_MAP.remove(openId);
        webSocketService.scanLoginSuccess(code,user.getId());
    }

    private void fillUserInfo(Long uid, WxOAuth2UserInfo userInfo) {
       User user = UserAdapter.buildAuthorizedUser(uid,userInfo);
       userDao.updateById(user);
    }

    private Integer getEventKey(WxMpXmlMessage wxMpXmlMessage) {
        try{
            String eventKey = wxMpXmlMessage.getEventKey();
            String code = eventKey.replace("qrscene_", "");
            return Integer.parseInt(code);
        }catch(Exception e){
            log.error("getEventKey error eventKey:{}",wxMpXmlMessage.getEventKey(),e);
            return null;
        }


    }
}
