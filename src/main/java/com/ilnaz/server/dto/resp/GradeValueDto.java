package com.ilnaz.server.dto.resp;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GradeValueDto {
    private int value;
    private LocalDate date;
}
