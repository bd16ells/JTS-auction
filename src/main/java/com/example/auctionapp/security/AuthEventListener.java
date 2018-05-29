package com.example.auctionapp.security;

import com.example.auctionapp.domain.user.User;
import com.example.auctionapp.domain.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthEventListener {
    private final Logger logger = LoggerFactory.getLogger(AuthEventListener.class);

    private enum LoginFailure{
        BAD_CREDENTIALS,
        DISABLED,
        LOCKED,
        EXPIRED
    }

    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthProperties authProperties;

    @EventListener
    public void authenticationFailed(AuthenticationFailureBadCredentialsEvent event){
        String username = (String) event.getAuthentication().getPrincipal();
        logger.info("user = {} loginFailure={}", username, LoginFailure.BAD_CREDENTIALS);

        Optional<User> user = userRepository.findByUsernameAndEnabled(username, true);

        if(user.isPresent()){
            user.get().setLoginAttempts(user.get().getLoginAttempts() + 1);
            logger.info("user={} loginFailure={} loginAttempts={} of {}", user.get().getUsername(),
                    LoginFailure.BAD_CREDENTIALS, user.get().getLoginAttempts(), authProperties.getLoginAttempts());

            if(user.get().getLoginAttempts() >= authProperties.getLoginAttempts()){
                logger.warn("Calling the POLICE on user={}", user.get().getUsername());
                user.get().setEnabled(false);
            }
            userRepository.save(user.get());
        }
    }
}
