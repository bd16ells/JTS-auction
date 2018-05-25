package com.example.auctionapp.domain.auction.bid.validation.rule;

import com.example.auctionapp.auditing.context.UserContextService;
import com.example.auctionapp.domain.auction.bid.Bid;
import com.example.validation.rule.SimpleRuleEngine;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
@AllArgsConstructor
public class BidRuleEngine extends SimpleRuleEngine<Bid>{

    private UserContextService userContextService;

    @PostConstruct
    void init(){

        addRule(new BidOnceRule(userContextService), false);
        addRule(new BidAmountRule(), false);
        addRule(new BidFactorRule(), false);
    }
}

