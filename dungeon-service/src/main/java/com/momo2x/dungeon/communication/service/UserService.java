package com.momo2x.dungeon.communication.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Service
public class UserService {

    public UserDetails getLoggedUser() {
        final var authentication = getContext().getAuthentication();
        return (UserDetails) authentication.getPrincipal();
    }

}
