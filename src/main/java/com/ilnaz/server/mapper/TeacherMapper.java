package com.ilnaz.server.mapper;

import com.ilnaz.server.dto.TeacherDto;
import com.ilnaz.server.model.Teacher;

public class TeacherMapper {
    public static Teacher toTeacher(TeacherDto teacherDto) {
        Teacher teacher = new Teacher();
        teacher.setFirstName(teacherDto.getFirstName());
        teacher.setSecondName(teacherDto.getSecondName());
        teacher.setLastName(teacherDto.getLastName());
        teacher.setGroupId(teacherDto.getGroupId());
        teacher.setSubjectsId(teacherDto.getSubjectsId());
        return teacher;
    }

    public static TeacherDto toTeacherDto(Teacher teacher) {
        TeacherDto teacherDto = new TeacherDto();

        teacherDto.setFirstName(teacher.getFirstName());
        teacherDto.setSecondName(teacher.getSecondName());
        teacherDto.setLastName(teacher.getLastName());
        teacherDto.setGroupId(teacher.getGroupId());
        teacherDto.setSubjectsId(teacher.getSubjectsId());
        return teacherDto;
    }
}
