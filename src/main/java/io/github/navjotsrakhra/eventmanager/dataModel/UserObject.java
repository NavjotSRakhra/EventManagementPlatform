/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved
 */

package io.github.navjotsrakhra.eventmanager.dataModel;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("NullableProblems")
@Entity
@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class UserObject implements UserDetails {
    @NonNull
    @Column(unique = true)
    private final String username;
    @NonNull
    private final List<Role> roles;
    @NonNull
    private String password;
    @NonNull
    private boolean accountExpired;
    @NonNull
    private boolean accountLocked;
    @NonNull
    private boolean credentialsExpired;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Objects.requireNonNull(roles).parallelStream().map(role -> new SimpleGrantedAuthority(role.name())).toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return isAccountNonExpired() && isAccountNonLocked();
    }
}
