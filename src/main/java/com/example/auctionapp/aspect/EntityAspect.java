package com.example.auctionapp.aspect;

import com.example.auctionapp.domain.auction.Auction;
import com.example.auctionapp.domain.auction.bid.Bid;
import com.example.auctionapp.domain.user.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@Aspect
public class EntityAspect {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    TimeOfExpiration timeOfExpiration;

    @Pointcut("execution(* org.springframework.data.repository.*.save(..))")
    public void anySave(){}

    @Around("anySave()")
    public Object setExpiryDate(ProceedingJoinPoint pjp) throws Throwable{

        Object obj = pjp.getArgs()[0];
        if(obj instanceof Auction){
            Auction auction = (Auction) obj;
            if(auction.getExpiryDate() == null) {
                auction.setExpiryDate(ZonedDateTime.now().plusSeconds(timeOfExpiration.getAuctionTime()));
                logger.info("Auction's expiration date has been set {}", auction.getExpiryDate());
                return pjp.proceed(new Object[]{auction});
            }
            else {
                logger.info("{} already has expiry date of {}", auction, auction.getExpiryDate());
                return pjp.proceed(pjp.getArgs());
            }
            // make sure auction isn't expired
        }
        else if(obj instanceof Bid){
            Bid bid = (Bid) obj;
            if(bid.getExpiryDate() == null) {
                //bid expires directly after auction
                bid.setExpiryDate(bid.getAuction().getExpiryDate().plusSeconds(1L));
                logger.info("Bid's expiration date has been set {}", bid.getExpiryDate());
                return pjp.proceed(new Object[]{bid});
            }
            else {
                logger.info("Bid {} already has expiry date of {}", bid, bid.getExpiryDate());
                return pjp.proceed(pjp.getArgs());
            }

        }
        else if(obj instanceof User){
            User user = (User) obj;
            user.setExpiryDate(ZonedDateTime.now().plusYears(timeOfExpiration.getUserTime()));
            logger.info("User={}'s expiration date has been set {}", user.getUsername(), user.getExpiryDate());
            return pjp.proceed(new Object[]{user});

        }
        return pjp.proceed(pjp.getArgs());



    }
}
