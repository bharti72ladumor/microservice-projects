package com.bharti.gateway.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
public class CustomUser implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private Boolean isActive;

    private User user;

    public CustomUser(User user) {
        this.user = user;
    }

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public CustomUser(Long id, String userName, String password, Collection<? extends GrantedAuthority> authorities, Boolean isActive) {
        this.id = id;
        this.username = userName;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
    }

    public static CustomUser build(User users) {
        System.out.println("User authorityrrrrrrrrrrrrrrrrrrrr");
        Set<GrantedAuthority> authorities = new HashSet<>();
        users.getRoles().forEach(roleLink -> {
            authorities.add(new SimpleGrantedAuthority(roleLink.name()));
            System.out.println("GGGGGGG: " + roleLink.name());
        });
        return new CustomUser(users.getId(), users.getUsername(), users.getPassword(), authorities, users.getIsActive());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
