package com.example.auctionapp.domain.auction;


import com.example.auctionapp.domain.AbstractEntity;
import com.example.auctionapp.domain.auction.bid.Bid;
import com.example.auctionapp.domain.auction.bid.BidComparator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SortComparator;

import javax.persistence.*;
import javax.swing.text.html.Option;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Data
@ToString(exclude = "bids")
@EqualsAndHashCode(exclude = "bids")
public class Auction extends AbstractEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;

    @NotNull(message = "{NotNull.Auction.name}")
    @Column(unique = true)
    private String name;

    @NotNull(message = "{NotNull.Auction.description}")
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, orphanRemoval = true)
    @SortComparator(BidComparator.class)
    private SortedSet<Bid> bids = new TreeSet<>();


    public Auction addBid(Bid bid){
        bid.setAuction(this);
        bids.add(bid);
        return this;
    }

    public Auction removeBid(Bid bid){
        bid.setAuction(null);
        bids.remove(bid);
        return this;
    }
    public Optional<Bid> getBidById(Long bidId){
        return bidId == null ? Optional.empty(): bids.stream().filter(bid -> bid.getId().equals(bidId)).findFirst();

    }
    public Optional<Bid> getBidByUsername(String username){
        return username == null ? Optional.empty() : bids.stream().filter(bid -> bid.getCreatedBy().equals(username)).findFirst();
    }

    public Optional<Bid> getHighestBid(){
        return bids.isEmpty() ? Optional.empty() : Optional.of(bids.first());
    }
}

