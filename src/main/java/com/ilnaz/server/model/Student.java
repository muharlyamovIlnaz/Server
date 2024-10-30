package com.ilnaz.server.model;

import lombok.Data;

import java.util.List;
@Data
public class Student {
    private long id;
    private String firstName;
    private String secondName;
    private String lastName;
    private Long groupId;
    private List<Long> subjectsId;
}
