package com.example.auctionapp.domain.auction.bid.validation.rule;

import com.example.auctionapp.domain.auction.Auction;
import com.example.auctionapp.domain.auction.bid.Bid;
import com.example.validation.rule.Rule;
import org.springframework.validation.Errors;

import java.util.Map;
import java.util.Optional;

public class BidFactorRule implements Rule<Bid> {
    @Override
    public void execute(Bid target, Map<Object, Object> context, Errors errors) {
        Auction auction = (Auction) context.get("auction");
        Optional<Bid> highestBid = auction.getHighestBid();

        if(highestBid.isPresent()){
            if(target.getAmount().doubleValue() > highestBid.get().getAmount().doubleValue() * 20.0D){
                errors.rejectValue("amount", "BidTooHigh", new Object[]{highestBid.get().getAmount().doubleValue()}, null);
            }
        }


    }
}
