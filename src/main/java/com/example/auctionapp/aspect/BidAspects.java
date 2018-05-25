package com.example.auctionapp.aspect;

import com.example.auctionapp.domain.auction.bid.Bid;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Aspect
@Component
public class BidAspects {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    // within(com.example.auctionapp.domain.auction.bid.BidRepository) && execution(* *.save(..))

    //@Pointcut("execution(* com.example.auctionapp.domain.auction.bid.BidRepository.save(..))")
    @Pointcut("within(com.example.auctionapp.domain.auction.bid.BidRepository) && execution(* *.save(..))")
    public void bidSave(){}

    @After("bidSave()")
    public void sendEmail(JoinPoint joinPoint) throws Throwable{
        logger.info("\t Email regarding update/creation of Bid sent from {} ", joinPoint);
    }
    @Around("bidSave() && args(bid)")
    public void bidFormat(ProceedingJoinPoint joinPoint, Bid bid) throws Throwable{

        BigDecimal newAmount = new BigDecimal(bid.getAmount().doubleValue());
        newAmount = newAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal temp = bid.getAmount();
        bid.setAmount(newAmount);

        logger.info("\t {} was changed to {} from {} call", temp, newAmount, joinPoint);

        Object[] args = new Object[]{bid};

        joinPoint.proceed(args);
    }
}
