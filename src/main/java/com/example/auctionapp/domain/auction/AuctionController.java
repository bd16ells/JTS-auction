package com.example.auctionapp.domain.auction;


import com.example.auctionapp.domain.auction.validation.AuctionValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class AuctionController {

    AuctionServiceImpl auctionService;

    @InitBinder
    public void init(WebDataBinder webDataBinder){
        webDataBinder.addValidators(new AuctionValidator());
    }

    @PostMapping("/auctions")
    public ResponseEntity<Auction> save( @Valid @RequestBody Auction auction)
    {
        //handle empty / incorrect body??
       return new ResponseEntity<>(auctionService.save(auction), HttpStatus.CREATED);
    }


    @GetMapping("/auctions")
    public Iterable<Auction> findAll()
    {
        return auctionService.findAll();
    }


    /**
     * If com.example.auction exists, return it with an OK http status. Otherwise, return
     * a status indicating it was not found.
     * we handle this instead of leaving it to browser ??
     * @param auction
     * @return
     */
    @GetMapping("/auctions/{id}")
    public ResponseEntity<Auction> findById(@PathVariable("id") Optional<Auction> auction)
    {
        return auction.isPresent() ? new ResponseEntity<>(auction.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/auctions/{id}")
    public ResponseEntity delete(@PathVariable("id") Optional<Auction> auction) {

        if (auction.isPresent()) {
            auctionService.delete(auction.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Auction> update(@PathVariable("id") Optional<Auction> current,
                                          @RequestBody Auction incoming){
        //handle empty / incorrect body??

        if(current.isPresent() ){
            return ResponseEntity.ok(auctionService.update(incoming, current.get()));
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}


//request params -- after ?
//path variable part of url
