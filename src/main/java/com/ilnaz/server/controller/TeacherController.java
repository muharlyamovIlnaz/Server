package com.ilnaz.server.controller;

import com.ilnaz.server.config.DBConfiguration;
import com.ilnaz.server.config.JsonConfiguration;
import com.ilnaz.server.dto.TeacherDto;
import com.ilnaz.server.repository.TeacherRepository;
import com.ilnaz.server.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/teacher/*")
public class TeacherController extends HttpServlet {
    private final Logger log;

    private final TeacherService teacherService;

    public TeacherController() throws SQLException {
        this.teacherService = createTeacherService();
        this.log = LoggerFactory.getLogger(TeacherController.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo(); // Это даст строку вида "/{id}" или null

        // Проверяем, передан ли ID
        if (pathInfo == null || pathInfo.equals("/")) {

            // Если нет ID, значит это запрос на список всех учителей
            getAllTeachers(req, resp);
        } else {
            // Если ID передан, обрабатываем запрос на конкретного учителя
            getTeacherById(req, resp, pathInfo);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Получен POST запрос /teacher: req: {}", req);
        TeacherDto teacherDto = JsonConfiguration.getObjectMapper().readValue(req.getInputStream(), TeacherDto.class);
        teacherService.addTeacher(teacherDto, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("Получен DELETE запрос /teacher: req: {}", req);
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            resp.getWriter().write("not found id");
        } else {
            String idStr = pathInfo.substring(1);
            long teacherId = Long.parseLong(idStr);
            try {
                teacherService.deleteTeacher(teacherId, resp);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void getAllTeachers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Получен GET запрос /teacher: req: {}", req);
        teacherService.getTeacherList(resp);
    }

    private void getTeacherById(HttpServletRequest req, HttpServletResponse resp, String pathInfo) throws IOException {
        String idStr = pathInfo.substring(1);
        log.info("Получен GET запрос /teacher/{}: req: {}", idStr, req);
        long teacherId = Long.parseLong(idStr);
        teacherService.getTeacherById(teacherId, resp);
    }

    protected TeacherService createTeacherService() throws SQLException {
        return new TeacherService(createTeacherRepository());
    }

    protected TeacherRepository createTeacherRepository() throws SQLException {
        return new TeacherRepository(DBConfiguration.getConnection());
    }


}
