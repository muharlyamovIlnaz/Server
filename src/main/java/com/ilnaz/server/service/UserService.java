package com.ilnaz.server.service;

import com.ilnaz.server.dto.UserDto;
import com.ilnaz.server.mapper.UserMapper;
import com.ilnaz.server.model.User;
import com.ilnaz.server.repository.UserRepository;

import java.sql.SQLException;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(UserDto userDto) throws Exception {
        User user = UserMapper.toUser(userDto);
        userRepository.save(user);

    }
}
