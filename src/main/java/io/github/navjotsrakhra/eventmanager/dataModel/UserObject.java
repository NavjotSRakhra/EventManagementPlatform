/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
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

/**
 * The UserObject class represents a user entity with user details.
 */
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

    /**
     * Get the authorities granted to the user.
     *
     * @return A collection of GrantedAuthority objects.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Objects.requireNonNull(roles).parallelStream().map(role -> new SimpleGrantedAuthority(role.name())).toList();
    }

    /**
     * Check if the user account is not expired.
     *
     * @return true if the account is not expired, false otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }

    /**
     * Check if the user account is not locked.
     *
     * @return true if the account is not locked, false otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    /**
     * Check if the user credentials are not expired.
     *
     * @return true if the credentials are not expired, false otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    /**
     * Check if the user account is enabled.
     *
     * @return true if the account is enabled, false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return isAccountNonExpired() && isAccountNonLocked();
    }
}
