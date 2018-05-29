package com.example.auction;

import com.example.auctionapp.domain.auction.Auction;
import com.example.auctionapp.domain.auction.AuctionController;
import com.example.auctionapp.domain.auction.AuctionServiceImpl;
import com.example.auctionapp.domain.user.UserRepository;
import com.example.auctionapp.security.WebSecurityConfig;
import com.example.validation.exception.WebApiExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
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
import static org.assertj.core.api.Assertions.*;



import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(AuctionController.class)
@Import(WebSecurityConfig.class)
public class AuctionControllerTests {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private AuctionServiceImpl auctionService;

    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    UserRepository mockUserRepository;

    @Test
    @WithMockUser
    public void findById() throws Exception{
        /**

        Auction com.example.auction = new Auction();
        com.example.auction.setId(5L);
        com.example.auction.setName("Bens Auction");
        Optional<Auction> optAuction = Optional.of(com.example.auction);

        Mockito.when(auctionService.findById(5L)).thenReturn(Optional.of(com.example.auction)) ;

        mockMvc.perform(get("/auctions/5").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().json("{'id': 5, 'name': 'Bens Auction'}"));
         **/
        mockMvc.perform(get("/auctions/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)));

    }

    @Test
    @WithMockUser
    public void deleteById() throws Exception{
//        Auction com.example.auction = new Auction();
//        com.example.auction.setId(5L);
//
//
//
//        mockMvc.perform(delete("/auctions/5").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.content().string(isEmptyOrNullString()));
//
//        Mockito.verify(auctionService, Mockito.times(1)).deleteById(5L);

        mockMvc.perform(delete("/auctions/1"))
                .andDo(print())
                //.andExpect(status().isNotFound()) // 404, comment out but change config
                .andExpect(status().isNoContent()) // 204
                .andExpect(content().string(isEmptyOrNullString())); //HttpStatus.NO_CONTENT

        ArgumentCaptor<Auction> argument = ArgumentCaptor.forClass(Auction.class);
        Mockito.verify(auctionService, Mockito.times(1)).delete(argument.capture());
        assertThat(argument.getValue().getId()).isEqualTo(1l);
    }

    @Test
    @WithMockUser
    public void createAndReturn() throws Exception{

        Auction auction = new Auction();
        auction.setName("testAuction");
        auction.setDescription("description");
        Mockito.when(auctionService.save(Mockito.any(Auction.class))).thenAnswer(invocation-> {

            Auction auctionArg = invocation.getArgument(0);

            Auction savedAuction = new Auction(); //create new entity, prove it was returned from service

            savedAuction.setId(1L);
//            savedAuction.setId(4l);
            savedAuction.setName(auctionArg.getName());
            savedAuction.setDescription(auctionArg.getDescription());
            //savedAuction.setName("Ben's Auction");
            return savedAuction;
        });

        mockMvc.perform(post("/auctions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(auction)))
                .andDo(print())
                .andExpect(status().isCreated()) // 201
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("testAuction")));




    }
    @MockBean
    WebApiExceptionHandler webApiExceptionHandler;
    @Test
    @WithMockUser
    public void createAuctionWithNullName() throws Exception{
        Auction auction = new Auction();
        auction.setId(1l);


        mockMvc.perform(post("/auctions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(auction)))
                .andDo(print())
                .andExpect(status().isOk()); // 200

//                .andExpect(jsonPath("$.error", Matchers.is("Bad Result")))
//                .andExpect(jsonPath("$.id", Matchers.is(1)));

    }

    @Test
    @WithMockUser
    public void updateAndReturn() throws Exception {


        Mockito.when(auctionService.update(ArgumentMatchers.any(Auction.class), ArgumentMatchers.any(Auction.class)))
                .thenAnswer(invocation -> {
                   Auction incoming = invocation.getArgument(0);
                   Auction current = invocation.getArgument(1);

                   Auction updatedAuction = new Auction(); // new entity to prove this was returned by the service
                   updatedAuction.setId(current.getId()); // prove Id taken from mock repo call
                   updatedAuction.setName(incoming.getName()); //prove incoming was passed in correctly
                    return updatedAuction;

                });

        Auction auction = new Auction();
        auction.setName("testAuction");
        auction.setDescription("description");

        mockMvc.perform(put("/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(auction)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("testAuction")))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                ;
    }




    @Test
    @WithMockUser
    public void findAll() throws Exception {
        Auction auction = new Auction();
        auction.setName("testAuction");

        Mockito.when(auctionService.findAll()).thenReturn(Arrays.asList(auction));

        mockMvc.perform(get("/auctions"))
                .andDo(print())
                .andExpect(status().isOk())
                //why cant you use
                //.andExpect(jsonPath("$.name", Matchers.is("testAuction")))
                .andExpect(jsonPath("$[0].name", Matchers.is("testAuction")))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));

    }







    @TestConfiguration
    static class InternalConfig {
        @Bean
        WebMvcConfigurer configurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addFormatters(FormatterRegistry registry) {
                    registry.addConverter(String.class, Auction.class, id -> {
                        Auction auction = new Auction();
                        auction.setId(Long.parseLong(id));
                        return auction;
                    });
                }
            };
        }
    }

}