package com.ilnaz.server.repository;

import com.ilnaz.server.model.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentRepository {
    Student getStudentById(long id);

    boolean deleteStudentById(long id) throws SQLException;

    Student addStudent(Student student);

    List<Student> getStudentsByGroupId(long groupId);


}
