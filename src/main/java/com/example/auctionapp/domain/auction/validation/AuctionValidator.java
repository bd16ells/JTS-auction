package com.example.auctionapp.domain.auction.validation;

import com.example.auctionapp.domain.auction.Auction;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class AuctionValidator implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return Auction.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Auction auction = (Auction) o;

        if(auction.getName() == null){
            errors.rejectValue("name", "NotNull.Auction.name", "Can't have null auction name");
        }
        if(auction.getDescription() == null){
            errors.rejectValue("description", "NotNull.Auction.description", "Cannot have null description");
        }

    }
}
