package com.obamax.ajo.services;

import com.obamax.ajo.models.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByEmail(String email);
}
