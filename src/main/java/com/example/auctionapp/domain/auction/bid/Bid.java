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
public class Bid extends AbstractEntity {



    @NotNull(message = "Must enter an amount")
    @BidIncrement
    private BigDecimal amount;

    @JsonIgnore
    @ManyToOne
    //////!!!!!!!!!!!!!?1?????????//////////
//    @NotNull(message = "Must have an auction")
    private Auction auction;
}
