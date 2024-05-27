package com.example.alumni.security;


import com.example.alumni.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {
    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + user.getRole().getName());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getIsAccountExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getIsAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getIsCredentialsExpired();
    }

    @Override
    public boolean isEnabled() {
        return !user.getIsDisabled();
    }
}
