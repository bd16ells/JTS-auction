package com.example.auctionapp.auditing.context;

import com.example.auctionapp.domain.user.User;

public interface UserContextService {

    User getCurrentUser();
    Long getCurrentUserId();
    String getCurrentUsername();

}
