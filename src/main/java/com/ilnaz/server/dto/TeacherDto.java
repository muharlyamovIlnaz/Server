package com.ilnaz.server.dto;

import lombok.Data;

import java.util.List;

@Data
public class TeacherDto {
    private String firstName;
    private String secondName;
    private String lastName;
    private long groupId;
    private List<Long> subjectsId;

}
