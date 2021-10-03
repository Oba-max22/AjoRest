package com.obamax.ajo.seeder;

import com.obamax.ajo.exceptions.ResourceNotFoundException;
import com.obamax.ajo.models.Role;
import com.obamax.ajo.models.RoleType;
import com.obamax.ajo.models.User;
import com.obamax.ajo.repositories.RoleRepository;
import com.obamax.ajo.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class SeedUser {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void loadUserData() {
        if (userRepository.count() == 0) {
            User user1 = new User();
            user1.setLastName("West");
            user1.setFirstName("Kanye");
            user1.setEmailAddress("kanyedonda@gmail.com");
            user1.setPassword(passwordEncoder.encode("donda100"));
            List<Role> roles = new ArrayList<>();
            Role role = roleRepository.findRoleByType(RoleType.ADMIN)
                    .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
            roles.add(role);
            user1.setRoles(roles);
            userRepository.save(user1);
            log.info("User seeded");
        }
    }
}
