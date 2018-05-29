package com.example.auction.bid;

import com.example.auctionapp.domain.auction.Auction;
import com.example.auctionapp.domain.auction.AuctionRepository;
import com.example.auctionapp.domain.auction.bid.Bid;
import com.example.auctionapp.domain.auction.bid.BidRepository;
import com.example.auctionapp.domain.auction.bid.BidServiceImpl;
import com.example.auctionapp.domain.auction.bid.validation.rule.BidRuleEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.util.Optional;


@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
@Import(BidRuleEngine.class)
public class BidServiceTest {

    @Mock
    AuctionRepository auctionRepository;

    @Mock
    BidRepository bidRepository;

    @InjectMocks
    BidServiceImpl bidService;
    @Mock
    BidRuleEngine bidRuleEngine;


    Auction auction = new Auction();

    @Before
    public void init(){
//        bidRuleEngine = Mockito.mock(bidRuleEngine.getClass());
        auction.setId(1L);
        auction.setName("name");
        auction.setDescription("Description");
    }

    @Test
    public void testSave(){

        Bid bid = new Bid();
        bid.setAmount(new BigDecimal(42.00));


        Mockito.when(bidRepository.save(bid)).thenAnswer((Answer<Bid>) invocationOnMock -> {
            Bid savedBid = new Bid();
            savedBid.setAmount(((Bid) (invocationOnMock.getArgument(0))).getAmount());
            savedBid.setId(1L);

            return savedBid;

        });

        assertThat(bidRepository.save(bid))
                //.hasFieldOrPropertyWithValue("auction", "my auction")
                .hasFieldOrPropertyWithValue("amount", new BigDecimal(42.00))
                .hasFieldOrPropertyWithValue("id", 1L);


    }

    @Test
    public void testUpdate() {
        Bid current = new Bid();
        current.setAmount(new BigDecimal("20.0"));
        current.setId(1L);
        Bid incoming = new Bid();
        incoming.setAmount(new BigDecimal("45.00"));
        incoming.setId(2L);
        Mockito.when(bidRepository.save(current)).thenReturn(current);




        assertThat(bidService.update(auction, incoming, current))
                //.hasFieldOrPropertyWithValue("auction", "my auction")
                .hasFieldOrPropertyWithValue("amount", new BigDecimal("45.00"))
                .hasFieldOrPropertyWithValue("id", 1L);

    }

    @Test
    public void testFindAll(){

        Bid bid = new Bid();
        bid.setAmount(new BigDecimal("42.0"));


        auction.addBid(bid);


        Mockito.when(auctionRepository.findById(auction.getId())).thenReturn(Optional.of(auction));

        assertThat(bidService.findAll(auction)).hasSize(1)
                .containsExactly(bid);


    }



    @Test
    public void testFindById(){
        Bid bid = new Bid();
        bid.setId(1L);
        bid.setAmount(new BigDecimal("42.00"));




        Mockito.when(bidRepository.findById(1L)).thenReturn(Optional.of(bid));


        assertThat(bidRepository.findById(1L).get()).isEqualTo(bid);


    }



    @Test
    public void testDelete(){
        Bid bid = new Bid();

        bidService.delete(auction, bid);

        Mockito.verify(auctionRepository, Mockito.times(1)).save(auction);

    }




}
