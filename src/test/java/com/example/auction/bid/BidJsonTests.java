package com.example.auction.bid;


import com.example.auctionapp.domain.auction.bid.Bid;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
@RunWith(SpringRunner.class)
@JsonTest
public class BidJsonTests {
    @Autowired
    private JacksonTester<Bid> json;

    @Value("${classpath:/json/bid.json}")
    Resource bidJson;

    @Test // object -> json
    public void testSerialize() throws Exception{
        assertThat(this.json.write(getBid()))
                .isEqualToJson("/json/bid.json");
    }
    @Test //json -> object
    public void testDeserialized() throws Exception{
        assertThat(this.json.parse(Files.readAllBytes(Paths.get(bidJson.getURI()))))
                .isEqualTo(getBid());

    }



    public Bid getBid(){
        Bid bid = new Bid();
       bid.setAmount(new BigDecimal("45.00"));
       bid.setId(1L);
       return bid;

    }
}