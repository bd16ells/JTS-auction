package com.example.auctionapp.domain.auction.validation;

import com.example.auctionapp.domain.auction.bid.Bid;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

public class BidValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Bid.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Bid bid = (Bid) o;
        if(bid.getAmount() == null || bid.getAmount().compareTo(BigDecimal.ZERO) <= 0){
            errors.rejectValue("amount", "NotNull.Bid.amount", "Bid cannot be 0");
        }
//        if(bid.getAuction() == null){
//            errors.rejectValue("auction", "NotNull.Bid.auction" , "Auction cannot be null");
//        }

    }
}

