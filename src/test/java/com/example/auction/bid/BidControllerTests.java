package com.example.auction.bid;

import com.example.auctionapp.domain.auction.Auction;
import com.example.auctionapp.domain.auction.bid.Bid;
import com.example.auctionapp.domain.auction.bid.BidController;
import com.example.auctionapp.domain.auction.bid.BidServiceImpl;
import com.example.auctionapp.security.WebSecurityConfig;
import com.example.auctionapp.domain.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SuppressWarnings("Duplicates")
@RunWith(SpringRunner.class)
@WebMvcTest(BidController.class)
@Import(WebSecurityConfig.class)
public class BidControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private BidServiceImpl bidService;

    @MockBean
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    private static Auction auction1 = new Auction();
    private static Bid bid1 = new Bid();
    private static Bid bid2 = new Bid();

    @Before
    public void before() {
        auction1.setId(1L);
        bid1.setId(1L);
        bid2.setId(2L);
    }


    @Test
    @WithMockUser
    public void save() throws Exception {


        Mockito.when(bidService.save(eq(auction1), ArgumentMatchers.any(Bid.class))).thenAnswer(invocation -> {
            Bid bidArg = invocation.getArgument(1);

            Bid returnBid = new Bid();
            returnBid.setId(1L);
            returnBid.setAmount(bidArg.getAmount());
            return returnBid;
        });
        Bid bid = new Bid();
        bid.setAmount(new BigDecimal("45.00"));

        mockMvc.perform(post("/auctions/1/bids")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bid)))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    @WithMockUser
    public void testDelete() throws Exception {


        auction1.addBid(bid1);

        mockMvc.perform(delete("/auctions/1/bids/1"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(content().string(isEmptyOrNullString()));
    }

    @Test
    @WithMockUser
    public void findAll() throws Exception {

        auction1.addBid(bid1);
        auction1.addBid(bid2);
        mockMvc.perform(get("/auctions/1/bids"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[*].id", Matchers.contains(1,2)));
    }


    @Test
    @WithMockUser
    public void findById() throws Exception{
       // auction1.addBid(bid2);
        mockMvc.perform(get("/auctions/1/bids/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(2)));
    }


    @Test
    @WithMockUser
    public void testUpdate() throws Exception{
        Mockito.when(bidService.update(eq(auction1), ArgumentMatchers.any(Bid.class), ArgumentMatchers.any(Bid.class))).thenAnswer(invocation -> {
            Bid incoming = invocation.getArgument(1);
            Bid current = invocation.getArgument(2);

            Bid updated = new Bid();
            updated.setId(current.getId());
            updated.setAmount(incoming.getAmount());

            return updated;
        });
        Bid bid = new Bid();
        bid.setAmount(new BigDecimal("45.00"));

        auction1.addBid(bid1);
        auction1.addBid(bid2);

        mockMvc.perform(put("/auctions/1/bids/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bid)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(2)))
                .andExpect(jsonPath("$.amount", Matchers.is(45.00)));

    }


    @TestConfiguration
    static class InternalConfig {
        @Bean
        WebMvcConfigurer configurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addFormatters(FormatterRegistry registry) {
                    registry.addConverter(String.class, Auction.class, id -> auction1);
                    registry.addConverter(String.class, Bid.class, id -> {
                        switch (id) {
                            case "1":
                                return bid1;
                            case "2":
                                return bid2;
                            default:
                                Bid bid = new Bid();
                                bid.setId(Long.parseLong(id));
                                return bid;
                        }
                    });
                }
            };
        }
    }
}
