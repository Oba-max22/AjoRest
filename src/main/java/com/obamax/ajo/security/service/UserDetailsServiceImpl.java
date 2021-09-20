package com.obamax.ajo.security.service;

import com.obamax.ajo.models.User;
import com.obamax.ajo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        User user = userService.findUserByEmail(email);

        return UserDetailsImpl.build(user);
    }
}