package com.example.auctionapp.domain.scheduling;

import com.example.auctionapp.domain.auction.Auction;
import com.example.auctionapp.domain.auction.AuctionRepository;
import com.example.auctionapp.domain.auction.bid.Bid;
import com.example.auctionapp.domain.auction.bid.BidRepository;
import com.example.auctionapp.domain.auction.event.AccountExpiredEvent;
import com.example.auctionapp.domain.auction.event.AuctionExpiredEvent;
import com.example.auctionapp.domain.auction.event.BidExpiredEvent;
import com.example.auctionapp.domain.user.User;
import com.example.auctionapp.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@Transactional
public class EntityPurge {
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    BidRepository bidRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ApplicationEventPublisher publisher;

    @Scheduled(cron = "${purge.cron.auction.expression}")
    public void purgeExpiredAuction() {
        ZonedDateTime now = ZonedDateTime.now();
       List<Auction> removed =  auctionRepository.deleteByExpiryDateLessThan(now);
       for(Auction a: removed){
           publisher.publishEvent(new AuctionExpiredEvent(a));
       }

    }

    @Scheduled(cron = "${purge.cron.bid.expression}")
    public void purgeExpiredBid() {
        ZonedDateTime now = ZonedDateTime.now();
        List<Bid> removed = bidRepository.deleteByExpiryDateLessThan(now);
        for(Bid b: removed){
            publisher.publishEvent(new BidExpiredEvent(b));
        }
    }
    @Scheduled(cron = "${purge.cron.user.expression}")
    public void purgeExpiredUser() {
        ZonedDateTime now = ZonedDateTime.now();
        List<User> removed = userRepository.deleteByExpiryDateLessThan(now);
        for(User u: removed){
            publisher.publishEvent(new AccountExpiredEvent(u));
        }
    }
}
