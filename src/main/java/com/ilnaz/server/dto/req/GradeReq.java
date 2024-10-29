package com.ilnaz.server.dto.req;

import lombok.Data;

@Data
public class GradeReq {
    private int value;
    private long studentId;
    private long teacherId;
    private long subjectId;
}
