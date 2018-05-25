package com.example.auctionapp.domain.auction.bid;

import com.example.auctionapp.domain.auction.Auction;

import java.util.Optional;

public interface BidService {

    Bid save(Auction auction, Bid bid);

    Bid update(Auction auction, Bid incoming, Bid current);

    void delete(Auction auction, Bid bid);

    void deleteById(Auction auction, Long id);

    Iterable<Bid> findAll(Auction auction);

    Iterable<Bid> findAllByAuctionId(Long id);

    Optional<Bid> findById(Auction auction, Long id);




}
