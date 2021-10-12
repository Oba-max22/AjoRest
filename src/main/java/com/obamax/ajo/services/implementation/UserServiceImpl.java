package com.obamax.ajo.services.implementation;

import com.obamax.ajo.models.User;
import com.obamax.ajo.repositories.UserRepository;
import com.obamax.ajo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmailAddress(email);
    }
}