package com.example.auction;


import com.example.auctionapp.domain.auction.Auction;
import com.example.auctionapp.domain.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.file.Files;
import java.nio.file.Paths;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuctionIntegrationTest {



    @Autowired
    ObjectMapper objectMapper;

    @Value("classpath:/json/createAuction.json")
    Resource createAuction;

    @Value("classpath:/json/createAuction2.json")
    Resource updateAuction;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepo;

    @Test
    @WithMockUser
    public void integrationTest() throws Exception {

        // perform a get when there are no auctions
        // expect nothing to return
        mockMvc.perform(get("/auctions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));


        Auction auction = new Auction();
        auction.setId(1L);
        auction.setName("Ben's Auction");
        auction.setDescription("used car");
        //add an com.example.auction from resource json file
// new String(Files.readAllBytes(Paths.get(createAuction.getURI())))
        mockMvc.perform(post("/auctions")
                .contentType(MediaType.APPLICATION_JSON)
                .content( new String(Files.readAllBytes(Paths.get(createAuction.getURI())))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("My Auction")));

        // doing find all
        mockMvc.perform(get("/auctions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is("My Auction")));
//
        //perform an update

        mockMvc.perform(put("/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content( new String(Files.readAllBytes(Paths.get(updateAuction.getURI())))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("Updated Auction")));

        // get new com.example.auction
        mockMvc.perform(get("/auctions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is("Updated Auction")));

        // delete
        mockMvc.perform(delete("/auctions/1"))
                .andDo(print())
                .andExpect(status().isNoContent()) // 204
                .andExpect(content().string(isEmptyOrNullString())); //HttpStatus.NO_CONTENT

        // make sure it is empty ( same as first sub-test )
        mockMvc.perform(get("/auctions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }

}
