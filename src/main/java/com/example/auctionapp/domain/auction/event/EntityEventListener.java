package com.example.auctionapp.domain.auction.event;

import com.example.auctionapp.domain.auction.Auction;
import com.example.auctionapp.domain.auction.bid.Bid;
import com.example.auctionapp.domain.auction.event.messaging.Sender;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class EntityEventListener  {
    private Logger logger = LoggerFactory.getLogger(EntityEventListener.class);

    @Autowired
    ApplicationEventPublisher publisher;

    @EventListener
    void onAuctionCreatedEvent(AuctionCreatedEvent event){
        logger.info("\t{} was created" , event.getSource());
    }
    @EventListener
    void onBidCreatedEvent(BidCreatedEvent event){
        logger.info("\t{} was placed on {}" , event.getSource(), event.getSource().getAuction());
    }
    @EventListener
    void onAuctionExpiredEvent(AuctionCompletedEvent event){
        Auction auction = event.getSource();
        logger.info("\t{} expired at {}", auction, auction.getExpiryDate());
        if(auction.getHighestBid().isPresent()){
            logger.info("\tEmailing {} that their bid of {} won!", auction.getHighestBid().get().getCreatedBy(),
                    auction.getHighestBid().get().getAmount());
            publisher.publishEvent(new AuctionWonEvent(auction));
            logger.info("\tEmailing {} that their auction has sold for {}", auction.getCreatedBy(),
                    auction.getHighestBid().get().getAmount());
        }
        else{
            logger.info("\tEmailing {} that their auction has failed to sell.", auction.getCreatedBy());
        }
    }
    @EventListener
    void onBidExpiredEvent(BidExpiredEvent event){
        logger.info("\t{} expired at {}", event.getSource(), event.getSource().getExpiryDate());
    }
    @EventListener
    void onUserCreatedEvent(UserCreatedEvent event){
        logger.info("\t{} created", event.getSource());
    }


    @Autowired
    Sender sender;
    @EventListener
    void onAuctionWonEvent(AuctionWonEvent event) {


        Auction auction = event.getSource();
        Optional<Bid> winningBid = auction.getHighestBid();
        if(winningBid.isPresent()) {
            JSONObject msg = new JSONObject();
            msg.put("seller", auction.getCreatedBy());
            msg.put("amount", winningBid.get().getAmount());
            msg.put("buyer", winningBid.get().getCreatedBy());

            sender.send(msg.toString());
        }
    }

}
