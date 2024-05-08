package com.example.demo.service;

import com.example.demo.Repository.UserRepository;
import com.example.demo.model.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity create(final UserEntity userEntity) {
        if( userEntity == null || userEntity.getUsername() == null) {
            throw new RuntimeException("Invalid arguments");
        }

        final String username = userEntity.getUsername();

        if( userRepository.existsByUsername(username)) {
            log.warn("Username already exists {}", username);
            throw new RuntimeException("Username already exists");
        }

        return userRepository.save(userEntity);
    }

    public UserEntity getByCredential(final String username, final String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public UserEntity getByCredential(final String username, final String password, final PasswordEncoder encoder) {
        final UserEntity originalUser = userRepository.findByUsername(username);
        if(originalUser != null && encoder.matches(password, originalUser.getPassword())) {
            return  originalUser;
        }
        return null;
    }
}
