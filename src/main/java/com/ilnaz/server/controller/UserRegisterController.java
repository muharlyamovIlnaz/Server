package com.ilnaz.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilnaz.server.config.DBConfiguration;
import com.ilnaz.server.dto.UserDto;
import com.ilnaz.server.repository.UserRepository;
import com.ilnaz.server.repository.impl.UserRepositoryImpl;
import com.ilnaz.server.service.UserService;
import com.ilnaz.server.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/user/register")
public class UserRegisterController extends HttpServlet {
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final Logger log;

    public UserRegisterController() throws SQLException {
        this.log = LoggerFactory.getLogger(UserRegisterController.class);

        UserRepository userRepository = new UserRepositoryImpl(DBConfiguration.getConnection());
        userService = new UserServiceImpl(userRepository);
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Получен POST запрос /user/register: req: {}", req);
        try {
            UserDto userDto = objectMapper.readValue(req.getInputStream(), UserDto.class);
            userService.registerUser(userDto);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("Аккаунт создан");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Аккаунт не создан");
        }
    }
}
