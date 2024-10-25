package com.ilnaz.server.repository;

import com.ilnaz.server.model.Teacher;

import java.sql.SQLException;
import java.util.List;

public interface TeacherRepository {
    List<Teacher> getAllTeachers();

    Teacher getTeacherById(long id);

    boolean deleteTeacher(long id) throws SQLException;

    Teacher addTeacher(Teacher teacher);
}
