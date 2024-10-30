package com.ilnaz.server.service;

import com.ilnaz.server.dto.TeacherDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public interface TeacherService {
    void getTeacherList(HttpServletResponse resp) throws IOException;

    void getTeacherById(long id, HttpServletResponse resp) throws IOException;

    void deleteTeacher(long id, HttpServletResponse resp) throws SQLException;

    void addTeacher(TeacherDto teacherDto, HttpServletResponse resp) throws IOException;
}
