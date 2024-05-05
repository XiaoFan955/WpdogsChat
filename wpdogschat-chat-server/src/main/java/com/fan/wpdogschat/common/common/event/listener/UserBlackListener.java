package com.fan.wpdogschat.common.common.event.listener;

import com.fan.wpdogschat.common.websocket.service.adapter.WebSocketAdapter;
import com.fan.wpdogschat.common.common.event.UserBlackEvent;
import com.fan.wpdogschat.common.user.dao.UserDao;
import com.fan.wpdogschat.common.user.domain.entity.User;
import com.fan.wpdogschat.common.user.service.IUserBackpackService;
import com.fan.wpdogschat.common.user.service.cache.UserCache;
import com.fan.wpdogschat.common.websocket.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


@Component
public class UserBlackListener {

    @Autowired
    private IUserBackpackService userBackpackService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private WebSocketService webSocketService;
    @Autowired
    private UserCache userCache;

//    @Async
//    @TransactionalEventListener(classes = UserBlackEvent.class, phase = TransactionPhase.AFTER_COMMIT)
//    public void sendMsg(UserBlackEvent event) {
//        User user = event.getUser();
//        webSocketService.sendMsgToAll(WebSocketAdapter.buildBlack(user));
//    }

//    @Async
////    @TransactionalEventListener(classes = UserBlackEvent.class, phase = TransactionPhase.AFTER_COMMIT)
////    public void changeUserStatus(UserBlackEvent event) {
////        userDao.invalidUid(event.getUser().getId());
////    }
////
////    @Async
////    @TransactionalEventListener(classes = UserBlackEvent.class, phase = TransactionPhase.AFTER_COMMIT)
////    public void evictCache(UserBlackEvent event) {
////        userCache.evictBlackMap();
////    }

}
