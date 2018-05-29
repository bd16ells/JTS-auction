package com.example.auctionapp.domain.auction;

import com.example.auctionapp.domain.auction.event.AuctionCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Setter
public class AuctionServiceImpl implements AuctionService{

    private AuctionRepository auctionRepository;
    ApplicationEventPublisher publisher;

    @Override
    public Auction save(Auction auction){
        if(auction.getId() == null) {
            publisher.publishEvent(new AuctionCreatedEvent(auction));
        }
        return auctionRepository.save(auction);

    }
    @Override
    public Iterable<Auction> findAll(){
        return auctionRepository.findAll();

    }

    @Override
    public void delete(Auction auction){
        auctionRepository.delete(auction);
    }

    @Override
    public void deleteById(Long id){
        auctionRepository.deleteById(id);
    }

    @Override
    public Optional<Auction> findById(Long id){
        return auctionRepository.findById(id);
    }

    @Override
    public Auction update(Auction incoming, Auction current){
        //BeanUtils.copyProperties(incoming, current, "id" );
        return save(current.copyFields(incoming, "bids"));
    }
}
