package com.example.auctionapp.domain.auction.bid;


import com.example.auctionapp.domain.AbstractEntity;
import com.example.auctionapp.domain.auction.Auction;
import com.example.auctionapp.domain.auction.bid.validation.constraint.BidIncrement;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Data
@ToString(exclude = "auction")
@EqualsAndHashCode(exclude = "auction")
public class Bid extends AbstractEntity implements Comparable<Bid> {

    @Override
    public int compareTo(Bid o) {
        return Double.compare(o.getAmount().doubleValue(), this.getAmount().doubleValue()) * -1;
    }

//    @Override
//    public int compare(Bid o1, Bid o2) {
//        return Double.compare(o1.getAmount().doubleValue(), o2.getAmount().doubleValue()) * -1;
//    }

    @NotNull(message = "Must enter an amount")
    @BidIncrement
    private BigDecimal amount;

    @JsonIgnore
    @ManyToOne
    //////!!!!!!!!!!!!!?1?????????//////////
//    @NotNull(message = "Must have an auction")
    private Auction auction;
}
