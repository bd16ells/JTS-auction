package com.example.auctionapp.aspect;

import com.example.auctionapp.auditing.context.SpringSecurityUserContextService;
import com.example.auctionapp.auditing.context.UserContextService;
import com.example.auctionapp.domain.auction.Auction;
import com.example.auctionapp.domain.auction.bid.Bid;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Aspect
@Component
public class AuctionAspects {

    @Autowired
    UserContextService userContextService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.example.auctionapp.domain.auction.AuctionRepository.save(..))")
    public void saveAuction(){}

    @After("saveAuction()")
    public void sendEmail(JoinPoint joinPoint){
        logger.info("\t Email regarding update/creation of Auction sent from {} ", joinPoint);
    }

    @Pointcut("execution(* com.example.auctionapp.domain.auction.bid.BidService.save(..))")
    public void addBid(){}

    @Around("addBid()")// && args(bid, auction)")
    public Object  emailOldHighestBidder(ProceedingJoinPoint joinPoint) throws Throwable {

        Auction auction = (Auction) joinPoint.getArgs()[0];
        Bid bid = (Bid) joinPoint.getArgs()[1];
        ////////////////////////////////////////////
        logger.info(userContextService.getCurrentUsername());
        //////////////////////////////////////////////
        if(auction.getHighestBid().isPresent()){
            if(bid.getAmount().doubleValue() > auction.getHighestBid().get().getAmount().doubleValue() &&
                    !SecurityContextHolder.getContext().getAuthentication().getName().equals(
                            auction.getHighestBid().get().getCreatedBy())){

                logger.info("\t Emailing '{}' that their bid of {} was outbid by '{}' with an amount of {}",
                        auction.getHighestBid().get().getCreatedBy(), auction.getHighestBid().get().getAmount(),
                        SecurityContextHolder.getContext().getAuthentication().getName(),
                        bid.getAmount() );
            }
        }
       return joinPoint.proceed(new Object[]{auction, bid});
    }
    @After("addBid()")
    public void emailNewHighestBidder(JoinPoint joinPoint) {
        Bid highestBid = (Bid) joinPoint.getArgs()[1];
        if (highestBid.getCreatedBy() != null) {
            logger.info("\t Emailing '{}' that their bid of {} is the new highest bid.",
                    highestBid.getCreatedBy(), highestBid.getAmount());
        }
    }


}
