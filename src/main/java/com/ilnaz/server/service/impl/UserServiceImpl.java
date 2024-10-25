package com.ilnaz.server.service.impl;

import com.ilnaz.server.dto.UserDto;
import com.ilnaz.server.mapper.UserMapper;
import com.ilnaz.server.model.User;
import com.ilnaz.server.repository.UserRepository;
import com.ilnaz.server.service.UserService;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(UserDto userDto) throws Exception {
        User user = UserMapper.toUser(userDto);
        userRepository.save(user);

    }
}
