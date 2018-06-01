package com.example.auctionapp.domain.auction.event;

import com.example.auctionapp.domain.auction.Auction;
import com.example.auctionapp.domain.auction.bid.Bid;
import org.springframework.context.ApplicationEvent;

import java.util.Optional;

public class AuctionWonEvent extends ApplicationEvent {

//    @Autowired
//    MessageSender messageSender;

    public AuctionWonEvent(Auction source) {
        super(source);
        Optional<Bid> winningBid = source.getHighestBid();
        if(winningBid.isPresent()) {
            winningBid.get().setDidWin(true);
        }

    }

    @Override
    public Auction getSource() {
        return (Auction) source;
    }
}
