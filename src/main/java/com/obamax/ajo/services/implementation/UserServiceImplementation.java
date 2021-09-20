package com.obamax.ajo.services.implementation;

import com.obamax.ajo.exceptions.ResourceNotFoundException;
import com.obamax.ajo.models.User;
import com.obamax.ajo.repositories.UserRepository;
import com.obamax.ajo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmailAddress(email);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Incorrect parameter; email " + email + " does not exist");
        }
        return user.get();
    }

}
