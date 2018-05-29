package com.example.auctionapp.domain.auction.event;

import com.example.auctionapp.domain.auction.Auction;
import com.example.auctionapp.domain.auction.bid.Bid;
import org.springframework.context.ApplicationEvent;

public class BidCreatedEvent extends ApplicationEvent {

    public BidCreatedEvent(Bid source) {
        super(source);
    }

    @Override
    public Bid getSource() {
        return (Bid) source;
    }
}
