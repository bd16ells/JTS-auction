package com.example.auctionapp.domain.auction.bid.validation.constraint;

import com.example.auctionapp.domain.auction.bid.Bid;
import com.example.auctionapp.domain.user.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * http://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#validator-customconstraints
 */
public class BidIncrementConstraint implements ConstraintValidator<BidIncrement, BigDecimal> {

    private double increment;

    @Override
    public void initialize(BidIncrement bidAmountRounded) {
        this.increment = bidAmountRounded.value();
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        //Object obj = (Object) context.unwrap(Bid.class);
        return value == null || value.doubleValue() % increment == 0.0D;
    }


}
