package com.ilnaz.server.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ilnaz.server.config.JsonConfiguration;
import com.ilnaz.server.dto.UserDto;
import com.ilnaz.server.dto.resp.TokenResp;
import com.ilnaz.server.mapper.UserMapper;
import com.ilnaz.server.model.User;
import com.ilnaz.server.repository.UserRepository;
import com.ilnaz.server.service.UserService;
import com.ilnaz.server.util.CryptoUtils;
import com.ilnaz.server.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Logger log;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.log = LoggerFactory.getLogger(UserServiceImpl.class);
    }

    @Override
    public void registerUser(UserDto userDto) throws Exception {
        byte[] salt = CryptoUtils.getSalt();
        User user = UserMapper.toUser(userDto, salt);
        userRepository.save(user, salt);
    }

    @Override
    public void loginUser(UserDto userDto, HttpServletResponse resp) throws Exception {
        log.info("Метод loginUser");
        User user = userRepository.findByUsername(userDto.getUsername());
        byte[] salt = userRepository.getSaltByUserId(user.getId());


        if (!CryptoUtils.validatePassword(userDto.getPassword(), user.getPassword(), salt)) {
            log.error("Invalid username or password");
            throw new Exception("Invalid username or password");
        }


        String token = JwtUtil.generateToken(user.getUsername(), String.valueOf(user.getRole()));

        String json = JsonConfiguration.getObjectMapper().writeValueAsString(new TokenResp(token));
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json);
    }
}
