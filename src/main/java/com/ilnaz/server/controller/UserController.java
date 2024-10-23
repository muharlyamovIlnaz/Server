package com.ilnaz.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilnaz.server.config.DBConfiguration;
import com.ilnaz.server.dto.UserDto;
import com.ilnaz.server.repository.UserRepository;
import com.ilnaz.server.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class UserController extends HttpServlet {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public UserController() throws SQLException {

        UserRepository userRepository = new UserRepository(DBConfiguration.getConnection());
        userService = new UserService(userRepository);
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
