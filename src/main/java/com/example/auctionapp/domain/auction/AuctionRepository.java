package com.example.auctionapp.domain.auction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
//    @Modifying
//    @Query("delete from auction a where a.expiryDate <= ?1")
//    void deleteAllExpiredSince(ZonedDateTime now);
    List<Auction> deleteByExpiryDateLessThan(ZonedDateTime now);
}
