package com.ilnaz.server.mapper;

import com.ilnaz.server.dto.UserDto;
import com.ilnaz.server.model.User;
import com.ilnaz.server.util.CryptoUtils;

public class UserMapper {
    public static User toUser(UserDto userDto) throws Exception {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(CryptoUtils.generateStrongPasswordHash(userDto.getPassword()));
        return user;
    }
}
