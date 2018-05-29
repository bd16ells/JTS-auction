package com.example.auctionapp.domain.auction.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EntityEventListener  {

    private Logger logger = LoggerFactory.getLogger(EntityEventListener.class);

    @EventListener
    void onAuctionCreatedEvent(AuctionCreatedEvent event){
        logger.info("{} was created" , event.getSource());
    }
    @EventListener
    void onBidCreatedEvent(BidCreatedEvent event){
        logger.info("{} was placed on {}" , event.getSource(), event.getSource().getAuction());
    }
    @EventListener
    void onAuctionExpiredEvent(AuctionExpiredEvent event){
        logger.info("{} expired at {}", event.getSource(), event.getSource().getExpiryDate());
    }
    @EventListener
    void onBidExpiredEvent(BidExpiredEvent event){
        logger.info("{} expired at {}", event.getSource(), event.getSource().getExpiryDate());
    }
    @EventListener
    void onUserCreatedEvent(UserCreatedEvent event){
        logger.info("{} created", event.getSource());
    }

}
