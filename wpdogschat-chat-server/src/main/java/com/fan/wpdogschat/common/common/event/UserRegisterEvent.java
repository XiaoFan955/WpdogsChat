package com.fan.wpdogschat.common.common.event;

import com.fan.wpdogschat.common.user.domain.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class UserRegisterEvent extends ApplicationEvent {
    private User user;

    public UserRegisterEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
