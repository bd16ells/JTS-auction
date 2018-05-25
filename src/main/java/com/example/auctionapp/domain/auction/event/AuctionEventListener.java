package com.example.auctionapp.domain.auction.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AuctionEventListener  {

    private Logger logger = LoggerFactory.getLogger(AuctionEventListener.class);

    @EventListener
    void onAuctionCreatedEvent(AuctionCreatedEvent event){
        logger.info("{}" , event.getSource());
    }

}
