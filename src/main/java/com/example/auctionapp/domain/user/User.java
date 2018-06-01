package com.example.auctionapp.domain.user;


import com.example.auctionapp.domain.AbstractEntity;
import com.example.auctionapp.domain.user.role.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Data
public class User extends AbstractEntity{

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Id
//    private long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

    @Column(name = "login_attempts", nullable = false)
    private int loginAttempts = 0;

    private ZonedDateTime expiryDate;
}
