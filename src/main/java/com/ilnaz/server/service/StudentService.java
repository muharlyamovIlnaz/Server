package com.ilnaz.server.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.ilnaz.server.dto.StudentDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public interface StudentService {
    void getStudentById(long id, HttpServletResponse resp) throws IOException;

    void deleteStudentById(long id,HttpServletResponse resp) throws SQLException;

    void addStudent(StudentDto studentDto, HttpServletResponse resp) throws IOException;

    void getStudentsByGroupId(long group_id, HttpServletResponse resp) throws IOException;

}
