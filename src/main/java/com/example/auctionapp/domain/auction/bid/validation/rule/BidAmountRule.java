package com.example.auctionapp.domain.auction.bid.validation.rule;

import com.example.auctionapp.auditing.context.UserContextService;
import com.example.auctionapp.domain.auction.Auction;
import com.example.auctionapp.domain.auction.bid.Bid;
import com.example.validation.rule.Rule;
import org.springframework.validation.Errors;

import java.util.Map;
import java.util.Optional;

public class BidAmountRule implements Rule<Bid> {

    private UserContextService userContextService;

    @Override
    public void execute(Bid target, Map<Object, Object> context, Errors errors) {
        Auction auction = (Auction) context.get("auction");
        Optional<Bid> highestBid = auction.getHighestBid();
        if( highestBid.isPresent() &&  target.getAmount().doubleValue() <=
                highestBid.get().getAmount().doubleValue() ) {
            errors.rejectValue("amount", "BidTooLow", new Object[]{highestBid.get().getAmount().doubleValue()}, null);
        }
// errors.rejectValue("amount", "NotNull.Bid.amount", "Bid cannot be 0");
    }
}
