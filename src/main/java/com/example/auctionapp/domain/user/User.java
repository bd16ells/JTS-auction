package com.example.auctionapp.domain.user;


import com.example.auctionapp.domain.AbstractEntity;
import com.example.auctionapp.domain.user.role.Role;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class User  {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Column(unique = true)
    private String username;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

}
