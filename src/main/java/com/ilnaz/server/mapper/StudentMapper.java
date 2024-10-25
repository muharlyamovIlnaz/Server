package com.ilnaz.server.mapper;

import com.ilnaz.server.dto.StudentDto;
import com.ilnaz.server.model.Student;

public class StudentMapper {
    public static Student toStudent(StudentDto studentDto){
        Student student = new Student();
        student.setFirstName(studentDto.getFirstName());
        student.setSecondName(studentDto.getSecondName());
        student.setLastName(studentDto.getLastName());
        student.setGroupId(studentDto.getGroupId());
        student.setSubjectsId(studentDto.getSubjectsId());
        return student;
    }

    public static StudentDto toStudentDto(Student student){
        StudentDto studentDto = new StudentDto();
        studentDto.setFirstName(student.getFirstName());
        studentDto.setSecondName(student.getSecondName());
        studentDto.setLastName(student.getLastName());
        studentDto.setGroupId(student.getGroupId());
        studentDto.setSubjectsId(student.getSubjectsId());
        return studentDto;
    }
}
