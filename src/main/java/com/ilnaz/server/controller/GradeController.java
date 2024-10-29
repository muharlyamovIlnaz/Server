package com.ilnaz.server.controller;

import com.ilnaz.server.config.DBConfiguration;
import com.ilnaz.server.config.JsonConfiguration;

import com.ilnaz.server.dto.req.GradeReq;
import com.ilnaz.server.repository.impl.GradeRepositoryImpl;
import com.ilnaz.server.repository.impl.StudentRepositoryImpl;
import com.ilnaz.server.service.GradeService;
import com.ilnaz.server.service.impl.GradeServiceImpl;
import com.ilnaz.server.service.impl.StudentServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/grade/*")
public class GradeController extends HttpServlet {
    private final GradeService gradeService;

    public GradeController() throws SQLException {
        this.gradeService = new GradeServiceImpl(new GradeRepositoryImpl(DBConfiguration.getConnection()));
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        GradeReq gradeReq = JsonConfiguration.getObjectMapper().readValue(req.getInputStream(), GradeReq.class);
        gradeService.giveRating(gradeReq, resp);
    }
}
