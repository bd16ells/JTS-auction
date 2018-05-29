package com.example.auctionapp.domain.auction.bid;


import com.example.auctionapp.domain.auction.Auction;
import com.example.auctionapp.domain.auction.validation.BidValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;


@RestController
@AllArgsConstructor
public class BidController {

    BidServiceImpl bidService;

    @InitBinder
    public void init(WebDataBinder webDataBinder){
        webDataBinder.addValidators(new BidValidator());
    }


    @PostMapping("/auctions/{auctionId}/bids")
    public ResponseEntity<Bid> save(@PathVariable("auctionId") Optional<Auction> auction ,
                                        @Valid @RequestBody Bid bid)
    {
        Bid saved = bidService.save(auction.get(), bid);
        return auction.isPresent() ? new ResponseEntity<Bid>(saved, HttpStatus.CREATED) :
                new ResponseEntity<Bid>(HttpStatus.NOT_FOUND);
        //return new ResponseEntity<Bid>(bidService.save(auction.get(), bid), HttpStatus.CREATED);
//        return bid;
    }

    @DeleteMapping("/auctions/{auctionId}/bids/{bidId}")
    public ResponseEntity delete(@PathVariable("auctionId") Optional<Auction> auction,
                                 @PathVariable("bidId") Optional<Bid> bid){

        if(auction.isPresent() && bid.isPresent()){
            bidService.delete(auction.get(), bid.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/auctions/{auctionId}/bids")
    public ResponseEntity<Iterable<Bid>> findAll(@PathVariable("auctionId") Optional<Auction> auction){
        SortedSet<Bid> bids = auction.get().getBids();
        if(auction.isPresent()) {
            return bids.iterator().hasNext() ? new ResponseEntity<>(bids, HttpStatus.OK) :
                    new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/auctions/{auctionId}/bids/{bidId}")
    public ResponseEntity<Bid> findById(@PathVariable("auctionId") Optional<Auction> auction,
                                                     @PathVariable("bidId") Optional<Bid> bid){
        if(auction.isPresent() && bid.isPresent()){
            return new ResponseEntity<>(bid.get(), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/auctions/{auctionId}/bids/{bidId}")
    public ResponseEntity<Bid> update(@PathVariable("auctionId") Optional<Auction> auction,
                                      @PathVariable("bidId") Optional<Bid> current,
                                      @RequestBody Bid incoming){
        if(auction.isPresent() && current.isPresent()){
            return new ResponseEntity<>(bidService.update(auction.get(), incoming, current.get()),
                                        HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
