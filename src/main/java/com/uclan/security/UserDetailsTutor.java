package com.uclan.security;

import com.uclan.domain.Tutor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsTutor implements UserDetails {
    @Getter
    private final Tutor tutor;

    public UserDetailsTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return tutor.getPassword();
    }

    @Override
    public String getUsername() {
        return tutor.getTutorname();
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
