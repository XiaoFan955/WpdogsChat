package com.fan.wpdogschat.common.common.event.listener;


import com.fan.wpdogschat.common.common.event.UserOnlineEvent;
import com.fan.wpdogschat.common.user.dao.UserDao;
import com.fan.wpdogschat.common.user.domain.entity.User;
import com.fan.wpdogschat.common.user.domain.enums.UserActiveStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


@Component
public class UserOnlineListener {

//    @Autowired
//    private IpService ipService;
//    @Autowired
//    private UserDao userDao;
//
//    @Async
//    @TransactionalEventListener(classes = UserOnlineEvent.class, phase = TransactionPhase.AFTER_COMMIT,fallbackExecution=true)
//    public void saveDB(UserOnlineEvent event) {
//        User user = event.getUser();
//        User update = new User();
//        update.setId(user.getId());
//        update.setLastOptTime(user.getLastOptTime());
//        update.setIpInfo(user.getIpInfo());
//        update.setActiveStatus(UserActiveStatusEnum.ONLINE.getStatus());
//        userDao.updateById(update);
//        //用户ip详情的解析
//        ipService.refreshIpDetailAsync(user.getId());
//    }
}
