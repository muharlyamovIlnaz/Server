package com.ilnaz.server.dto;

import lombok.Data;

import java.util.List;
@Data
public class StudentDto {
    private String firstName;
    private String secondName;
    private String lastName;
    private Long groupId;
    private List<Long> subjectsId;

}
