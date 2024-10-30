package com.ilnaz.server.controller;

import com.ilnaz.server.config.DBConfiguration;
import com.ilnaz.server.config.JsonConfiguration;
import com.ilnaz.server.dto.StudentDto;
import com.ilnaz.server.repository.impl.StudentRepositoryImpl;
import com.ilnaz.server.service.StudentService;
import com.ilnaz.server.service.impl.StudentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/student/*")
public class StudentController extends HttpServlet {
    private final Logger log;
    private final StudentService studentService;

    public StudentController() throws SQLException {
        this.log = LoggerFactory.getLogger(StudentController.class);
        this.studentService = createStudentService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        log.info("Получен GET запрос /student/{}: req: {}", pathInfo, req);
        if (pathInfo != null || !pathInfo.equals("/")) {
            pathInfo = pathInfo.substring(1);
            long studentId = Long.parseLong(pathInfo);
            studentService.getStudentById(studentId, resp);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("Получен POST запрос /student: req: {}", req);
        StudentDto studentDto = JsonConfiguration.getObjectMapper().readValue(req.getInputStream(), StudentDto.class);
        studentService.addStudent(studentDto, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp){
        log.info("Получен DELETE запрос /student: req: {}", req);
        String pathInfo = req.getPathInfo();
        if (pathInfo != null || !pathInfo.equals("/")) {
            pathInfo = pathInfo.substring(1);
            long studentId = Long.parseLong(pathInfo);
            try {
                studentService.deleteStudentById(studentId, resp);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    protected StudentServiceImpl createStudentService() throws SQLException {
        return new StudentServiceImpl(createStudentRepository());
    }

    protected StudentRepositoryImpl createStudentRepository() throws SQLException {
        return new StudentRepositoryImpl(DBConfiguration.getConnection());
    }

}
