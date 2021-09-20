package com.obamax.ajo.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BaseConfiguration {
    @Bean
    public PasswordEncoder passwordEncoderBCrypt() {
        return new BCryptPasswordEncoder();
    }
}
