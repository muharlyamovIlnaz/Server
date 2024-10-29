package com.ilnaz.server.dto.resp;

import lombok.Data;

import java.util.List;

@Data
public class SubjectGradeDto {
    private String subjectName;
    private Long subjectId;
    private List<GradeValueDto> gradeValues;
}
