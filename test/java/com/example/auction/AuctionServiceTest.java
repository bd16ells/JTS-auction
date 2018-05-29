package com.example.auction;


import com.example.auctionapp.domain.auction.Auction;
import com.example.auctionapp.domain.auction.AuctionRepository;
import com.example.auctionapp.domain.auction.AuctionServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Arrays;
import java.util.Optional;

import org.mockito.stubbing.Answer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.*;

@SuppressWarnings("duplicates")
@RunWith(MockitoJUnitRunner.class)
public class AuctionServiceTest {

    @Mock
    AuctionRepository auctionRepository;

    @InjectMocks
    AuctionServiceImpl auctionService;
    @Mock
    ApplicationEventPublisher publisher;
    @Before
    public void setup(){
        publisher = Mockito.mock(ApplicationEventPublisher.class);
        auctionService.setPublisher(publisher);
    }


    @Test
    public void testFindAll(){
    Auction auction = new Auction();
    //com.example.auction.setId(1L);
    // mock, setting up the test
        Mockito.when(auctionRepository.findAll()).thenReturn(Arrays.asList(auction));

        assertThat(auctionService.findAll()).hasSize(1);
                //.contains(com.example.auction);


        //isSameAs checks reference
        // contains checks fields


    }



    @Test
    public void testFindById(){

        Auction auction = new Auction();
        auction.setId(1L);
        Optional<Auction> auctionO = Optional.of(auction);


        Mockito.when(auctionRepository.findById(1L)).thenReturn(Optional.of(auction));

        assertThat(auctionService.findById(1L)).isEqualTo(auctionO);
        //.contains(com.example.auction);

    }


    @Test
    public void testSave(){

        Auction auction = new Auction();
        auction.setName("auct");
        auction.setDescription("description");
        Mockito.when(auctionRepository.save(auction)).thenAnswer((Answer<Auction>) invocationOnMock -> {

            //Auction savedAuction = invocationOnMock.getArgument(0);
            Auction savedAuction = new Auction();
            savedAuction.setId(1L);
            savedAuction.setName(((Auction)(invocationOnMock.getArgument(0))).getName());
            savedAuction.setDescription(((Auction)(invocationOnMock.getArgument(0))).getDescription());
            return savedAuction;

        });

        assertThat(auctionService.save(auction)).hasFieldOrPropertyWithValue("id", 1L)
        .hasFieldOrPropertyWithValue("name", "auct");

    }

    @Test
    public void testDelete(){
        Auction auction = new Auction();

        auctionService.delete(new Auction());
//        auctionService.delete(new Auction());


        Mockito.verify(auctionRepository, Mockito.times(1)).delete(auction);

    }


    @Test
    public void testDeleteById(){

        auctionService.deleteById(1L);
   //     auctionService.deleteById(1L);

        Mockito.verify(auctionRepository, Mockito.times(1)).deleteById(1L);
    }
 // @WebMvcTest -- controller


}
