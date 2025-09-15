package org.astrobrains.urlshortener.service;

import org.astrobrains.urlshortener.model.User;
import org.astrobrains.urlshortener.records.CreateUserCmd;
import org.astrobrains.urlshortener.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional(readOnly = true)
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createUser(CreateUserCmd cmd) {
        if (userRepository.existsByEmail(cmd.email())) {
            throw new RuntimeException("Email already exists");
        }
        User user = User.builder()
                .email(cmd.email())
                .password(passwordEncoder.encode(cmd.password()))
                .name(cmd.name())
                .role(cmd.role())
                .createdAt(Instant.now()).build();
        log.info("User Created with email {}", cmd.email());
        userRepository.save(user);
    }
}