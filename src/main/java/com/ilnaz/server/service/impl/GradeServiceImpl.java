package com.ilnaz.server.service.impl;

import com.ilnaz.server.config.JsonConfiguration;
import com.ilnaz.server.dto.req.GradeReq;
import com.ilnaz.server.mapper.GradeMapper;
import com.ilnaz.server.mapper.StudentMapper;
import com.ilnaz.server.model.Grade;
import com.ilnaz.server.model.Student;
import com.ilnaz.server.repository.GradeRepository;
import com.ilnaz.server.service.GradeService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;

    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    public void giveRating(GradeReq gradeReq, HttpServletResponse resp) throws IOException {
        Grade grade = gradeRepository.giveRating(GradeMapper.toGrade(gradeReq));
        String json = JsonConfiguration.getObjectMapper().writeValueAsString(grade);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write(json);

    }
}
