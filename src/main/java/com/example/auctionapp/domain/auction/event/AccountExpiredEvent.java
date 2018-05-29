package com.example.auctionapp.domain.auction.event;

import com.example.auctionapp.domain.user.User;
import org.springframework.context.ApplicationEvent;

public class AccountExpiredEvent extends ApplicationEvent {
    public AccountExpiredEvent(User source) {
        super(source);
    }

    @Override
    public User getSource() {
        return (User) source;
    }
}
