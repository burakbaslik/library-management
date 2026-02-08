package com.burakbaslik.config;

import com.burakbaslik.model.Role;
import com.burakbaslik.model.User;
import com.burakbaslik.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner seedAdmin(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        return args -> {
            userRepository.findByUsername("cc").orElseGet(() -> {
                User admin = User.builder()
                        .name("Admin")
                        .username("cc")
                        .password(encoder.encode("1234"))
                        .authorities(Set.of(Role.ROLE_ADMIN))
                        .accountNonExpired(true)
                        .credentialsNonExpired(true)
                        .isEnabled(true)
                        .accountNonLocked(true)
                        .build();
                return userRepository.save(admin);
            });
        };
    }
}
