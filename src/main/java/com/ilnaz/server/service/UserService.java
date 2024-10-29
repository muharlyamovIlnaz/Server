package com.ilnaz.server.service;

import com.ilnaz.server.dto.UserDto;

public interface UserService {
    void registerUser(UserDto userDto) throws Exception;
    void loginUser(UserDto userDto) throws Exception;
}
