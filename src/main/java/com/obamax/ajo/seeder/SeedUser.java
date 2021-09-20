package com.obamax.ajo.seeder;

import com.obamax.ajo.models.User;
import com.obamax.ajo.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SeedUser {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void loadUserData() {
        if (userRepository.count() == 0) {
            User user1 = new User();
            user1.setLastName("West");
            user1.setFirstName("Kanye");
            user1.setEmailAddress("kanyedonda@gmail.com");
            user1.setPassword(passwordEncoder.encode("donda100"));
            userRepository.save(user1);
            log.info("User seeded");
        }
    }
}
