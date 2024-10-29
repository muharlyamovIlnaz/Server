package com.ilnaz.server.model;

import lombok.Data;

@Data
public class Grade {
    private long id;
    private int value;
    private long studentId;
    private long teacherId;
    private long subjectId;
}
