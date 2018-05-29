package com.example.auctionapp.domain.auction.bid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface BidRepository extends CrudRepository<Bid, Long> {
    List<Bid> deleteByExpiryDateLessThan(ZonedDateTime now);

}
