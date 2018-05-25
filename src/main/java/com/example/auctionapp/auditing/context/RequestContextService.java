package com.example.auctionapp.auditing.context;

import java.time.ZonedDateTime;

public interface RequestContextService {
    String getPath();
    String getUuid();
    ZonedDateTime getTimestamp();
}
