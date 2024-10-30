package com.ilnaz.server.service.impl;

import com.ilnaz.server.config.JsonConfiguration;
import com.ilnaz.server.dto.TeacherDto;
import com.ilnaz.server.mapper.TeacherMapper;
import com.ilnaz.server.model.Teacher;
import com.ilnaz.server.repository.TeacherRepository;
import com.ilnaz.server.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final Logger log;


    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
        this.log = LoggerFactory.getLogger(TeacherServiceImpl.class);
    }

    @Override
    public void getTeacherList(HttpServletResponse resp) throws IOException {
        List<Teacher> allTeachers = teacherRepository.getAllTeachers();
        List<TeacherDto> collect = allTeachers.stream().map(TeacherMapper::toTeacherDto).collect(Collectors.toList());
        String json = JsonConfiguration.getObjectMapper().writer().writeValueAsString(collect);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json);
        log.info("Response body: {}", resp );

    }

    @Override
    public void getTeacherById(long id, HttpServletResponse resp) throws IOException {
        Teacher teacher = teacherRepository.getTeacherById(id);
        String json = JsonConfiguration.getObjectMapper().writeValueAsString(teacher);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json);
        log.info("Response body: {}", json );


    }

    @Override
    public void deleteTeacher(long id, HttpServletResponse resp) throws SQLException {
        boolean successfulDeletion = teacherRepository.deleteTeacher(id);
        if (successfulDeletion) {
            resp.setStatus(HttpServletResponse.SC_OK);
            log.info("Body delete: {}", resp.getStatus() );
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            log.info("Body delete: {}", resp.getStatus() );
        }


    }

    @Override
    public void addTeacher(TeacherDto teacherDto, HttpServletResponse resp) throws IOException {
        Teacher teacher = teacherRepository.addTeacher(TeacherMapper.toTeacher(teacherDto));
        String json = JsonConfiguration.getObjectMapper().writeValueAsString(teacher);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write(json);
        log.info("Response body: {}", json );
    }
}
