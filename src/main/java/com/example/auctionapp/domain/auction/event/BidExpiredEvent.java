package com.example.auctionapp.domain.auction.event;

import com.example.auctionapp.domain.auction.bid.Bid;
import org.springframework.context.ApplicationEvent;

public class BidExpiredEvent extends ApplicationEvent {

    public BidExpiredEvent(Bid source) {
        super(source);
    }

    @Override
    public Bid getSource() {
        return (Bid) source;
    }
}
