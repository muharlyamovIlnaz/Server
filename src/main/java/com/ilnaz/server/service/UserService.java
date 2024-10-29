package com.ilnaz.server.service;

import com.ilnaz.server.dto.UserDto;

import javax.servlet.http.HttpServletResponse;

public interface UserService {
    void registerUser(UserDto userDto) throws Exception;
    void loginUser(UserDto userDto, HttpServletResponse resp) throws Exception;
}
