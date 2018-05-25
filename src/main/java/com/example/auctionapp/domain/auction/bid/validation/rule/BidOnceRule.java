package com.example.auctionapp.domain.auction.bid.validation.rule;

import com.example.auctionapp.auditing.context.UserContextService;
import com.example.auctionapp.domain.auction.Auction;
import com.example.auctionapp.domain.auction.bid.Bid;
import com.example.validation.rule.Rule;
import com.example.validation.rule.RuleDescription;
import lombok.AllArgsConstructor;
import org.springframework.validation.Errors;

import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class BidOnceRule implements Rule<Bid> {
    private UserContextService userContextService;

    @Override
    public void execute(Bid target, Map<Object, Object> context, Errors errors){
        Auction auction = (Auction) context.get("auction");

        Optional<Bid> currentBid = auction.getBidByUsername(userContextService.getCurrentUsername());

        if(currentBid.isPresent()){
            errors.reject("BidAlreadyExists", new Object[]{currentBid.get()}, "{BidAlreadyExists.message}");
        }
    }

}
