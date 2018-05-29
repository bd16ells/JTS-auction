package com.example.auctionapp.domain.user;

public interface UserService {
    User update(User incoming, User current);
    User save(User user);
    void delete(User user);
}
