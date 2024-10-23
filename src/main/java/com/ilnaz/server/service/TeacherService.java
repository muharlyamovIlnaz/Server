package com.ilnaz.server.service;

import com.ilnaz.server.config.JsonConfiguration;
import com.ilnaz.server.dto.TeacherDto;
import com.ilnaz.server.mapper.TeacherMapper;
import com.ilnaz.server.model.Teacher;
import com.ilnaz.server.repository.TeacherRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public void getTeacherList(HttpServletResponse resp) throws IOException {
        List<Teacher> allTeachers = teacherRepository.getAllTeachers();
        List<TeacherDto> collect = allTeachers.stream().map(TeacherMapper::toTeacherDto).collect(Collectors.toList());
        String json = JsonConfiguration.getObjectMapper().writer().writeValueAsString(collect);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json);

    }

    public void getTeacherById(long id, HttpServletResponse resp) throws IOException {
        Teacher teacher = teacherRepository.getTeacherById(id);
        String json = JsonConfiguration.getObjectMapper().writeValueAsString(teacher);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json);

    }

    public void deleteTeacher(long id, HttpServletResponse resp) throws SQLException {
        boolean successfulDeletion = teacherRepository.deleteTeacher(id);
        if (successfulDeletion) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }


    }

    public void addTeacher(TeacherDto teacherDto, HttpServletResponse resp) throws IOException {
        Teacher teacher = teacherRepository.addTeacher(TeacherMapper.toTeacher(teacherDto));
        String json = JsonConfiguration.getObjectMapper().writeValueAsString(teacher);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write(json);
    }
}
