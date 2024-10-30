package com.ilnaz.server.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ilnaz.server.config.JsonConfiguration;
import com.ilnaz.server.dto.StudentDto;
import com.ilnaz.server.mapper.StudentMapper;
import com.ilnaz.server.model.Student;
import com.ilnaz.server.repository.StudentRepository;
import com.ilnaz.server.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final Logger log;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
        this.log = LoggerFactory.getLogger(TeacherServiceImpl.class);
    }

    @Override
    public void getStudentById(long id, HttpServletResponse resp) throws IOException {
        Student student = studentRepository.getStudentById(id);
        String json = JsonConfiguration.getObjectMapper().writeValueAsString(student);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json);
        log.info("Response body: {}", resp);

    }

    @Override
    public void deleteStudentById(long id, HttpServletResponse resp) throws SQLException {
        boolean successfulDeletion = studentRepository.deleteStudentById(id);
        if (successfulDeletion) {
            resp.setStatus(HttpServletResponse.SC_OK);
            log.info("Body delete: {}", resp.getStatus());
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            log.info("Body delete: {}", resp.getStatus());
        }

    }

    @Override
    public void addStudent(StudentDto studentDto, HttpServletResponse resp) throws IOException {
        Student student = studentRepository.addStudent(StudentMapper.toStudent(studentDto));
        String json = JsonConfiguration.getObjectMapper().writeValueAsString(student);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write(json);
        log.info("Response body: {}", resp );
    }

    @Override
    public void getStudentsByGroupId(long group_id, HttpServletResponse resp) throws IOException {
        List<Student> allStudent = studentRepository.getStudentsByGroupId(group_id);
        List<StudentDto> collect = allStudent.stream().map(StudentMapper::toStudentDto).collect(Collectors.toList());
        String json = JsonConfiguration.getObjectMapper().writer().writeValueAsString(collect);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json);
        log.info("Response body: {}", resp );

    }
}
