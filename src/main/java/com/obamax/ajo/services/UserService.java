package com.obamax.ajo.services;

import com.obamax.ajo.models.User;

public interface UserService {
    User findUserByEmail(String email);
}
