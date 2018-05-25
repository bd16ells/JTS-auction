package com.example.auctionapp.security;

import com.example.auctionapp.domain.user.User;
import com.example.auctionapp.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userQuery = userRepository.findByUsername(username);

        if(userQuery.isPresent()){
            User user = userQuery.get();
            /**
             * handle roles
             *
             */
            List<GrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            });

            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(), user.getPassword(), authorities );
        }
        else
        {
            throw new UsernameNotFoundException(username);
        }
    }
}
