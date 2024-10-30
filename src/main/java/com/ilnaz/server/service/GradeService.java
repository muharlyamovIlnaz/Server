package com.ilnaz.server.service;

import com.ilnaz.server.dto.req.GradeReq;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface GradeService {
    void giveRating(GradeReq gradeReq, HttpServletResponse resp) throws IOException;
}
