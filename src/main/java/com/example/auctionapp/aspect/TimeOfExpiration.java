package com.example.auctionapp.aspect;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "expiration-time")
@Data
public class TimeOfExpiration {
    private Long auctionTime;
    private Long userTime;

}
