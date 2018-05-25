package com.example.auctionapp.domain.auction.bid;

import java.util.Comparator;

public class BidComparator implements Comparator<Bid> {
    @Override
    public int compare(Bid o1, Bid o2) {
        return Double.compare(o1.getAmount().doubleValue(), o2.getAmount().doubleValue()) * -1;
    }
}
