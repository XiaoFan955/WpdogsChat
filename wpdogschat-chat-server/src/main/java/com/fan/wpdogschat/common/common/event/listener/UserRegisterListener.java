package com.fan.wpdogschat.common.common.event.listener;

import com.fan.wpdogschat.common.common.event.UserRegisterEvent;
import com.fan.wpdogschat.common.user.dao.UserDao;
import com.fan.wpdogschat.common.user.domain.entity.User;
import com.fan.wpdogschat.common.user.domain.enums.IdempotentEnum;
import com.fan.wpdogschat.common.user.domain.enums.ItemEnum;
import com.fan.wpdogschat.common.user.service.IUserBackpackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


@Component
public class UserRegisterListener {

    @Autowired
    private IUserBackpackService userBackpackService;
    @Autowired
    private UserDao userDao;

    /**
     * 监听注册事件，注册事务提交后执行发送改名卡
     * @param event
     */
    @Async
    @TransactionalEventListener(classes = UserRegisterEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void sendCard(UserRegisterEvent event) {
        User user = event.getUser();
        userBackpackService.acquireItem(user.getId(), ItemEnum.MODIFY_NAME_CARD.getId(), IdempotentEnum.UID, user.getId().toString());
    }

    @Async
    @TransactionalEventListener(classes = UserRegisterEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void sendBadge(UserRegisterEvent event) {
        //前100名的注册徽章
        User user = event.getUser();
        int registeredCount = userDao.count();
        if (registeredCount < 10) {
            userBackpackService.acquireItem(user.getId(), ItemEnum.REG_TOP10_BADGE.getId(), IdempotentEnum.UID, user.getId().toString());
        }
//        else if (registeredCount < 100) {
//            userBackpackService.acquireItem(user.getId(), ItemEnum.REG_TOP100_BADGE.getId(), IdempotentEnum.UID, user.getId().toString());
//        }

    }
}
