package org.example.hikingmatchservice.security.custom;

import lombok.Data;
import org.example.hikingmatchservice.entities.PersonalInformation;
import org.example.hikingmatchservice.entities.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUser extends User {

    private Long id;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Long id) {
        super(username, password, authorities);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
