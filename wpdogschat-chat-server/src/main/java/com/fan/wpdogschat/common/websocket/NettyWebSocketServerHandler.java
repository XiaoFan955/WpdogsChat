package com.fan.wpdogschat.common.websocket;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.fan.wpdogschat.common.websocket.domain.enums.WSReqTypeEnum;
import com.fan.wpdogschat.common.websocket.domain.vo.req.WSBaseReq;
import com.fan.wpdogschat.common.websocket.service.WebSocketService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.beans.factory.annotation.Autowired;

@ChannelHandler.Sharable
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    private WebSocketService webSocketService;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        webSocketService = SpringUtil.getBean(WebSocketService.class);
        //保存连接
        webSocketService.connect(ctx.channel());
    }

    /**
     * 客户端主动下线
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        userOffLine(ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof WebSocketServerProtocolHandler.HandshakeComplete){
            //握手完成
            System.out.println("握手完成");
        }else if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if(event.state()== IdleState.READER_IDLE){
                //读空闲
                System.out.println("读空闲");
                //todo 用户下线
                userOffLine(ctx.channel());
                //连接关闭

            }
        }
    }

    /**
     * 用户下线统一处理
     * @param channel
     */
    private void userOffLine(Channel channel) {
        webSocketService.offLine(channel);
        channel.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String text=msg.text();
        WSBaseReq wsBaseReq = JSONUtil.toBean(text, WSBaseReq.class);
        switch (WSReqTypeEnum.of(wsBaseReq.getType())) {
            case LOGIN:
                webSocketService.handleLoginReq(ctx.channel());
                break;
            case HEARTBEAT:
                break;
            case AUTHORIZE:
                webSocketService.authorize(ctx.channel(),wsBaseReq.getData());
                break;
        }
    }
}
