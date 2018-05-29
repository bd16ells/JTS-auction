package com.example.auctionapp.domain.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {


    Optional<User> findByUsername(String username); // magic sql statements
    Optional<User> findByUsernameAndEnabled(String username, boolean enabled);
    List<User> deleteByExpiryDateLessThan(ZonedDateTime now);

}
