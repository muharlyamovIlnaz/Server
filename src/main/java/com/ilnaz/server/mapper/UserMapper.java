package com.ilnaz.server.mapper;

import com.ilnaz.server.auth.Role;
import com.ilnaz.server.dto.UserDto;
import com.ilnaz.server.model.User;
import com.ilnaz.server.util.CryptoUtils;

public class UserMapper {
    public static User toUser(UserDto userDto, byte[] salt) throws Exception {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(CryptoUtils.generateStrongPasswordHash(userDto.getPassword(), salt));
        if (userDto.getRole() == null) {
            user.setRole(Role.ROLE_DEFAULT);
        } else {
            user.setRole(userDto.getRole());
        }
        return user;
    }
}
