package com.ewertonilima.course.services.impl;

import com.ewertonilima.course.repositories.UserRepository;
import com.ewertonilima.course.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
