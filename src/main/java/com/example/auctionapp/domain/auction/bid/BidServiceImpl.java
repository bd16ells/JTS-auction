package com.example.auctionapp.domain.auction.bid;

import com.example.auctionapp.domain.auction.Auction;
import com.example.auctionapp.domain.auction.AuctionNotFoundException;
import com.example.auctionapp.domain.auction.AuctionRepository;
import com.example.auctionapp.domain.auction.bid.validation.rule.BidRuleEngine;
import com.example.auctionapp.domain.auction.event.BidCreatedEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class BidServiceImpl implements BidService {


    BidRepository bidRepository;
    AuctionRepository auctionRepository;
    BidRuleEngine bidRuleEngine;
    ApplicationEventPublisher publisher;
    @Override
    public Bid save(Auction auction, Bid bid) {
        executeRules(auction,bid);
        //bid is not already present
        if(!auction.getBidById(bid.getId()).isPresent() ) {
            Long id = bid.getId();
            // must add bid first, otherwise our eventListener does not know which
            // auction the bid was placed on.
            auction.addBid(bid);
            if(id == null){
                publisher.publishEvent(new BidCreatedEvent(bid));
            }



        }

        Bid savedBid =  bidRepository.save(bid);
        auctionRepository.save(auction);
        return savedBid;
    }

    @Override
    public Bid update(Auction auction, Bid incoming, Bid current) {
        // take from incoming put on current
        BeanUtils.copyProperties(incoming, current, "id", "auction");

        return save(auction, current);
    }

    @Override
    public void delete(Auction auction, Bid bid) {
//        if(auction.getBidById(bid.getId()).isPresent() ) {
//            auction.removeBid(bid);
//            bid.setAuction(null);
//
//        }
        auction.removeBid(bid);
        auctionRepository.save(auction);



    }

    @Override
    public void deleteById(Auction auction, Long id) {
        Optional<Bid> tempBid = auction.getBidById(id);

        if(tempBid.isPresent()) {
            delete(auction, tempBid.get());
        }

    }

    @Override
    public Iterable<Bid> findAll(Auction auction) {
        Optional<Auction> tempAuct = auctionRepository.findById(auction.getId());

        if(tempAuct.isPresent()){
            return tempAuct.get().getBids();
        }

        throw new AuctionNotFoundException();
    }

    @Override
    public Iterable<Bid> findAllByAuctionId(Long id) {
        Optional<Auction> auction = auctionRepository.findById(id);

        if(auction.isPresent()){
            return auction.get().getBids();
        }
        throw new BidNotFoundException();
    }

    @Override
    public Optional<Bid> findById(Auction auction, Long id) {
        return auctionRepository.findById(auction.getId()).get().getBidById(id);
    }

    private void executeRules(Auction auction, Bid bid) {
        Map<Object, Object> context = new HashMap<>();
        context.put("auction", auction);
        bidRuleEngine.execute(bid, context);
    }
}
