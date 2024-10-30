package com.ilnaz.server.mapper;

import com.ilnaz.server.dto.req.GradeReq;
import com.ilnaz.server.model.Grade;


public class GradeMapper {
    public static Grade toGrade(GradeReq gradeReq) {
        Grade grade = new Grade();
        grade.setStudentId(gradeReq.getStudentId());
        grade.setTeacherId(gradeReq.getTeacherId());
        grade.setSubjectId(gradeReq.getSubjectId());
        grade.setValue(gradeReq.getValue());
        return grade;
    }

    public static GradeReq toGradeReq(Grade grade) {
        GradeReq gradeReq = new GradeReq();
        gradeReq.setStudentId(grade.getStudentId());
        gradeReq.setTeacherId(grade.getTeacherId());
        gradeReq.setSubjectId(grade.getSubjectId());
        gradeReq.setValue(grade.getValue());
        return gradeReq;
    }
}
