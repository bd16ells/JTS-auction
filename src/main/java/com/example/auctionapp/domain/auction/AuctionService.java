package com.example.auctionapp.domain.auction;

import java.util.Optional;

public interface AuctionService {

    Auction save(Auction auction);

    Iterable<Auction> findAll();

    void delete(Auction auction);

    void deleteById(Long id);

    Optional<Auction> findById(Long id);

    Auction update(Auction incoming, Auction current);


}
