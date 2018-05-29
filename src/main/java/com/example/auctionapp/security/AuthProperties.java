package com.example.auctionapp.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {

    private int loginAttempts;

    public int getLoginAttempts(){
        return loginAttempts;
    }
    public void setLoginAttempts(int loginAttempts){
        this.loginAttempts = loginAttempts;
    }

}
